package robots;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class Cell {

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
        this.battery = battery;
    }

    /**
     * Robot
     */
    private Robot robot;

    public Robot getRobot() {
        return robot;
    }

    public Robot takeRobot() {
        robot.setPosition(null);
        var tmp = robot;
        robot = null;
        return tmp;
    }

    public void setRobot(Robot robot) { // !!! Кто решает, что робот может находиться в ячейке?? - не соответсвует диаграмме 01_Расстановка на поле
        // !!! Что будет, если переданный робот уже находится в некоторой ячейке??
        if(getRobot() != null) throw new IllegalArgumentException("In cell already set robot");

        robot.setPosition(this);
        this.robot = robot;
    }

    /**
     * Neighbor cells
     */
    private Map<Direction, Cell> neighborCells = new EnumMap<>(Direction.class);

    public Cell neighborCell(@NotNull Direction direction) {
        return neighborCells.get(direction);
    }

    void setNeighbor(@NotNull Cell cell, @NotNull Direction direction) { // !!! Название аргумента ячейки малоинформативно
        if(neighborCells.containsKey(direction) && neighborCells.containsValue(cell)) return; // !!! Здесь что-то малопонятное: учесть случаи добавления той же ячейки, задание новой ячейки, если уже есть сосед и т.д.
        if(neighborCells.containsKey(direction)) throw new IllegalArgumentException();
        neighborCells.put(direction, cell);
        if(cell.neighborCell(direction.getOppositeDirection()) == null) {
            cell.setNeighbor(this, direction.getOppositeDirection());
        }
    }

    public Direction isNeighbor(@NotNull Cell cell) { // !!! Название аргумента малоинформативно
        for(var i : neighborCells.entrySet()) { // !!! Может реализовать итератор соседей???
            if(i.getValue().equals(cell)) return i.getKey();
        }
        return null;
    }

    /**
     * Neighbor walls
     */
    private Map<Direction, Wall> neighborWalls = new EnumMap<>(Direction.class);

    public Wall neighborWall(@NotNull Direction direction) {
        return neighborWalls.get(direction);
    }

    void setNeighbor(@NotNull Wall wall, @NotNull Direction direction) {
        neighborWalls.put(direction, wall);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(battery, cell.battery) &&
                Objects.equals(robot, cell.robot) &&
                Objects.equals(neighborCells, cell.neighborCells) &&
                Objects.equals(neighborWalls, cell.neighborWalls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighborCells.size(), neighborWalls.size());
    }

    @Override
    public String toString() {
        return "Cell{" +
                "battery=" + battery +
                ", robot=" + robot +
                ", neighborCells=" + neighborCells.size() +
                ", neighborWalls=" + neighborWalls.size() +
                '}';
    }
}
