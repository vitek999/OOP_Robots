package robots.model;

import org.jetbrains.annotations.NotNull;
import robots.model.event.*;
import robots.model.field.cells.ExitCell;
import robots.model.field.Field;
import robots.model.field.cell_objects.Robot;
import robots.model.labirints.Labirint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Игра.
 */
public class Game {

    /**
     * Статус игры.
     */
    private GameStatus gameStatus;

    /**
     * Активный робот.
     */
    private Robot activeRobot;

    /**
     * Робот-победитель.
     */
    private Robot winner;

    /**
     * Игровое поле.
     */
    private Field gameField;
    // !!! Зачем сильная связь???
    // DONE: Пробрасываю лабиринт через конструктор, не сохраняя в поле класса

    public Game(Labirint labirint) {
        startGame(labirint);
    }

    /**
     * Старт новой игры
     * @param labirint лабиринт, содержащий расстановку элементов на поле
     */
    public void startGame(@NotNull Labirint labirint) {
        setStatus(GameStatus.GAME_IS_ON);

        buildField(labirint);

        gameField.addFieldActionListener(new FieldObserver());

        for(var i : gameField.getRobotsOnField()) {
            i.addRobotActionListener(new RobotObserver());
        }

        // set active robot
        passMoveNextRobot();
    }

    /**
     * Прервать игру
     */
    public void abort() { // !!! Не корректное название - она не завершилась, она прервалась
                          // DONE: Переименовал метод finish -> abort
        setStatus(GameStatus.GAME_ABORTED);
        setActiveRobot(null);
    }

    /**
     * Получить текущий статус игры {@link Game#gameStatus}
     * @return текующий статус игры
     */
    public GameStatus getStatus() {
        return gameStatus;
    }

    private void setStatus(GameStatus status) {
        if(gameStatus != status) {
            gameStatus = status;
            fireGameStatusIsChanged(gameStatus);
        }
    }

    /**
     * Получить робота-победителя.
     * Возвращается [null], если победитель не найден.
     * @return робот-победитель
     */
    public Robot getWinner() {
        return winner;
    }

    /**
     * Получить активного робота {@link Game#activeRobot}.
     * Возвращается null, если ни один робот не является активным.
     * @return активный робот.
     */
    public Robot getActiveRobot() {
        return activeRobot;
    }

    /**
     * Получить игровое поле {@link Game#gameField}.
     * @return игровое поле.
     */
    public Field getGameField() { // !!! либо везде get, либо везде без get
                                  // DONE: Переименовал, добавил к остальным методам префикс get
        return gameField;
    }

    /**
     * Получить роботов на поле.
     * @return неизменяемый список роботов, находящихся на поле.
     */
    public List<Robot> getRobotsOnField() {
        return Collections.unmodifiableList(gameField.getRobotsOnField()); // !!! Можно возвращать неизменяемый контейнер
                                                                           // DONE: Возвращаю неизменяемый контейнер
    }

    /**
     * Получить телепортированных роботов.
     * @return неизменяемый список телепортированных роботов.
     */
    public List<Robot> getTeleportedRobots() {
        return Collections.unmodifiableList(gameField.getTeleportedRobots()); // !!! Можно возвращать неизменяемый контейнер
                                                                              // DONE: Возвращаю неизменяемый контейнер
    }

    /**
     * Обновить состояние игры.
     */
    private void updateGameState() {
        GameStatus status = determineOutcomeGame();
        setStatus(status);
        if(status == GameStatus.GAME_IS_ON) {
            passMoveNextRobot();
            gameField.updateRenewablePowerSupplies();
        } else {
            setActiveRobot(null);
        }
    }

    /**
     * Передать ход следующему роботу.
     */
    private void passMoveNextRobot() {
        // !!! Не ясное условие
        // DONE: Вынес проверку на состояние игры в метод updateGameState

        List<Robot> robotsOnField = gameField.getRobotsOnField();

        if(robotsOnField.size() == 1 ) {
            Robot robot = robotsOnField.get(0);
            setActiveRobot(robot); // !!! Хорошо ли, что игра думает за робота??
                                   // DONE: Убрал проверку на заряд робота, теперь робот сам решает (см Robot.setActive)
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

    /**
     * Определить исход игры.
     * @return статус игры.
     */
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

    /**
     * Имеют ли все роботы нулевой заряд.
     * @param robots список роботов.
     * @return true - если все роботы имеют нулевой заряд.
     */
    private boolean robotsHasLowCharge(@NotNull List<Robot> robots) { // !!! Про батарейку знает только робот - см. название метода
                                                                      // DONE: переменовал метод robotsHasLowBattery -> robotsHasLowCharge
        boolean result = true;

        for(int i = 0; i < robots.size() && result; ++i) {
            result = robots.get(i).getCharge() == 0;
        }

        return result;
    }

    /**
     * Построить игровое поле.
     * @param labirint лабиринт, содержащий расстановку элементов на поле.
     */
    private void buildField(@NotNull Labirint labirint) {
        gameField = labirint.buildField();
    }

    private void setWinner(@NotNull Robot robot) {
        winner = robot;
        // !!! Непонятная для текущего контекста деятельность
        // DONE: убрал вызов setActiveRobot(null), т.к. он был не нужен, ибо эту деятельность выпоняет passMoveNextRobot
    }

    /**
     * Задать активного робота.
     * @param robot робот, которому становится активным.
     */
    private void setActiveRobot(Robot robot) {

        if (activeRobot != null) activeRobot.setActive(false);

        activeRobot = robot;

        if(robot != null ) robot.setActive(true);
    }

    /**
     * Класс, реализующий наблюдение за событиями {@link RobotActionListener}.
     */
    private class RobotObserver implements RobotActionListener {

        @Override
        public void robotIsMoved(@NotNull RobotActionEvent event) {
            fireRobotIsMoved(event.getRobot());
            if(!(event.getToCell() instanceof ExitCell)){ // !!! Повтор кода, см. обработчик ниже
                                                          // DONE: Так как исправил замечание ниже, повтора нет, так как здесь статус определяется толькл при выполнению условия
                updateGameState();
            }                    // !!! Почему не метод passMoveNextRobot()?
                                 // DONE: Использую passMoveNextRobot
        }

        @Override
        public void robotSkippedStep(@NotNull RobotActionEvent event) {
            fireRobotIsSkipStep(event.getRobot());
            updateGameState();
        }

        @Override
        public void robotActivityChanged(@NotNull RobotActionEvent event) {
            // Not implemented yet
        }

        @Override
        public void robotChangedPowerSupply(@NotNull RobotActionEvent event) {
            // Not implemented yet
        }

        @Override
        public void robotChargedPowerSupply(@NotNull RobotActionEvent event) {
            // Not implemented yet
        }
    }

    /**
     * Класс, реализующий наблюдение за событиями {@link FieldActionListener}.
     */
    private class FieldObserver implements FieldActionListener {

        @Override
        public void robotIsTeleported(@NotNull FieldActionEvent event) {
            updateGameState();
            fireRobotIsTeleported(event.getRobot()); // !!! Странный порядок генерации события - обработчики этого события не увидят изменения состояния игры.
                                                     // DONE: Исправил порядок генерации события: сначала изменяется обстановка игры, а потом пробрасывается событие о телеортации робота.
        }
    }

    /**
     * Список слушателей, подписанных на события игры.
     */
    private final ArrayList<GameActionListener> gameActionListeners = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями игры.
     * @param listener слушатель.
     */
    public void addGameActionListener(@NotNull GameActionListener listener) {
        gameActionListeners.add(listener);
    }

    /**
     * Удалить слушателя за событиями игры.
     * @param listener слушатель.
     */
    public void removeGameActionListener(@NotNull GameActionListener listener) {
        gameActionListeners.remove(listener);
    }

    /**
     * Оповестить сулшателей {@link Game#gameActionListeners}, что робот переместился.
     * @param robot робот, который переместился.
     */
    private void fireRobotIsMoved(@NotNull Robot robot) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setRobot(robot);
            listener.robotIsMoved(event);
        }
    }

    /**
     * Оповестить сулшателей {@link Game#gameActionListeners}, что робот пропустил ход.
     * @param robot робот, который пропустил ход.
     */
    private void fireRobotIsSkipStep(@NotNull Robot robot) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setRobot(robot);
            listener.robotIsSkipStep(event);
        }
    }

    /**
     * Оповестить сулшателей {@link Game#gameActionListeners}, что робот телепортировался.
     * @param robot робот, который телепортировался.
     */
    private void fireRobotIsTeleported(@NotNull Robot robot) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setRobot(robot);
            listener.robotIsTeleported(event);
        }
    }

    /**
     * Оповестить сулшателей {@link Game#gameActionListeners}, что статус игры изменился.
     * @param status статус игры.
     */
    private void fireGameStatusIsChanged(@NotNull GameStatus status) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setStatus(status);
            listener.gameStatusChanged(event);
        }
    }
}
