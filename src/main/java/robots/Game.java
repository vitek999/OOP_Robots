package robots;

import org.jetbrains.annotations.NotNull;
import robots.event.*;
import robots.labirints.Labirint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private GameStatus gameStatus;
    private Robot activeRobot;
    private Robot winner;
    private Field gameField;
    // !!! Зачем сильная связь???
    // DONE: Пробрасываю лабиринт через конструктор, не сохраняя в поле класса

    public Game(Labirint labirint) {
        initGame(labirint);
    }

    private void initGame(@NotNull Labirint labirint) {
        setStatus(GameStatus.GAME_IS_ON);

        buildField(labirint);

        gameField.addFieldlActionListener(new FieldObserver());

        for(var i : gameField.getRobotsOnField()) {
            i.addRobotActionListener(new RobotObserver());
        }

        // set active robot
        passMoveNextRobot();
    }

    public void abort() { // !!! Не корректное название - она не завершилась, она прервалась
                          // DONE: Переименовал метод finish -> abort
        setStatus(GameStatus.GAME_ABORTED);
        setActiveRobot(null);
    }

    public GameStatus status() {
        return gameStatus;
    }

    private void setStatus(GameStatus status) {
        if(gameStatus != status) {
            gameStatus = status;
            fireGameStatusIsChanged(gameStatus);
        }
    }

    public Robot winner() {
        return winner;
    }

    public Robot activeRobot() {
        return activeRobot;
    }

    public Field getGameField() { // !!! либо везде get, либо везде без get
        return gameField;
    }

    public List<Robot> getRobotsOnField() {
        return Collections.unmodifiableList(gameField.getRobotsOnField()); // !!! Можно возвращать неизменяемый контейнер
                                                                           // DONE: Возвращаю неизменяемый контейнер
    }

    public List<Robot> getTeleportedRobots() {
        return Collections.unmodifiableList(gameField.getTeleportedRobots()); // !!! Можно возвращать неизменяемый контейнер
                                                                              // DONE: Возвращаю неизменяемый контейнер
    }

    private void passMoveNextRobot() {
        if(gameStatus != GameStatus.GAME_IS_ON) { // !!! Не ясное условие
            setActiveRobot(null);
            return;
        }

        List<Robot> robotsOnField = gameField.getRobotsOnField();

        if(robotsOnField.size() == 1 ) {
            Robot robot = robotsOnField.get(0);
            if(robot.getCharge() > 0) setActiveRobot(robot); // !!! Хорошо ли, что игра думает за робота??
        } else if (robotsOnField.size() == 2) {
            Robot firstRobot = robotsOnField.get(0);
            Robot secondRobot = robotsOnField.get(1);
            if(!firstRobot.isActive() && firstRobot.getCharge() > 0) {
                setActiveRobot(firstRobot);
            } else if (!secondRobot.isActive() && secondRobot.getCharge() > 0){
                setActiveRobot(secondRobot);
            }
        }
    }

    private GameStatus determineOutcomeGame() {
        GameStatus result = GameStatus.GAME_IS_ON;

        List<Robot> robotsOnField = gameField.getRobotsOnField();
        List<Robot> teleportedRobots = gameField.getTeleportedRobots();

        if(robotsOnField.isEmpty() && !teleportedRobots.isEmpty()) {
            result = GameStatus.ALL_ROBOTS_OUT;
        }

        if(!robotsOnField.isEmpty() && teleportedRobots.isEmpty() && robotsHasLowCharge(robotsOnField)) {
            result = GameStatus.ALL_ROBOTS_HAVE_LOW_CHARGE;
        }

        if(teleportedRobots.size() == 1 && robotsHasLowCharge(robotsOnField)) {
            setWinner(teleportedRobots.get(0));
            result = GameStatus.WINNER_FOUND;
        }

        return result;
    }

    private boolean robotsHasLowCharge(@NotNull List<Robot> robots) { // !!! Про батарейку знает только робот - см. название метода
                                                                      // DONE: переменовал метод robotsHasLowBattery -> robotsHasLowCharge
        boolean result = true;

        for(int i = 0; i < robots.size() && result; ++i) {
            result = robots.get(i).getCharge() == 0;
        }

        return result;
    }

    private void buildField(@NotNull Labirint labirint) {
        gameField = labirint.buildField();
    }

    private void setWinner(@NotNull Robot robot) {
        winner = robot;
        setActiveRobot(null); // !!! Непонятная для текущего контекста деятельность
    }

    private void setActiveRobot(Robot robot) {

        if (activeRobot != null) activeRobot.setActive(false);

        activeRobot = robot;

        if(robot != null ) robot.setActive(true);
    }

    /** Events */

    private class RobotObserver implements RobotActionListener {

        @Override
        public void robotIsMoved(@NotNull RobotActionEvent event) {
            fireRobotIsMoved(event.getRobot());
            if(!(event.getToCell() instanceof ExitCell)){ // !!! Повтор кода, см. обработчик ниже
                                                          // DONE: Так как исправил замечание ниже, повтора нет, так как здесь статус определяется толькл при выполнению условия
                setStatus(determineOutcomeGame());
            }
            passMoveNextRobot(); // !!! Почему не метод passMoveNextRobot()?
                                 // DONE: Использую passMoveNextRobot
        }

        @Override
        public void robotSkippedStep(@NotNull RobotActionEvent event) {
            fireRobotIsSkipStep(event.getRobot());
            setStatus(determineOutcomeGame());
            passMoveNextRobot();
        }

        @Override
        public void robotActivityChanged(@NotNull RobotActionEvent event) {
            // Not implemented yet
        }

        @Override
        public void robotChangedBattery(@NotNull RobotActionEvent event) {
            // Not implemented yet
        }
    }

    private class FieldObserver implements FieldActionListener {

        @Override
        public void robotIsTeleported(@NotNull FieldActionEvent event) {
            GameStatus status = determineOutcomeGame();
            setStatus(status);
            passMoveNextRobot();
            fireRobotIsTeleported(event.getRobot()); // !!! Странный порядок генерации события - обработчики этого события не увидят изменения состояния игры.
                                                     // DONE: Исправил порядок генерации события: сначала изменяется обстановка игры, а потом пробрасывается событие о телеортации робота.
        }
    }

    private ArrayList<GameActionListener> gameActionListeners = new ArrayList<>();

    public void addGameActionListener(@NotNull GameActionListener listener) {
        gameActionListeners.add(listener);
    }

    public void removeGameActionListener(@NotNull GameActionListener listener) {
        gameActionListeners.remove(listener);
    }

    private void fireRobotIsMoved(@NotNull Robot robot) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setRobot(robot);
            listener.robotIsMoved(event);
        }
    }

    private void fireRobotIsSkipStep(@NotNull Robot robot) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setRobot(robot);
            listener.robotIsSkipStep(event);
        }
    }

    private void fireRobotIsTeleported(@NotNull Robot robot) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setRobot(robot);
            listener.robotIsTeleported(event);
        }
    }

    private void fireGameStatusIsChanged(@NotNull GameStatus status) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setStatus(status);
            listener.gameStatusChanged(event);
        }
    }
}
