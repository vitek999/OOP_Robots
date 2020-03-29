package robots;

import org.jetbrains.annotations.NotNull;
import robots.event.FieldActionEvent;
import robots.event.FieldActionListener;
import robots.event.RobotActionEvent;
import robots.event.RobotActionListener;

import java.util.List;

public class Game {

    private GameStatus gameStatus;
    private Robot activeRobot;
    private Robot winner;
    private Field gameField;

    public Game() {
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
        if (gameStatus != GameStatus.GAME_IS_ON) throw new RuntimeException("Game not is on");
        gameStatus = GameStatus.GAME_FINISHED_AHEAD_OF_SCHEDULE;
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

    private void passMoveNextRobot() {
        if (gameStatus != GameStatus.GAME_IS_ON) throw new RuntimeException("Game not is on");
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
            result = GameStatus.WINNER_FOUND;
            winner = teleportedRobots.get(0);
        }

        return result;
    }

    private boolean robotsHasLowBattery(List<Robot> robots) {
        boolean result = true;

        for(int i = 0; i < robots.size() && result; ++i) {
            result = robots.get(i).getCharge() == 0;
        }

        return result;
    }

    private void buildField() {
        // TODO: why it's constants? may be make as method params
        gameField = new Field(10, 10, new Point(8, 8));

        // Set robots
        Robot firstRobot = new Robot();
        firstRobot.setBattery(new Battery(10));
        gameField.getCell(new Point(5,5)).setRobot(firstRobot);

        Robot secondRobot = new Robot();
        secondRobot.setBattery(new Battery(5));
        gameField.getCell(new Point(7,7)).setRobot(secondRobot);

        // set wall
        new Wall(new BetweenCellsPosition(
                gameField.getCell(new Point(8,8)),
                gameField.getCell(new Point(8,7))));

        // set battery
        gameField.getCell(new Point(6,6)).setBattery(new Battery(7));
    }

    private void setWinner(@NotNull Robot robot) {
        if (gameStatus != GameStatus.GAME_IS_ON) throw new RuntimeException("Game not is on");
        winner = robot;
        gameStatus = GameStatus.WINNER_FOUND;

        // Deactivate all robots?
        activeRobot = null;
        robot.setActive(false);
    }

    private void setActiveRobot(@NotNull Robot robot) {
        if (gameStatus != GameStatus.GAME_IS_ON) throw new RuntimeException("Game not is on");

        if (activeRobot != null) activeRobot.setActive(false);
        activeRobot = robot;
        robot.setActive(true);
    }

    private class RobotObserver implements RobotActionListener {

        @Override
        public void robotIsMoved(RobotActionEvent event) {
            gameStatus = determineOutcomeGame();

            if(!(event.getRobot().getPosition() instanceof ExitCell) && gameStatus == GameStatus.GAME_IS_ON){
                passMoveNextRobot();
            }
        }

        @Override
        public void robotIsSkipStep(RobotActionEvent event) {
            gameStatus = determineOutcomeGame();

            if(gameStatus == GameStatus.GAME_IS_ON)
                passMoveNextRobot();
        }
    }

    private class FieldObserver implements FieldActionListener {

        @Override
        public void robotIsTeleported(FieldActionEvent event) {
            gameStatus = determineOutcomeGame();

            if(gameStatus == GameStatus.GAME_IS_ON)
                passMoveNextRobot();
        }
    }
}
