package robots;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallPositionTest {

    private final Direction direction = Direction.NORTH;

    private Cell cell;
    private Cell neighborCell;

    @BeforeEach
    public void testSetup() {
        cell = new Cell();
        neighborCell = new Cell();
    }

    @Test
    public void test_canWallSet_withCellAndDirectionOnSingleCellWithoutWall() {
        assertTrue(WallPosition.canWallSet(cell, Direction.NORTH));
    }

    @Test
    public void test_canWallSet_withCellAndDirectionOnSingleCellWithWall() {
        new Wall(new WallPosition(cell, direction));

        assertFalse(WallPosition.canWallSet(cell, direction));
    }

    @Test
    public void test_canWallSet_withCellAndDirectionTwoNeighborCellsWithoutWall() {
        cell.setNeighbor(neighborCell, direction);

        assertTrue(WallPosition.canWallSet(cell, direction));
    }

    @Test
    public void test_canWallSet_withCellAndDirectionTwoNeighborCellsWithWall() {
        cell.setNeighbor(neighborCell, direction);

        new Wall(new WallPosition(cell, direction));

        assertFalse(WallPosition.canWallSet(cell, direction));
    }

    @Test
    public void test_canWallSet_withTwoNeighborsCellsWithoutWall() {
        cell.setNeighbor(neighborCell, direction);

        assertTrue(WallPosition.canWallSet(cell, neighborCell));
    }

    @Test
    public void test_canWallSet_withTwoNeighborsCellsWithWall() {
        cell.setNeighbor(neighborCell, direction);

        new Wall(new WallPosition(cell, neighborCell));

        assertFalse(WallPosition.canWallSet(cell, neighborCell));
    }

    @Test
    public void test_canWallSet_withTwoNotNeighborsCellsWithoutWall() {
        assertFalse(WallPosition.canWallSet(cell, neighborCell));
    }

    @Test
    public void test_create_withCellAndDirectionOnSingleCellWithWall() {
        WallPosition wallPosition = new WallPosition(cell, direction);
        new Wall(wallPosition);

        assertThrows(IllegalArgumentException.class, () -> new WallPosition(cell, direction));
    }

    @Test
    public void test_create_withCellAndDirectionOnTwoNeighborCellsWithWall() {
        cell.setNeighbor(neighborCell, direction);
        WallPosition wallPosition = new WallPosition(cell, neighborCell);
        new Wall(wallPosition);

        assertThrows(IllegalArgumentException.class, () -> new WallPosition(cell, neighborCell));
    }

    @Test
    public void test_createAndGetNeighborCells_withCellAndDirectionOnSingleCellWithoutWall() {
        WallPosition wallPosition = new WallPosition(cell, direction);

        assertEquals(cell, wallPosition.getNeighborCells().get(direction.getOppositeDirection()));
    }

    @Test
    public void test_createAndGetNeighborCells_withCellAndDirectionOnTwoNeighborCellsWithoutWall() {
        cell.setNeighbor(neighborCell, direction);
        WallPosition wallPosition = new WallPosition(cell, direction);

        assertEquals(cell, wallPosition.getNeighborCells().get(direction.getOppositeDirection()));
        assertEquals(neighborCell, wallPosition.getNeighborCells().get(direction));
    }

    @Test
    public void test_createAndGetNeighborCells_withTwoNeighborsCellsWithoutWall() {
        cell.setNeighbor(neighborCell, direction);
        WallPosition wallPosition = new WallPosition(cell, neighborCell);

        assertEquals(cell, wallPosition.getNeighborCells().get(direction.getOppositeDirection()));
        assertEquals(neighborCell, wallPosition.getNeighborCells().get(direction));
    }
}