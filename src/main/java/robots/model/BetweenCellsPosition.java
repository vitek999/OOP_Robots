package robots.model;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class BetweenCellsPosition {

    private Map<Direction, Cell> neighborCells = new EnumMap<>(Direction.class);

    public BetweenCellsPosition(@NotNull Cell cell, @NotNull Cell neighborCell) {
        Direction neighborDirection = cell.isNeighbor(neighborCell);

        if(neighborDirection == null) throw new IllegalArgumentException();

        // !!! Снова определяем соседство - см canWallSet
        // DONE: Вынес повторяющуюся логику в cellWallSet перед проверкой на действительное соседство между ячейками
        neighborCells.put(neighborDirection, neighborCell);
        neighborCells.put(neighborDirection.getOppositeDirection(), cell);
    }

    public BetweenCellsPosition(@NotNull Cell cell, @NotNull Direction direction) {

        neighborCells.put(direction.getOppositeDirection(), cell);

        Cell neighborCell = cell.neighborCell(direction);
        if(neighborCell != null) {
            neighborCells.put(direction, neighborCell);
        }
    }

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
