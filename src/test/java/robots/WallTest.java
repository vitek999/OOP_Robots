package robots;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    @Test
    public void test_create_withCorrectWallPosition() {
        Direction direction = Direction.NORTH;
        Cell cell = new Cell();
        Cell neighborCell = new Cell();
        cell.setNeighbor(neighborCell, direction);

        WallPosition wallPosition = new WallPosition(cell, neighborCell);
        Wall wal = new Wall(wallPosition);

        assertEquals(wallPosition, wal.getPosition());
        assertEquals(wal, cell.neighborWall(direction));
        assertEquals(wal, neighborCell.neighborWall(direction.getOppositeDirection()));
    }

    @Test
    public void test_create_withInCorrectWallPosition() {
        Direction direction = Direction.NORTH;
        Cell cell = new Cell();
        Cell neighborCell = new Cell();

        assertThrows(IllegalArgumentException.class, () -> new Wall(new WallPosition(cell, neighborCell)));
        assertNull(cell.neighborWall(direction));
        assertNull(neighborCell.neighborWall(direction.getOppositeDirection()));
    }
}