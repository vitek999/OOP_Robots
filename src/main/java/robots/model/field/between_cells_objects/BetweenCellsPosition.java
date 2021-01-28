package robots.model.field.between_cells_objects;

import org.jetbrains.annotations.NotNull;
import robots.model.Direction;
import robots.model.field.Cell;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Класс позиции между ячейками {@link Cell}
 */
public class BetweenCellsPosition {

    /**
     * Сосдение ячейки.
     */
    private final Map<Direction, Cell> neighborCells = new EnumMap<>(Direction.class);

    /**
     * Конструтор класса позиции между ячейками.
     * @param cell ячейка.
     * @param neighborCell сосеняя ячейка.
     * @throws IllegalArgumentException если ячейки не являются соседними.
     */
    public BetweenCellsPosition(@NotNull Cell cell, @NotNull Cell neighborCell) {
        Direction neighborDirection = cell.getNeighborDirection(neighborCell);

        if(neighborDirection == null) throw new IllegalArgumentException();

        // !!! Снова определяем соседство - см canWallSet
        // DONE: Вынес повторяющуюся логику в cellWallSet перед проверкой на действительное соседство между ячейками
        neighborCells.put(neighborDirection, neighborCell);
        neighborCells.put(neighborDirection.getOppositeDirection(), cell);
    }

    /**
     * Конструтор класса позиции между ячейками.
     * @param cell ячейка.
     * @param direction направление.
     */
    public BetweenCellsPosition(@NotNull Cell cell, @NotNull Direction direction) {

        neighborCells.put(direction.getOppositeDirection(), cell);

        Cell neighborCell = cell.getNeighborCell(direction);
        if(neighborCell != null) {
            neighborCells.put(direction, neighborCell);
        }
    }

    /**
     * Получить сосдение ячейки {@link BetweenCellsPosition#neighborCells}.
     * @return сосдение ячейки.
     */
    public Map<Direction, Cell> getNeighborCells () {
        return Collections.unmodifiableMap(neighborCells); // !!! Можно возвращать неизменяемый контейнер
                                                           // DONE: Возвращаю неизменяемый контейнер unmodifiableMap
    }

    // !!! Позиция НИКАК не может знать про стену
    // DONE: Перенёс логику canWallSet в конструктор BetweenCellsPosition

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetweenCellsPosition that = (BetweenCellsPosition) o;
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
