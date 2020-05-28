package robots.model.field;

import org.jetbrains.annotations.NotNull;
import robots.model.*;
import robots.model.field.between_cells_objects.BetweenCellsPosition;

import java.util.*;

public abstract class Cell {

    private final UUID uuid = UUID.randomUUID();

    /**
     * Cell objects
     */

    protected List<CellObject> objectList = new ArrayList<>();

    public void addObject(@NotNull CellObject cellObject) {
        boolean isPositionSetSuccess = cellObject.setPosition(this);
        if(!isPositionSetSuccess) throw new IllegalArgumentException();
        objectList.add(cellObject);
    }

    public CellObject takeObject(CellObject cellObject) {
        CellObject result = null;
        if(objectList.contains(cellObject)) {
            objectList.remove(cellObject);
            cellObject.setPosition(null);
            result = cellObject;
        }
        return result;
    }

    public MobileCellObject getMobileCellObject() {
        return (MobileCellObject) objectList.stream().filter(i -> i instanceof MobileCellObject).findFirst().orElse(null);
    }

    /**
     * Neighbor cells
     */
    private final Map<Direction, Cell> neighborCells = new EnumMap<>(Direction.class);

    public final Map<Direction, Cell> getNeighborCells() {
        return Collections.unmodifiableMap(neighborCells);
    }

    public Cell getNeighborCell(@NotNull Direction direction) {
        return neighborCells.get(direction);
    }

    void setNeighbor(@NotNull Cell neighborCell, @NotNull Direction direction) { // !!! Название аргумента ячейки малоинформативно
        // DONE: переименовал аргумент ячейки cell -> neighborCell
        // !!! Здесь что-то малопонятное: учесть случаи добавления той же ячейки, задание новой ячейки, если уже есть сосед и т.д.
        // DONE: Добавил проверки и тесты: добавляется ячейка, которая уже является соседом; установка соседа самого на сеья; d заданном направлении уже есть сосед.
        if (neighborCell == this || neighborCells.containsKey(direction) || neighborCells.containsValue(neighborCell))
            throw new IllegalArgumentException();
        neighborCells.put(direction, neighborCell);
        if (neighborCell.getNeighborCell(direction.getOppositeDirection()) == null) {
            neighborCell.setNeighbor(this, direction.getOppositeDirection());
        }
    }

    public Direction getNeighborDirection(@NotNull Cell other) {
        for (var i : neighborCells.entrySet()) {
            if (i.getValue().equals(other)) return i.getKey();
        }
        return null;
    }

    /**
     * Neighbor walls
     */
    private final Map<Direction, BetweenCellObject> neighborBetweenCellObjects = new EnumMap<>(Direction.class);

    public BetweenCellObject getNeighborBetweenCellObject(@NotNull Direction direction) {
        return neighborBetweenCellObjects.get(direction);
    }

    public void setWall(@NotNull BetweenCellObject betweenCellObject, @NotNull Direction direction) {
        if (getNeighborBetweenCellObject(direction) == betweenCellObject) return;
        BetweenCellsPosition position = new BetweenCellsPosition(this, direction);
        if (!betweenCellObject.canSetAtPosition(position) || neighborBetweenCellObjects.containsKey(direction) || neighborBetweenCellObjects.containsValue(betweenCellObject)) {
            throw new IllegalArgumentException();
        }
        Cell neighbor = getNeighborCell(direction);
        neighborBetweenCellObjects.put(direction, betweenCellObject);
        if (neighbor != null) {
            neighbor.setWall(betweenCellObject, direction.getOppositeDirection());
        }
        betweenCellObject.setPosition(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(uuid, cell.uuid) &&
                Objects.equals(objectList, cell.objectList) &&
                Objects.equals(neighborCells, cell.neighborCells) &&
                Objects.equals(neighborBetweenCellObjects, cell.neighborBetweenCellObjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, objectList, neighborCells.size(), neighborBetweenCellObjects.size());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
