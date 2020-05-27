package robots.model;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class WallSegment { // !!! Все-таки не стена, а ее часть
                           // DONE: Переименовал класс Wall -> WallSegment

    private BetweenCellsPosition position;

    void setPosition(@NotNull BetweenCellsPosition position) {
        this.position = position;
    }

    public BetweenCellsPosition getPosition() {
        return position;
    }

    public boolean canSetAtPosition(@NotNull BetweenCellsPosition position) {    // !!! Идею реализации метода не понял TODO
                                                                                      // DONE: Метод проверяет, есть ли у ячеек, входящих в позицию уже установленные стены в данном направлении
        boolean result = true;
        Map<Direction, Cell> neighborCells = position.getNeighborCells();

        var iterator = neighborCells.entrySet().iterator();

        while (iterator.hasNext() && result) {
            var i = iterator.next();
            WallSegment neighborWall = i.getValue().neighborWall(i.getKey().getOppositeDirection());
            result = (neighborWall == null) || (neighborWall == this);
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
