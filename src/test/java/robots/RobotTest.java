package robots;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    private final static int DEFAULT_TEST_BATTERY_CHARGE = 10;
    private static final int AMOUNT_OF_CHARGE_FOR_MOVE = 1;
    private static final int AMOUNT_OF_CHARGE_FOR_SKIP_STEP = 2;

    private Robot robot;

    @BeforeEach
    public void testSetup() {
        robot = new Robot();
    }

    @Test
    public void test_setActiveAndIsActive() {
        robot.setActive(true);
        assertTrue(robot.isActive());
    }

    @Test
    public void test_canStayAtPosition_emptyCell() {
        Cell cell = new Cell();

        assertTrue(Robot.canStayAtPosition(cell));
    }

    @Test
    public void test_canStayAtPosition_cellWithRobot() {
        Cell cell = new Cell();
        cell.setRobot(robot);

        assertFalse(Robot.canStayAtPosition(cell));
    }

    @Test
    public void test_canStayAtPosition_cellWithBattery() {
        Cell cell = new Cell();
        cell.setBattery(new Battery(DEFAULT_TEST_BATTERY_CHARGE));

        assertTrue(Robot.canStayAtPosition(cell));
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotActiveAndEnoughCharge() {
        Direction direction = Direction.NORTH;
        Cell cell = new Cell();
        Cell neighborCell = new Cell();
        cell.setNeighbor(neighborCell, direction);
        cell.setRobot(robot);

        robot.setBattery(new Battery(DEFAULT_TEST_BATTERY_CHARGE));
        robot.setActive(true);
        robot.move(direction);


        assertEquals(robot, neighborCell.getRobot());
        assertEquals(neighborCell, robot.getPosition());
        assertNull(cell.getRobot());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE - AMOUNT_OF_CHARGE_FOR_MOVE, robot.getCharge());
    }

    @Test
    public void test_move_noCellInDirectionAndRobotActiveAndEnoughCharge() {
        Cell cell = new Cell();
        cell.setRobot(robot);

        robot.setBattery(new Battery(DEFAULT_TEST_BATTERY_CHARGE));
        robot.setActive(true);
        robot.move(Direction.NORTH);

        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
        assertEquals(cell, robot.getPosition());
        assertEquals(robot, cell.getRobot());
    }

    @Test
    public void test_move_emptyCellInDirectionWithWallAndRobotActiveAndEnoughCharge(){
        Direction direction = Direction.NORTH;
        Cell cell = new Cell();
        Cell neighborCell = new Cell();
        cell.setNeighbor(neighborCell, direction);
        cell.setRobot(robot);
        new Wall(new WallPosition(cell, neighborCell));

        robot.setBattery(new Battery(DEFAULT_TEST_BATTERY_CHARGE));
        robot.setActive(true);
        robot.move(direction);


        assertEquals(robot, cell.getRobot());
        assertEquals(cell, robot.getPosition());
        assertNull(neighborCell.getRobot());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotNotActiveAndEnoughCharge() {
        Direction direction = Direction.NORTH;
        Cell cell = new Cell();
        Cell neighborCell = new Cell();
        cell.setNeighbor(neighborCell, direction);
        cell.setRobot(robot);

        robot.setBattery(new Battery(DEFAULT_TEST_BATTERY_CHARGE));
        robot.setActive(false);
        robot.move(direction);


        assertEquals(robot, cell.getRobot());
        assertEquals(cell, robot.getPosition());
        assertNull(neighborCell.getRobot());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotActiveAndNotEnoughCharge() {
        Direction direction = Direction.NORTH;
        Cell cell = new Cell();
        Cell neighborCell = new Cell();
        cell.setNeighbor(neighborCell, direction);
        cell.setRobot(robot);

        robot.setBattery(new Battery(0));
        robot.setActive(true);
        robot.move(direction);


        assertEquals(robot, cell.getRobot());
        assertEquals(cell, robot.getPosition());
        assertNull(neighborCell.getRobot());
        assertEquals(0, robot.getCharge());
    }

    @Test
    public void test_skipStep_robotActiveEnoughCharge() {
        Cell cell = new Cell();
        cell.setRobot(robot);

        robot.setBattery(new Battery(DEFAULT_TEST_BATTERY_CHARGE));
        robot.setActive(true);

        robot.skipStep();

        assertEquals(cell, robot.getPosition());
        assertEquals(robot, cell.getRobot());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE - AMOUNT_OF_CHARGE_FOR_SKIP_STEP, robot.getCharge());
    }

    @Test
    public void test_skipStep_robotNotActiveEnoughCharge() {
        Cell cell = new Cell();
        cell.setRobot(robot);

        robot.setBattery(new Battery(DEFAULT_TEST_BATTERY_CHARGE));
        robot.setActive(false);

        robot.skipStep();

        assertEquals(cell, robot.getPosition());
        assertEquals(robot, cell.getRobot());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
    }

    @Test
    public void test_skipStep_robotActiveNotEnoughCharge() {
        Cell cell = new Cell();
        cell.setRobot(robot);

        robot.setBattery(new Battery(1));
        robot.setActive(true);

        robot.skipStep();

        assertEquals(cell, robot.getPosition());
        assertEquals(robot, cell.getRobot());
        assertEquals(0, robot.getCharge());
    }

    @Test
    public void test_changeBattery_robotIsActiveCellContainsBattery(){
        Cell cell = new Cell();
        cell.setRobot(robot);
        Battery newBattery = new Battery(5);
        cell.setBattery(newBattery);

        Battery robotBattery = new Battery(DEFAULT_TEST_BATTERY_CHARGE);
        robot.setBattery(robotBattery);
        robot.setActive(true);

        robot.changeBattery();

        assertNull(cell.getBattery());
        assertEquals(newBattery.charge(), robot.getCharge());
    }

    @Test
    public void test_changeBattery_robotIsNotActiveCellContainsBattery(){
        Cell cell = new Cell();
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
        Cell cell = new Cell();
        cell.setRobot(robot);

        Battery robotBattery = new Battery(DEFAULT_TEST_BATTERY_CHARGE);
        robot.setBattery(robotBattery);
        robot.setActive(true);

        robot.changeBattery();

        assertEquals(robotBattery.charge(), robot.getCharge());
    }
 }

