package robots;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    private Cell cell;
    private Cell neighborCell;
    private final Direction direction = Direction.NORTH;

    // TODO: тесировать события через логический флаг.

    private final static int DEFAULT_TEST_BATTERY_CHARGE = 10;
    private static final int AMOUNT_OF_CHARGE_FOR_MOVE = 1;
    private static final int AMOUNT_OF_CHARGE_FOR_SKIP_STEP = 2;

    private Robot robot;

    @BeforeEach
    public void testSetup() {
        robot = new Robot();
        robot.setActive(true);
        robot.setBattery(new Battery(DEFAULT_TEST_BATTERY_CHARGE));
        cell = new Cell();
        neighborCell = new Cell();
        cell.setNeighbor(neighborCell, direction);
    }

    @Test
    public void test_setActiveAndIsActive() {
        robot.setActive(true);
        assertTrue(robot.isActive());
    }

    @Test
    public void test_canStayAtPosition_emptyCell() {
        assertTrue(Robot.canStayAtPosition(cell));
    }

    @Test
    public void test_canStayAtPosition_cellWithRobot() {
        cell.setRobot(robot);

        assertFalse(Robot.canStayAtPosition(cell));
    }

    @Test
    public void test_canStayAtPosition_cellWithBattery() {
        cell.setBattery(new Battery(DEFAULT_TEST_BATTERY_CHARGE));

        assertTrue(Robot.canStayAtPosition(cell));
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotActiveAndEnoughCharge() {
        cell.setRobot(robot);

        robot.move(direction);

        assertEquals(robot, neighborCell.getRobot());
        assertEquals(neighborCell, robot.getPosition());
        assertNull(cell.getRobot());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE - AMOUNT_OF_CHARGE_FOR_MOVE, robot.getCharge());
    }

    @Test
    public void test_move_noCellInDirectionAndRobotActiveAndEnoughCharge() {
        neighborCell.setRobot(robot);

        robot.move(Direction.NORTH);

        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
        assertEquals(neighborCell, robot.getPosition());
        assertEquals(robot, neighborCell.getRobot());
    }

    @Test
    public void test_move_emptyCellInDirectionWithWallAndRobotActiveAndEnoughCharge(){
        cell.setRobot(robot);
        new Wall(new BetweenCellsPosition(cell, neighborCell));

        robot.setBattery(new Battery(DEFAULT_TEST_BATTERY_CHARGE));
        robot.move(direction);


        assertEquals(robot, cell.getRobot());
        assertEquals(cell, robot.getPosition());
        assertNull(neighborCell.getRobot());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotNotActiveAndEnoughCharge() {
        cell.setRobot(robot);

        robot.setActive(false);
        robot.move(direction);

        assertEquals(robot, cell.getRobot());
        assertEquals(cell, robot.getPosition());
        assertNull(neighborCell.getRobot());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotActiveAndNotEnoughCharge() {
        cell.setRobot(robot);

        robot.setBattery(new Battery(0));
        robot.move(direction);

        assertEquals(robot, cell.getRobot());
        assertEquals(cell, robot.getPosition());
        assertNull(neighborCell.getRobot());
        assertEquals(0, robot.getCharge());
    }

    @Test
    public void test_skipStep_robotActiveEnoughCharge() {
        cell.setRobot(robot);

        robot.skipStep();

        assertEquals(cell, robot.getPosition());
        assertEquals(robot, cell.getRobot());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE - AMOUNT_OF_CHARGE_FOR_SKIP_STEP, robot.getCharge());
    }

    @Test
    public void test_skipStep_robotNotActiveEnoughCharge() {
        cell.setRobot(robot);

        robot.setActive(false);

        robot.skipStep();

        assertEquals(cell, robot.getPosition());
        assertEquals(robot, cell.getRobot());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
    }

    @Test
    public void test_skipStep_robotActiveNotEnoughCharge() {
        cell.setRobot(robot);

        robot.setBattery(new Battery(1));

        robot.skipStep();

        assertEquals(cell, robot.getPosition());
        assertEquals(robot, cell.getRobot());
        assertEquals(0, robot.getCharge());
    }

    @Test
    public void test_changeBattery_robotIsActiveCellContainsBattery(){
        cell.setRobot(robot);
        Battery newBattery = new Battery(5);
        cell.setBattery(newBattery);

        robot.changeBattery();

        assertNull(cell.getBattery());
        assertEquals(newBattery.charge(), robot.getCharge());
    }

    @Test
    public void test_changeBattery_robotIsNotActiveCellContainsBattery(){
        cell.setRobot(robot);
        Battery newBattery = new Battery(5);
        cell.setBattery(newBattery);

        Battery robotBattery = new Battery(DEFAULT_TEST_BATTERY_CHARGE);
        robot.setBattery(robotBattery);
        robot.setActive(false);

        robot.changeBattery();

        assertEquals(newBattery, cell.getBattery());
        assertEquals(robotBattery.charge(), robot.getCharge());
    }

    @Test
    public void test_changeBattery_robotIsActiveCellNotContainsBattery(){
        cell.setRobot(robot);

        Battery robotBattery = new Battery(DEFAULT_TEST_BATTERY_CHARGE);
        robot.setBattery(robotBattery);

        robot.changeBattery();

        assertEquals(robotBattery.charge(), robot.getCharge());
    }
 }

