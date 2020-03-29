package robots;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class Wall {

    private BetweenCellsPosition position;

    public Wall(@NotNull BetweenCellsPosition position) {
        if(!canCreateWall(position)) throw new IllegalArgumentException();

        this.position = position;

        Map<Direction, Cell> neighborCells = position.getNeighborCells();

        for (var i : neighborCells.entrySet()) {
            i.getValue().setNeighbor(this, i.getKey().getOppositeDirection());
        }
    }

    public BetweenCellsPosition getPosition() {
        return position;
    }

    private static boolean canCreateWall(@NotNull BetweenCellsPosition position) {
        boolean result = true;
        Map<Direction, Cell> neighborCells = position.getNeighborCells();

        var iterator = neighborCells.entrySet().iterator();

        while (iterator.hasNext() && result) {
            var i = iterator.next();
            result = i.getValue().neighborWall(i.getKey().getOppositeDirection()) == null;
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return Objects.equals(position, wall.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "Wall{" +
                "position=" + position +
                '}';
    }
}
