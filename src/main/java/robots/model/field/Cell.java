package robots.model.field;

import org.jetbrains.annotations.NotNull;
import robots.model.*;
import robots.model.field.between_cells_objects.BetweenCellsPosition;
import robots.model.field.cell_objects.Battery;
import robots.model.field.cell_objects.Robot;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Cell {

    private UUID uuid = UUID.randomUUID();

    /**
     * Battery
     */
    private Battery battery;

    public Battery getBattery() {
        return battery;
    }

    public Battery takeBattery() {
        var tmp = battery;
        battery = null;
        return tmp;
    }

    public void setBattery(Battery battery) { // !!! А как же проверка, что это возможно
        // DONE: Добавил статический метод c проверкой canLocateAtPosition в класс Battery.
        if (!Battery.canLocateAtPosition(this)) throw new IllegalArgumentException();
        this.battery = battery;
    }

    /**
     * Robot
     */
    private MobileCellObject robot;

    public Robot getRobot() {
        return (Robot) robot;
    }

    public Robot takeRobot() {
        robot.setPosition(null);
        var tmp = robot;
        robot = null;
        return (Robot) tmp;
    }

    public void setRobot(MobileCellObject robot) {
        // !!! Кто решает, что робот может находиться в ячейке?? - не соответсвует диаграмме 01_Расстановка на поле
        // DONE: робот решает, может ли он находиться в ячейке (см. метод Robot.setPosition)
        // !!! Что будет, если переданный робот уже находится в некоторой ячейке??
        // DONE: Будет выкинуто исключение IllegalArgumentException
        boolean isPositionSetSuccess = robot.setPosition(this);
        if (!isPositionSetSuccess) throw new IllegalArgumentException("In cell already set robot");
        this.robot = robot;
    }

    /**
     * Neighbor cells
     */
    private Map<Direction, Cell> neighborCells = new EnumMap<>(Direction.class);

    public Cell neighborCell(@NotNull Direction direction) {
        return neighborCells.get(direction);
    }

    void setNeighbor(@NotNull Cell neighborCell, @NotNull Direction direction) { // !!! Название аргумента ячейки малоинформативно
        // DONE: переименовал аргумент ячейки cell -> neighborCell
        // !!! Здесь что-то малопонятное: учесть случаи добавления той же ячейки, задание новой ячейки, если уже есть сосед и т.д.
        // DONE: Добавил проверки и тесты: добавляется ячейка, которая уже является соседом; установка соседа самого на сеья; d заданном направлении уже есть сосед.
        if (neighborCell == this || neighborCells.containsKey(direction) || neighborCells.containsValue(neighborCell))
            throw new IllegalArgumentException();
        neighborCells.put(direction, neighborCell);
        if (neighborCell.neighborCell(direction.getOppositeDirection()) == null) {
            neighborCell.setNeighbor(this, direction.getOppositeDirection());
        }
    }

    public Direction isNeighbor(@NotNull Cell other) { // !!! Название аргумента малоинформативно
        // DONE: Переименовал аргумент cell -> other
        for (var i : neighborCells.entrySet()) { // !!! Может реализовать итератор соседей??? TODO

            if (i.getValue().equals(other)) return i.getKey();
        }
        return null;
    }

    /**
     * Neighbor walls
     */
    private Map<Direction, BetweenCellObject> neighborBetweenCellObjects = new EnumMap<>(Direction.class);

    public BetweenCellObject neighborBetweenCellObject(@NotNull Direction direction) {
        return neighborBetweenCellObjects.get(direction);
    }

    public void setWall(@NotNull BetweenCellObject betweenCellObject, @NotNull Direction direction) {
        if (neighborBetweenCellObject(direction) == betweenCellObject) return;
        BetweenCellsPosition position = new BetweenCellsPosition(this, direction);
        if (!betweenCellObject.canSetAtPosition(position) || neighborBetweenCellObjects.containsKey(direction) || neighborBetweenCellObjects.containsValue(betweenCellObject)) {
            throw new IllegalArgumentException();
        }
        Cell neighbor = neighborCell(direction);
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
        return Objects.equals(battery, cell.battery) &&
                Objects.equals(uuid, cell.uuid) &&
                Objects.equals(robot, cell.robot) &&
                Objects.equals(neighborCells, cell.neighborCells) &&
                Objects.equals(neighborBetweenCellObjects, cell.neighborBetweenCellObjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, neighborCells.size(), neighborBetweenCellObjects.size());
    }

    @Override
    public String toString() {
        return "Cell{" +
                "battery=" + battery +
                ", robot=" + robot +
                ", neighborCells=" + neighborCells.size() +
                ", neighborWalls=" + neighborBetweenCellObjects.size() +
                '}';
    }
}
