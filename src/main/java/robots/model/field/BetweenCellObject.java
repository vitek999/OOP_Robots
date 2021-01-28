package robots.model.field;

import org.jetbrains.annotations.NotNull;
import robots.model.Direction;
import robots.model.field.between_cells_objects.BetweenCellsPosition;

import java.util.Map;
import java.util.Objects;

/**
 * Объект, располагающийся между ячейками {@link Cell}
 */
public abstract class BetweenCellObject {

    /**
     * Позиция ообъекта между ячейками
     */
    protected BetweenCellsPosition position;

    /**
     * Получить позицию {@link BetweenCellObject#position}.
     * @return позиция.
     */
    public BetweenCellsPosition getPosition() {
        return position;
    }

    /**
     * Установить позицию {@link BetweenCellObject#position}.
     * @param position позиция.
     */
    void setPosition(@NotNull BetweenCellsPosition position) {
        this.position = position;
    }

    /**
     * Может ли находится объект в позиции.
     * @param newPosition проверяемая позиция.
     * @return может ли находится объект в позиции.
     */
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
