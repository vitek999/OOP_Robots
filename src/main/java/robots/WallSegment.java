package robots;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class WallSegment { // !!! Все-таки не стена, а ее часть
                           // DONE: Переименовал класс Wall -> WallSegment

    private BetweenCellsPosition position;

    public WallSegment(@NotNull BetweenCellsPosition position) {
        if(!canCreateWall(position)) throw new IllegalArgumentException();

        this.position = position;

        Map<Direction, Cell> neighborCells = position.getNeighborCells();
        // !!! Ниже - это ответственность стены?? TODO
        for (var i : neighborCells.entrySet()) { // !!! Может добавить итератор? TODO
            i.getValue().setNeighbor(this, i.getKey().getOppositeDirection());
        }
    }

    public BetweenCellsPosition getPosition() {
        return position;
    }

    private static boolean canCreateWall(@NotNull BetweenCellsPosition position) { // !!! Идею реализации метода не понял TODO
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
        WallSegment wallSegment = (WallSegment) o;
        return Objects.equals(position, wallSegment.position);
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
