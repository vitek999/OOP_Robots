package robots.model.field;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.model.Direction;
import robots.model.event.RobotActionEvent;
import robots.model.event.RobotActionListener;
import robots.model.field.between_cells_objects.Door;
import robots.model.field.cell_objects.power_supplies.Accumulator;
import robots.model.field.cell_objects.power_supplies.Battery;
import robots.model.field.cell_objects.Robot;
import robots.model.field.between_cells_objects.WallSegment;
import robots.model.field.cell_objects.power_supplies.Windmill;
import robots.model.field.cells.CellWithPowerSupply;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    private enum EVENT {ROBOT_MOVED, ROBOT_SKIP_STEP}

    private final List<EVENT> events = new ArrayList<>();
    private final List<EVENT> expectedEvents = new ArrayList<>();

    private class EventsListener implements RobotActionListener {

        @Override
        public void robotIsMoved(@NotNull RobotActionEvent event) {
            events.add(EVENT.ROBOT_MOVED);
        }

        @Override
        public void robotSkippedStep(@NotNull RobotActionEvent event) {
            events.add(EVENT.ROBOT_SKIP_STEP);
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

    private Cell cell;
    private Cell neighborCell;
    private final Direction direction = Direction.NORTH;

    private final static int DEFAULT_TEST_BATTERY_CHARGE = 10;
    private static final int AMOUNT_OF_CHARGE_FOR_MOVE = 1;
    private static final int AMOUNT_OF_CHARGE_FOR_SKIP_STEP = 2;

    private Robot robot;

    @BeforeEach
    public void testSetup() {
        // clean events
        events.clear();
        expectedEvents.clear();

        // create robot
        robot = new Robot(new Battery(DEFAULT_TEST_BATTERY_CHARGE));
        robot.setActive(true);
        robot.addRobotActionListener(new EventsListener());

        // create field
        cell = new CellWithPowerSupply();
        neighborCell = new CellWithPowerSupply();
        cell.setNeighbor(neighborCell, direction);
    }

    @Test
    public void test_setActiveAndIsActive() {
        robot.setActive(true);

        assertTrue(robot.isActive());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_canStayAtPosition_emptyCell() {
        assertTrue(robot.canLocateAtPosition(cell));
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_canStayAtPosition_cellWithRobot() {
        cell.addObject(robot);

        assertFalse(robot.canLocateAtPosition(cell));
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_canStayAtPosition_cellWithBattery() {
        cell.addObject(new Battery(DEFAULT_TEST_BATTERY_CHARGE));

        assertTrue(robot.canLocateAtPosition(cell));
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotActiveAndEnoughCharge() {
        cell.addObject(robot);

        robot.move(direction);

        expectedEvents.add(EVENT.ROBOT_MOVED);

        assertEquals(robot, neighborCell.getMobileCellObject());
        assertEquals(neighborCell, robot.getPosition());
        assertNull(cell.getMobileCellObject());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE - AMOUNT_OF_CHARGE_FOR_MOVE, robot.getCharge());
        assertEquals(expectedEvents, events);
    }

    @Test
    public void test_move_noCellInDirectionAndRobotActiveAndEnoughCharge() {
        neighborCell.addObject(robot);

        robot.move(Direction.NORTH);

        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
        assertEquals(neighborCell, robot.getPosition());
        assertEquals(robot, neighborCell.getMobileCellObject());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_move_emptyCellInDirectionWithWallAndRobotActiveAndEnoughCharge() {
        cell.addObject(robot);
        cell.setWall(new WallSegment(), cell.getNeighborDirection(neighborCell));

        robot.setPowerSupply(new Battery(DEFAULT_TEST_BATTERY_CHARGE));
        robot.move(direction);

        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(cell, robot.getPosition());
        assertNull(neighborCell.getMobileCellObject());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotNotActiveAndEnoughCharge() {
        cell.addObject(robot);

        robot.setActive(false);
        robot.move(direction);

        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(cell, robot.getPosition());
        assertNull(neighborCell.getMobileCellObject());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotActiveAndNotEnoughCharge() {
        cell.addObject(robot);

        robot.setPowerSupply(new Battery(0));
        robot.move(direction);

        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(cell, robot.getPosition());
        assertNull(neighborCell.getMobileCellObject());
        assertEquals(0, robot.getCharge());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_skipStep_robotActiveEnoughCharge() {
        cell.addObject(robot);

        robot.skipStep();

        expectedEvents.add(EVENT.ROBOT_SKIP_STEP);

        assertEquals(cell, robot.getPosition());
        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE - AMOUNT_OF_CHARGE_FOR_SKIP_STEP, robot.getCharge());
        assertEquals(expectedEvents, events);
    }

    @Test
    public void test_skipStep_robotNotActiveEnoughCharge() {
        cell.addObject(robot);

        robot.setActive(false);

        robot.skipStep();

        assertEquals(cell, robot.getPosition());
        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_skipStep_robotActiveNotEnoughCharge() {
        cell.addObject(robot);

        robot.setPowerSupply(new Battery(1));

        robot.skipStep();

        expectedEvents.add(EVENT.ROBOT_SKIP_STEP);

        assertEquals(cell, robot.getPosition());
        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(0, robot.getCharge());
        assertEquals(expectedEvents, events);
    }

    @Test
    public void test_changeBattery_robotIsActiveCellContainsBattery() {
        cell.addObject(robot);
        Battery newBattery = new Battery(5);
        cell.addObject(newBattery);

        robot.changePowerSupply();

        assertNull(((CellWithPowerSupply) cell).getPowerSupply());
        assertEquals(newBattery.getCharge(), robot.getCharge());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_changeBattery_robotIsNotActiveCellContainsBattery() {
        cell.addObject(robot);
        Battery newBattery = new Battery(5);
        cell.addObject(newBattery);

        Battery robotBattery = new Battery(DEFAULT_TEST_BATTERY_CHARGE);
        robot.setPowerSupply(robotBattery);
        robot.setActive(false);

        robot.changePowerSupply();

        assertEquals(newBattery, ((CellWithPowerSupply) cell).getPowerSupply());
        assertEquals(robotBattery.getCharge(), robot.getCharge());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_changeBattery_robotIsActiveCellNotContainsBattery() {
        cell.addObject(robot);

        Battery robotBattery = new Battery(DEFAULT_TEST_BATTERY_CHARGE);
        robot.setPowerSupply(robotBattery);

        robot.changePowerSupply();

        assertEquals(robotBattery.getCharge(), robot.getCharge());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_chargePowerSupply_chargeAccumulatorByRenewableSource() {
        cell.addObject(robot);
        Windmill windmill = new Windmill(DEFAULT_TEST_BATTERY_CHARGE, DEFAULT_TEST_BATTERY_CHARGE);
        neighborCell.addObject(windmill);
        int accumulatorCharge = 2;
        robot.setPowerSupply(new Accumulator(accumulatorCharge, DEFAULT_TEST_BATTERY_CHARGE));

        robot.chargePowerSupply();

        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
        assertEquals(2, windmill.getCharge());
    }

    @Test
    public void test_chargePowerSupply_chargeAccumulatorByNotRenewableSource() {
        cell.addObject(robot);
        Accumulator accumulator = new Accumulator(DEFAULT_TEST_BATTERY_CHARGE, DEFAULT_TEST_BATTERY_CHARGE);
        neighborCell.addObject(accumulator);
        int accumulatorCharge = 2;
        robot.setPowerSupply(new Accumulator(accumulatorCharge, DEFAULT_TEST_BATTERY_CHARGE));

        robot.chargePowerSupply();

        assertEquals(accumulatorCharge, robot.getCharge());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, accumulator.getCharge());
    }

    @Test
    public void test_chargePowerSupply_neighborCellsWithoutPowerSupplies() {
        cell.addObject(robot);
        int accumulatorCharge = 2;
        robot.setPowerSupply(new Accumulator(accumulatorCharge, DEFAULT_TEST_BATTERY_CHARGE));

        robot.chargePowerSupply();

        assertEquals(accumulatorCharge, robot.getCharge());
    }

    @Test
    public void test_chargePowerSupply_fromSeveralPowerSupplies() {
        cell.addObject(robot);
        int windmillCharge = 2;
        Windmill firstWindmill = new Windmill(windmillCharge, DEFAULT_TEST_BATTERY_CHARGE);
        neighborCell.addObject(firstWindmill);
        Cell secondNeighbor = new CellWithPowerSupply();
        cell.setNeighbor(secondNeighbor, Direction.SOUTH);
        Windmill secondWindmill = new Windmill(windmillCharge, DEFAULT_TEST_BATTERY_CHARGE);
        secondNeighbor.addObject(secondWindmill);
        int accumulatorCharge = 2;
        robot.setPowerSupply(new Accumulator(accumulatorCharge, DEFAULT_TEST_BATTERY_CHARGE));

        robot.chargePowerSupply();

        int expectedRobotCharge = accumulatorCharge + 2*windmillCharge;
        assertEquals(expectedRobotCharge, robot.getCharge());
        assertEquals(0, firstWindmill.getCharge());
        assertEquals(0, secondWindmill.getCharge());
    }

    @Test
    public void test_chargePowerSupply_byEmptySource() {
        cell.addObject(robot);
        Windmill windmill = new Windmill(0, DEFAULT_TEST_BATTERY_CHARGE);
        neighborCell.addObject(windmill);
        int accumulatorCharge = 2;
        robot.setPowerSupply(new Accumulator(accumulatorCharge, DEFAULT_TEST_BATTERY_CHARGE));

        robot.chargePowerSupply();

        assertEquals(accumulatorCharge, robot.getCharge());
        assertEquals(0, windmill.getCharge());
    }

    @Test
    public void test_performAction_withoutDoor() {
        cell.addObject(robot);

        robot.performAction();

        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
    }

    @Test
    public void test_performAction_withSingleDoorAndEnoughCharge() {
        cell.addObject(robot);
        Door door = new Door(false);
        cell.setWall(door, Direction.NORTH);

        robot.performAction();

        int expectedRobotCharge = DEFAULT_TEST_BATTERY_CHARGE - door.actionCost();
        assertEquals(expectedRobotCharge, robot.getCharge());
        assertTrue(door.isOpen());
    }

    @Test
    public void test_performAction_withSingleDoorAndNotEnoughCharge() {
        cell.addObject(robot);
        robot.setPowerSupply(new Battery(0));
        Door door = new Door(false);
        cell.setWall(door, Direction.NORTH);

        robot.performAction();

        assertEquals(0, robot.getCharge());
        assertFalse(door.isOpen());
    }

    @Test
    public void test_performAction_withSeveralDoorAndEnoughChargeForAllActions() {
        cell.addObject(robot);
        Door firstDoor = new Door(false);
        Door secondDoor = new Door(false);
        cell.setWall(firstDoor, Direction.NORTH);
        cell.setWall(secondDoor, Direction.SOUTH);

        robot.performAction();

        int expectedRobotCharge = DEFAULT_TEST_BATTERY_CHARGE - (firstDoor.actionCost() + secondDoor.actionCost());
        assertEquals(expectedRobotCharge, robot.getCharge());
        assertTrue(firstDoor.isOpen());
        assertTrue(secondDoor.isOpen());
    }

    @Test
    public void test_performAction_withSeveralDoorAndEnoughChargeForOneActions() {
        cell.addObject(robot);
        int robotCharge = 1;
        robot.setPowerSupply(new Battery(robotCharge));
        Door firstDoor = new Door(false);
        Door secondDoor = new Door(false);
        cell.setWall(firstDoor, Direction.NORTH);
        cell.setWall(secondDoor, Direction.SOUTH);

        robot.performAction();

        int expectedRobotCharge = robotCharge - firstDoor.actionCost();
        assertEquals(expectedRobotCharge, robot.getCharge());
        assertTrue(firstDoor.isOpen());
        assertFalse(secondDoor.isOpen());
    }

    @Test
    public void test_move_toDirectionWithClosedDoor() {
        cell.addObject(robot);
        cell.setWall(new Door(false), Direction.NORTH);

        robot.move(Direction.NORTH);


        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(cell, robot.getPosition());
        assertNull(neighborCell.getMobileCellObject());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, robot.getCharge());
        assertEquals(expectedEvents, events);
    }

    @Test
    public void test_move_toDirectionWithOpenedDoor() {
        cell.addObject(robot);
        cell.setWall(new Door(true), Direction.NORTH);

        robot.move(Direction.NORTH);


        expectedEvents.add(EVENT.ROBOT_MOVED);

        assertEquals(robot, neighborCell.getMobileCellObject());
        assertEquals(neighborCell, robot.getPosition());
        assertNull(cell.getMobileCellObject());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE - AMOUNT_OF_CHARGE_FOR_MOVE, robot.getCharge());
        assertEquals(expectedEvents, events);
    }
    // TODO: canStay в ячейке с мельницей
    // TODO: changeBattery если она Portable
    // TODO: changeBattery если она не Portable
}

