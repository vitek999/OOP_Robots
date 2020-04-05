package robots;

import org.jetbrains.annotations.NotNull;
import robots.event.*;
import robots.labirints.Labirint;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private GameStatus gameStatus;
    private Robot activeRobot;
    private Robot winner;
    private Field gameField;
    private Labirint labirint;

    public Game(Labirint labirint) {
        this.labirint = labirint;

        initGame();
    }

    private void initGame() {
        gameStatus = GameStatus.GAME_IS_ON;

        buildField();

        gameField.addFieldlActionListener(new FieldObserver());

        for(var i : gameField.getRobotsOnField()) {
            i.addRobotActionListener(new RobotObserver());
        }

        // set active robot
        passMoveNextRobot();
    }

    public void finish() {
        gameStatus = GameStatus.GAME_FINISHED_AHEAD_OF_SCHEDULE;
        setActiveRobot(null);
    }

    public GameStatus gameStatus() {
        return gameStatus;
    }

    public Robot winner() {
        return winner;
    }

    public Robot activeRobot() {
        return activeRobot;
    }

    public List<Robot> getRobotsOnField() {
        return new ArrayList<>(gameField.getRobotsOnField());
    }

    public List<Robot> getTeleportedRobots() {
        return new ArrayList<>(gameField.getTeleportedRobots());
    }

    private void passMoveNextRobot() {
        List<Robot> robotsOnField = gameField.getRobotsOnField();

        if(robotsOnField.size() == 1 ) {
            Robot robot = robotsOnField.get(0);
            if(robot.getCharge() > 0) setActiveRobot(robot);
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

        if(!robotsOnField.isEmpty() && teleportedRobots.isEmpty() && robotsHasLowBattery(robotsOnField)) {
            result = GameStatus.ALL_ROBOTS_HAVE_LOW_BATTERIES;
        }

        if(teleportedRobots.size() == 1 && robotsHasLowBattery(robotsOnField)) {
            setWinner(teleportedRobots.get(0));
        }

        return result;
    }

    private boolean robotsHasLowBattery(@NotNull List<Robot> robots) {
        boolean result = true;

        for(int i = 0; i < robots.size() && result; ++i) {
            result = robots.get(i).getCharge() == 0;
        }

        return result;
    }

    private void buildField() {
        gameField = labirint.buildField();
    }

    private void setWinner(@NotNull Robot robot) {

        winner = robot;

        gameStatus = GameStatus.WINNER_FOUND;

        setActiveRobot(null);
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
            gameStatus = determineOutcomeGame();

            fireRobotIsMoved(event.getRobot());

            if(!(event.getRobot().getPosition() instanceof ExitCell)){
                passMoveNextRobot();
            }
        }

        @Override
        public void robotIsSkipStep(@NotNull RobotActionEvent event) {
            gameStatus = determineOutcomeGame();

            fireRobotIsSkipStep(event.getRobot());

            passMoveNextRobot();
        }
    }

    private class FieldObserver implements FieldActionListener {

        @Override
        public void robotIsTeleported(@NotNull FieldActionEvent event) {
            gameStatus = determineOutcomeGame();

            fireRobotIsTeleported(event.getRobot());

            passMoveNextRobot();
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
}
