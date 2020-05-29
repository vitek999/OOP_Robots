package robots.model.field;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.model.Direction;
import robots.model.Point;
import robots.model.event.FieldActionEvent;
import robots.model.event.FieldActionListener;
import robots.model.field.cell_objects.power_supplies.Battery;
import robots.model.field.cell_objects.Robot;
import robots.model.field.cell_objects.power_supplies.Windmill;
import robots.model.field.cells.CellWithPowerSupply;
import robots.model.field.cells.ExitCell;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private int eventCount = 0;

    class FieldObserver implements FieldActionListener {

        @Override
        public void robotIsTeleported(@NotNull FieldActionEvent event) {
            eventCount += 1;
        }
    }

    private Field field;

    @BeforeEach
    public void testSetup() {
        eventCount = 0;
        field = new Field(2, 2, new Point(1, 1));
        field.addFieldlActionListener(new FieldObserver());
    }

    @Test
    public void test_create_withCorrectParams() {
        Cell cell_0_0 = field.getCell(new Point(0, 0));
        Cell cell_0_1 = field.getCell(new Point(1, 0));
        Cell cell_1_0 = field.getCell(new Point(0, 1));
        Cell cell_1_1 = field.getCell(new Point(1, 1));

        Assertions.assertEquals(Direction.SOUTH, cell_0_0.getNeighborDirection(cell_1_0));
        assertEquals(Direction.SOUTH, cell_0_1.getNeighborDirection(cell_1_1));
        assertEquals(Direction.NORTH, cell_1_1.getNeighborDirection(cell_0_1));
        assertEquals(Direction.NORTH, cell_1_0.getNeighborDirection(cell_0_0));
        assertEquals(Direction.EAST, cell_0_0.getNeighborDirection(cell_0_1));
        assertEquals(Direction.EAST, cell_1_0.getNeighborDirection(cell_1_1));
        assertEquals(Direction.WEST, cell_0_1.getNeighborDirection(cell_0_0));
        assertEquals(Direction.WEST, cell_1_1.getNeighborDirection(cell_1_0));
        assertTrue(cell_1_1 instanceof ExitCell);
    }

    @Test
    public void test_create_withNegativeWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(-1, 1, new Point(0, 0)));
    }

    @Test
    public void test_create_withZeroWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(0, 1, new Point(0, 0)));
    }

    @Test
    public void test_create_withNegativeHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Field(1, -1, new Point(0, 0)));
    }

    @Test
    public void test_create_withZeroHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Field(1, 0, new Point(0, 0)));
    }

    @Test
    public void test_create_withIncorrectExitPoint() {
        assertThrows(IllegalArgumentException.class, () -> new Field(1, 1, new Point(2, 2)));
    }

    @Test
    public void test_getRobotsOnField_empty() {
        assertTrue(field.getRobotsOnField().isEmpty());
    }

    @Test
    public void test_getRobotsOnField_oneRobot() {
        Robot robot = new Robot(new Battery(10));
        field.getCell(new Point(0, 0)).addObject(robot);

        assertTrue(field.getRobotsOnField().contains(robot));
        assertEquals(1, field.getRobotsOnField().size());
    }

    @Test
    public void test_getRobotsOnField_severalRobots() {
        Robot robot = new Robot(new Battery(10));
        Robot anotherRobot = new Robot(new Battery(10));
        field.getCell(new Point(0, 0)).addObject(robot);
        field.getCell(new Point(1, 0)).addObject(anotherRobot);

        assertTrue(field.getRobotsOnField().containsAll(Arrays.asList(robot, anotherRobot)));
        assertEquals(2, field.getRobotsOnField().size());
    }

    @Test
    public void test_getTeleportedRobots_empty() {
        assertTrue(field.getTeleportedRobots().isEmpty());
    }

    @Test
    public void test_TeleportedRobots_oneRobot() {
        Robot robot = new Robot(new Battery(10));
        field.getCell(new Point(1, 1)).addObject(robot);

        assertTrue(field.getTeleportedRobots().contains(robot));
        assertEquals(1, field.getTeleportedRobots().size());
    }

    @Test
    public void test_TeleportedRobots_severalRobots() {
        Robot robot = new Robot(new Battery(10));
        Robot anotherRobot = new Robot(new Battery(10));
        Cell exitCell =  field.getCell(new Point(1, 1));

        exitCell.addObject(robot);
        exitCell.addObject(anotherRobot);

        assertTrue(field.getTeleportedRobots().containsAll(Arrays.asList(robot, anotherRobot)));
        assertEquals(2, field.getTeleportedRobots().size());
    }

    @Test
    public void test_teleportEvent_oneRobot() {
        int expectedEventCount = 1;
        Robot robot = new Robot(new Battery(10));

        field.getCell(new Point(1, 1)).addObject(robot);

        assertEquals(expectedEventCount, eventCount);
    }

    @Test
    public void test_teleportEvent_TwoRobots() {
        int expectedEventCount = 2;
        Robot robot = new Robot(new Battery(10));
        Robot anotherRobot = new Robot(new Battery(10));

        field.getCell(new Point(1, 1)).addObject(robot);
        field.getCell(new Point(1, 1)).addObject(anotherRobot);

        assertEquals(expectedEventCount, eventCount);
    }

    @Test
    public void test_updateRenewablePowerSupplies_singleWindMill() {
        field.getCell(new Point(0,0)).addObject(new Windmill(0, 10));
        field.updateRenewablePowerSupplies();

        int charge = ((CellWithPowerSupply) field.getCell(new Point(0,0))).getPowerSupply().getCharge();

        int expectedCharge = 2;
        assertEquals(expectedCharge, charge);
    }

    @Test
    public void test_updateRenewablePowerSupplies_severalWindMill() {
        field.getCell(new Point(0,0)).addObject(new Windmill(0, 10));
        field.getCell(new Point(1,0)).addObject(new Windmill(0, 10));
        field.updateRenewablePowerSupplies();

        int firstWindmillCharge = ((CellWithPowerSupply) field.getCell(new Point(0,0))).getPowerSupply().getCharge();
        int secondWindmillCharge = ((CellWithPowerSupply) field.getCell(new Point(1,0))).getPowerSupply().getCharge();

        int expectedCharge = 2;
        assertEquals(expectedCharge, firstWindmillCharge);
        assertEquals(expectedCharge, secondWindmillCharge);
    }
}
