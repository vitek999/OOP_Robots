package robots;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    private Cell cell;

    public CellTest() {
    }


    @BeforeEach
    private void testSetup() {
        cell = new Cell();
    }

    @Test
    public void test_setRobotInEmptyCell() {
        Robot robot = new Robot();

        cell.setRobot(robot);

        assertEquals(robot, cell.getRobot());
        assertEquals(cell, robot.getPosition());
    }

    @Test
    public void test_takeRobotFromCell() {
        Robot robot = new Robot();

        cell.setRobot(robot);

        assertEquals(robot, cell.takeRobot());
        assertNull(robot.getPosition());
        assertNull(cell.getRobot());
    }


    @Test
    public void test_setRobotToCellWithRobot() {
        Robot robot = new Robot();
        Robot newRobot = new Robot();

        cell.setRobot(robot);

        assertThrows(IllegalArgumentException.class, () -> cell.setRobot(newRobot));
        assertEquals(robot, cell.getRobot());
        assertEquals(cell, robot.getPosition());
        assertNull(newRobot.getPosition());
    }

    @Test
    public void test_setBattery() {
        Battery battery = new Battery(10);

        cell.setBattery(battery);

        assertEquals(battery, cell.getBattery());
    }

    @Test
    public void test_setNeighborCell() {
        Cell neighborCell = new Cell();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);

        assertEquals(neighborCell, cell.neighborCell(direction));
        assertEquals(cell, neighborCell.neighborCell(direction.getOppositeDirection()));
    }

}