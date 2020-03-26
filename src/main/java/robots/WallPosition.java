package robots;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class WallPosition {

    private Map<Direction, Cell> neighborCells = new EnumMap<>(Direction.class);

    public WallPosition(@NotNull Cell cell, @NotNull Cell neighborCell) {
        if(!canWallSet(cell, neighborCell)) throw new IllegalArgumentException();

        Direction neighborDirection = cell.isNeighbor(neighborCell);
        neighborCells.put(neighborDirection, neighborCell);
        neighborCells.put(neighborDirection.getOppositeDirection(), cell);
    }

    public WallPosition(@NotNull Cell cell, @NotNull Direction direction) {
        if(!canWallSet(cell, direction)) throw new IllegalArgumentException();

        neighborCells.put(direction.getOppositeDirection(), cell);

        Cell neighborCell = cell.neighborCell(direction);
        if(neighborCell != null) {
            neighborCells.put(direction, neighborCell);
        }
    }

    public Map<Direction, Cell> getNeighborCells () {
        return new EnumMap<>(neighborCells);
    }

    public static boolean canWallSet(@NotNull Cell cell, @NotNull Direction direction) {
        Cell neighborCell = cell.neighborCell(direction);
        boolean result = cell.neighborWall(direction) == null;
        if(neighborCell != null){
            result = neighborCell.neighborWall(direction.getOppositeDirection()) == null;
        }
        return result;
    }

    public static boolean canWallSet(@NotNull Cell cell, @NotNull Cell neighborCell) {
        Direction neighborDirection = cell.isNeighbor(neighborCell);
        return  (neighborDirection != null) &&
                (cell.neighborWall(neighborDirection) == null) &&
                (neighborCell.neighborWall(neighborDirection.getOppositeDirection()) == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WallPosition that = (WallPosition) o;
        return Objects.equals(neighborCells, that.neighborCells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighborCells);
    }

    @Override
    public String toString() {
        return "WallPosition{" +
                "neighborCells=" + neighborCells +
                '}';
    }
}
