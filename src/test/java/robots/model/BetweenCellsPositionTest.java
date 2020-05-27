package robots.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.model.BetweenCellsPosition;
import robots.model.Cell;
import robots.model.Direction;

import static org.junit.jupiter.api.Assertions.*;

class BetweenCellsPositionTest {

    private final Direction direction = Direction.NORTH;

    private Cell cell;
    private Cell neighborCell;

    @BeforeEach
    public void testSetup() {
        cell = new Cell();
        neighborCell = new Cell();
    }

    @Test
    public void test_createAndGetNeighborCells_withCellAndDirectionOnSingleCell() {
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, direction);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(direction.getOppositeDirection()));
    }

    @Test
    public void test_createAndGetNeighborCells_withCellAndDirectionOnTwoNeighborCells() {
        cell.setNeighbor(neighborCell, direction);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, direction);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(direction.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(direction));
    }

    @Test
    public void test_createAndGetNeighborCells_withTwoNeighborsCells() {
        cell.setNeighbor(neighborCell, direction);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, neighborCell);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(direction.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(direction));
    }

    @Test
    public void test_createAndGetNeighborCells_toNorthOnSingleCell() {
        Direction north = Direction.NORTH;
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, north);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(north.getOppositeDirection()));
    }

    @Test
    public void test_createAndGetNeighborCells_toSouthOnSingleCell() {
        Direction south = Direction.SOUTH;
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, south);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(south.getOppositeDirection()));
    }

    @Test
    public void test_createAndGetNeighborCells_toEastOnSingleCell() {
        Direction east = Direction.EAST;
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, east);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(east.getOppositeDirection()));
    }

    @Test
    public void test_createAndGetNeighborCells_toWestOnSingleCell() {
        Direction west = Direction.WEST;
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, west);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(west.getOppositeDirection()));
    }

    @Test
    public void test_createAndGetNeighborCells_toNorthOnTwoNeighborCells() {
        Direction north = Direction.NORTH;
        cell.setNeighbor(neighborCell, north);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, neighborCell);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(north.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(north));
    }

    @Test
    public void test_createAndGetNeighborCells_toSouthOnTwoNeighborCells() {
        Direction south = Direction.SOUTH;
        cell.setNeighbor(neighborCell, south);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, neighborCell);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(south.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(south));
    }

    @Test
    public void test_createAndGetNeighborCells_toEastOnTwoNeighborCells() {
        Direction east = Direction.EAST;
        cell.setNeighbor(neighborCell, east);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, neighborCell);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(east.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(east));
    }

    @Test
    public void test_createAndGetNeighborCells_toWestOnTwoNeighborCells() {
        Direction west = Direction.WEST;
        cell.setNeighbor(neighborCell, west);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, neighborCell);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(west.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(west));
    }

    @Test
    public void test_createAndGetNeighborCells_toNorthOnTwoNeighborCellsByCellAndDirection() {
        Direction north = Direction.NORTH;
        cell.setNeighbor(neighborCell, north);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, north);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(north.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(north));
    }

    @Test
    public void test_createAndGetNeighborCells_toSouthOnTwoNeighborCellsByCellAndDirection() {
        Direction south = Direction.SOUTH;
        cell.setNeighbor(neighborCell, south);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, south);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(south.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(south));
    }

    @Test
    public void test_createAndGetNeighborCells_toEastOnTwoNeighborCellsByCellAndDirection() {
        Direction east = Direction.EAST;
        cell.setNeighbor(neighborCell, east);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, east);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(east.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(east));
    }

    @Test
    public void test_createAndGetNeighborCells_toWestOnTwoNeighborCellsByCellAndDirection() {
        Direction west = Direction.WEST;
        cell.setNeighbor(neighborCell, west);
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(cell, west);

        assertEquals(cell, betweenCellsPosition.getNeighborCells().get(west.getOppositeDirection()));
        assertEquals(neighborCell, betweenCellsPosition.getNeighborCells().get(west));
    }
}