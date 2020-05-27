package robots.model.field;

import org.jetbrains.annotations.NotNull;
import robots.model.Direction;
import robots.model.field.between_cells_objects.BetweenCellsPosition;

import java.util.Map;
import java.util.Objects;

public class WallSegment extends BetweenCellObject{ // !!! Все-таки не стена, а ее часть
                           // DONE: Переименовал класс Wall -> WallSegment

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
