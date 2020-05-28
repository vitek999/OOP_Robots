package robots.model.field;

import org.jetbrains.annotations.NotNull;
import robots.model.Direction;
import robots.model.field.between_cells_objects.BetweenCellsPosition;

import java.util.Map;
import java.util.Objects;

public abstract class BetweenCellObject {

    protected BetweenCellsPosition position;

    public BetweenCellsPosition getPosition() {
        return position;
    }

    void setPosition(@NotNull BetweenCellsPosition position) {
        this.position = position;
    }

    public boolean canSetAtPosition(@NotNull BetweenCellsPosition newPosition) {
        boolean result = true;
        Map<Direction, Cell> neighborCells = newPosition.getNeighborCells();

        var iterator = neighborCells.entrySet().iterator();

        while (iterator.hasNext() && result) {
            var i = iterator.next();
            BetweenCellObject neighborWall = i.getValue().getNeighborBetweenCellObject(i.getKey().getOppositeDirection());
            result = (neighborWall == null) || (neighborWall == this);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetweenCellObject that = (BetweenCellObject) o;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
