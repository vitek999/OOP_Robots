package robots;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    private Cell cell;
    private Cell neighborCell;
    private final Direction direction = Direction.NORTH;

    @BeforeEach
    public void testSetup() {
        cell = new Cell();
        neighborCell = new Cell();
    }

    @Test
    public void test_create_onOneCellWithoutWallByDirection() {

        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, direction);
        Wall wal = new Wall(betweenCellsPosition);

        assertEquals(betweenCellsPosition, wal.getPosition());
        assertEquals(wal, cell.neighborWall(direction));
    }

    @Test
    public void test_create_onOneCellWithWallByDirection() {

        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, direction);
        Wall wal = new Wall(betweenCellsPosition);

        assertThrows(IllegalArgumentException.class, () -> new Wall(betweenCellsPosition));
        assertEquals(betweenCellsPosition, wal.getPosition());
        assertEquals(wal, cell.neighborWall(direction));
    }

    @Test
    public void test_create_onTwoCellsWithoutWallByDirection() {
        cell.setNeighbor(neighborCell, direction);

        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, direction);
        Wall wal = new Wall(betweenCellsPosition);

        assertEquals(betweenCellsPosition, wal.getPosition());
        assertEquals(wal, cell.neighborWall(direction));
        assertEquals(wal, neighborCell.neighborWall(direction.getOppositeDirection()));
    }

    @Test
    public void test_create_onTwoCellsWithWallByDirection() {
        cell.setNeighbor(neighborCell, direction);

        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, direction);
        Wall wal = new Wall(betweenCellsPosition);

        assertThrows(IllegalArgumentException.class, () -> new Wall(betweenCellsPosition));
        assertEquals(betweenCellsPosition, wal.getPosition());
        assertEquals(wal, cell.neighborWall(direction));
        assertEquals(wal, neighborCell.neighborWall(direction.getOppositeDirection()));
    }

    @Test
    public void test_create_onTwoCellsWithoutWallByTwoCells() {
        cell.setNeighbor(neighborCell, direction);

        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, neighborCell);
        Wall wal = new Wall(betweenCellsPosition);

        assertEquals(betweenCellsPosition, wal.getPosition());
        assertEquals(wal, cell.neighborWall(direction));
        assertEquals(wal, neighborCell.neighborWall(direction.getOppositeDirection()));
    }

    @Test
    public void test_create_onTwoCellsWithWallByTwoCells() {
        cell.setNeighbor(neighborCell, direction);

        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, neighborCell);
        Wall wal = new Wall(betweenCellsPosition);

        assertThrows(IllegalArgumentException.class, () -> new Wall(betweenCellsPosition));
        assertEquals(betweenCellsPosition, wal.getPosition());
        assertEquals(wal, cell.neighborWall(direction));
        assertEquals(wal, neighborCell.neighborWall(direction.getOppositeDirection()));
    }

    @Test
    public void test_create_withInCorrectWallPosition() {

        assertThrows(IllegalArgumentException.class, () -> new Wall(new BetweenCellsPosition(cell, neighborCell)));
        assertNull(cell.neighborWall(direction));
        assertNull(neighborCell.neighborWall(direction.getOppositeDirection()));
    }
}