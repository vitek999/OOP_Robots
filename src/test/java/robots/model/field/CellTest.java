package robots.model.field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.model.Direction;
import robots.model.field.cell_objects.power_supplies.Battery;
import robots.model.field.cell_objects.Robot;
import robots.model.field.between_cells_objects.WallSegment;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    private Cell cell;

    public CellTest() {
    }


    @BeforeEach
    public void testSetup() {

        cell = new CellTestModel();
    }

    @Test
    public void test_setRobot_InEmptyCell() {
        Robot robot = new Robot(new Battery(10));

        cell.addObject(robot);

        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(cell, robot.getPosition());
    }

    @Test
    public void test_takeRobot_FromCellWithRobot() {
        Robot robot = new Robot(new Battery(10));

        cell.addObject(robot);

        assertEquals(robot, cell.takeObject(cell.getMobileCellObject()));
        assertNull(robot.getPosition());
        assertNull(cell.getMobileCellObject());
    }


    @Test
    public void test_setRobot_ToCellWithRobot() {
        Robot robot = new Robot(new Battery(10));
        Robot newRobot = new Robot(new Battery(10));

        cell.addObject(robot);

        assertThrows(IllegalArgumentException.class, () -> cell.addObject(newRobot));
        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(cell, robot.getPosition());
        assertNull(newRobot.getPosition());
    }

    @Test
    public void test_setRobot_ToCellAgain() {
        Robot robot = new Robot(new Battery(10));

        cell.addObject(robot);

        assertThrows(IllegalArgumentException.class, () -> cell.addObject(robot));
        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(cell, robot.getPosition());
    }

    @Test
    public void test_setNeighborCell() {
        Cell neighborCell = new CellTestModel();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);

        assertEquals(neighborCell, cell.getNeighborCell(direction));
        assertEquals(cell, neighborCell.getNeighborCell(direction.getOppositeDirection()));
    }


    @Test
    public void test_setNeighborCell_doubleSided() {
        Cell neighborCell = new CellTestModel();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);
        assertThrows(IllegalArgumentException.class, () -> neighborCell.setNeighbor(cell, direction.getOppositeDirection()));
        assertEquals(neighborCell, cell.getNeighborCell(direction));
        assertEquals(cell, neighborCell.getNeighborCell(direction.getOppositeDirection()));
    }

    @Test
    public void test_setNeighborCell_twoTimesInOneDirection() {
        Cell neighborCell = new CellTestModel();
        Cell anotherCell = new CellTestModel();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);
        assertThrows(IllegalArgumentException.class, () -> cell.setNeighbor(anotherCell, direction));
        assertEquals(neighborCell, cell.getNeighborCell(direction));
        assertEquals(cell, neighborCell.getNeighborCell(direction.getOppositeDirection()));
    }

    @Test
    public void test_setNeighborCell_alreadyNeighborWithAnotherDirection() {
        Cell neighborCell = new CellTestModel();
        Direction direction = Direction.NORTH;
        Direction anotherDirection = Direction.SOUTH;

        cell.setNeighbor(neighborCell, direction);
        assertThrows(IllegalArgumentException.class, () -> cell.setNeighbor(neighborCell, anotherDirection));
        assertEquals(neighborCell, cell.getNeighborCell(direction));
        assertEquals(cell, neighborCell.getNeighborCell(direction.getOppositeDirection()));
    }

    @Test
    public void test_setNeighborCell_setSelfAsNeighbor() {
        Direction direction = Direction.NORTH;

        assertThrows(IllegalArgumentException.class, () -> cell.setNeighbor(cell, direction));
        assertNull(cell.getNeighborCell(direction));
    }

    @Test
    public void test_isNeighbor_WhenNeighborCellExists() {
        Cell neighborCell = new CellTestModel();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);
        assertEquals(direction, cell.getNeighborDirection(neighborCell));
    }

    @Test
    public void test_isNeighbor_WhenNeighborCellNotExists() {
        Cell neighborCell = new CellTestModel();

        assertNull(cell.getNeighborDirection(neighborCell));
    }

    @Test
    public void test_setWall_inOneSingleCell() {
        Direction direction = Direction.NORTH;
        WallSegment wallSegment = new WallSegment();

        cell.setBetweenCellObject(wallSegment, direction);
        assertEquals(wallSegment, cell.getNeighborBetweenCellObject(direction));
        assertEquals(cell,wallSegment.getPosition().getNeighborCells().get(direction.getOppositeDirection()));
    }

    @Test
    public void test_setWall_InSingleWithSameWallAndAnotherDirection() {
        Direction direction = Direction.NORTH;
        WallSegment wallSegment = new WallSegment();

        cell.setBetweenCellObject(wallSegment, direction);
        assertThrows(IllegalArgumentException.class, () -> cell.setBetweenCellObject(wallSegment, Direction.SOUTH));
        assertEquals(wallSegment, cell.getNeighborBetweenCellObject(direction));
        assertEquals(cell,wallSegment.getPosition().getNeighborCells().get(direction.getOppositeDirection()));
    }

    @Test
    public void test_setWall_InSingleWithSameDirectionAndAnotherWallSegment() {
        Direction direction = Direction.NORTH;
        WallSegment wallSegment = new WallSegment();
        WallSegment anotherWallSegment = new WallSegment();

        cell.setBetweenCellObject(wallSegment, direction);
        assertThrows(IllegalArgumentException.class, () -> cell.setBetweenCellObject(anotherWallSegment, direction));
        assertEquals(wallSegment, cell.getNeighborBetweenCellObject(direction));
        assertEquals(cell,wallSegment.getPosition().getNeighborCells().get(direction.getOppositeDirection()));
    }

    @Test
    public void test_setWall_inNeighborCells() {
        Direction direction = Direction.NORTH;
        Cell neighborCell = new CellTestModel();
        WallSegment wallSegment = new WallSegment();
        cell.setNeighbor(neighborCell, direction);

        cell.setBetweenCellObject(wallSegment, direction);

        assertEquals(wallSegment, cell.getNeighborBetweenCellObject(direction));
        assertEquals(wallSegment, neighborCell.getNeighborBetweenCellObject(direction.getOppositeDirection()));
        assertEquals(cell,wallSegment.getPosition().getNeighborCells().get(direction.getOppositeDirection()));
        assertEquals(neighborCell,wallSegment.getPosition().getNeighborCells().get(direction));
    }

    @Test
    public void test_setWall_InNeighborCellsWithSameDirectionAndAnotherWallSegment() {
        Direction direction = Direction.NORTH;
        Cell neighborCell = new CellTestModel();
        cell.setNeighbor(neighborCell, direction);
        WallSegment wallSegment = new WallSegment();
        WallSegment anotherWallSegment = new WallSegment();

        cell.setBetweenCellObject(wallSegment, direction);

        assertThrows(IllegalArgumentException.class, () -> cell.setBetweenCellObject(anotherWallSegment, direction));
        assertEquals(wallSegment, cell.getNeighborBetweenCellObject(direction));
        assertEquals(wallSegment, neighborCell.getNeighborBetweenCellObject(direction.getOppositeDirection()));
        assertEquals(cell,wallSegment.getPosition().getNeighborCells().get(direction.getOppositeDirection()));
        assertEquals(neighborCell,wallSegment.getPosition().getNeighborCells().get(direction));
    }

    @Test
    public void test_setWall_InNeighborCellsWithSameWallSegmentAndAnotherDirection() {
        Direction direction = Direction.NORTH;
        Cell neighborCell = new CellTestModel();
        cell.setNeighbor(neighborCell, direction);
        WallSegment wallSegment = new WallSegment();
        Direction anotherDirection = direction.getOppositeDirection();

        cell.setBetweenCellObject(wallSegment, direction);

        assertThrows(IllegalArgumentException.class, () -> cell.setBetweenCellObject(wallSegment, anotherDirection));
        assertEquals(wallSegment, cell.getNeighborBetweenCellObject(direction));
        assertEquals(wallSegment, neighborCell.getNeighborBetweenCellObject(direction.getOppositeDirection()));
        assertEquals(cell,wallSegment.getPosition().getNeighborCells().get(direction.getOppositeDirection()));
        assertEquals(neighborCell,wallSegment.getPosition().getNeighborCells().get(direction));
    }

    @Test
    public void test_neighborWall_wallNotExists() {
        Direction direction = Direction.NORTH;

        assertNull(cell.getNeighborBetweenCellObject(direction));
    }
}