package robots;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BetweenCellsPositionTest {

    // TODO: порверка правильности возвращения направления и стены при уставноки стены (все 4 стороны), когда есть пары и когда нет пар.
    // TODO: позиция теперь не проверяет наличие стены между ячейка.

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
        assertTrue(BetweenCellsPosition.canWallSet(cell, Direction.NORTH));
    }

    @Test
    public void test_canWallSet_withCellAndDirectionOnSingleCellWithWall() {
        new Wall(new BetweenCellsPosition(cell, direction));

        assertFalse(BetweenCellsPosition.canWallSet(cell, direction));
    }

    @Test
    public void test_canWallSet_withCellAndDirectionTwoNeighborCellsWithoutWall() {
        cell.setNeighbor(neighborCell, direction);

        assertTrue(BetweenCellsPosition.canWallSet(cell, direction));
    }

    @Test
    public void test_canWallSet_withCellAndDirectionTwoNeighborCellsWithWall() {
        cell.setNeighbor(neighborCell, direction);

        new Wall(new BetweenCellsPosition(cell, direction));

        assertFalse(BetweenCellsPosition.canWallSet(cell, direction));
    }

    @Test
    public void test_canWallSet_withTwoNeighborsCellsWithoutWall() {
        cell.setNeighbor(neighborCell, direction);

        assertTrue(BetweenCellsPosition.canWallSet(cell, neighborCell));
    }

    @Test
    public void test_canWallSet_withTwoNeighborsCellsWithWall() {
        cell.setNeighbor(neighborCell, direction);

        new Wall(new BetweenCellsPosition(cell, neighborCell));

        assertFalse(BetweenCellsPosition.canWallSet(cell, neighborCell));
    }

    @Test
    public void test_canWallSet_withTwoNotNeighborsCellsWithoutWall() {
        assertFalse(BetweenCellsPosition.canWallSet(cell, neighborCell));
    }

    @Test
    public void test_create_withCellAndDirectionOnSingleCellWithWall() {
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, direction);
        new Wall(betweenCellsPosition);

        assertThrows(IllegalArgumentException.class, () -> new BetweenCellsPosition(cell, direction));
    }

    @Test
    public void test_create_withCellAndDirectionOnTwoNeighborCellsWithWall() {
        cell.setNeighbor(neighborCell, direction);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, neighborCell);
        new Wall(betweenCellsPosition);

        assertThrows(IllegalArgumentException.class, () -> new BetweenCellsPosition(cell, neighborCell));
    }

    @Test
    public void test_createAndGetNeighborCells_withCellAndDirectionOnSingleCellWithoutWall() {
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, direction);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(direction.getOppositeDirection()));
    }

    @Test
    public void test_createAndGetNeighborCells_withCellAndDirectionOnTwoNeighborCellsWithoutWall() {
        cell.setNeighbor(neighborCell, direction);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, direction);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(direction.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(direction));
    }

    @Test
    public void test_createAndGetNeighborCells_withTwoNeighborsCellsWithoutWall() {
        cell.setNeighbor(neighborCell, direction);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, neighborCell);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(direction.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(direction));
    }
}