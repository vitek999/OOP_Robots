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

    public void setBattery(Battery battery) {
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

    public void setRobot(Robot robot) {
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

    void setNeighbor(@NotNull Cell cell, @NotNull Direction direction) {
        neighborCells.put(direction, cell);
        if(cell.neighborCell(direction.getOppositeDirection()) == null) {
            cell.setNeighbor(this, direction.getOppositeDirection());
        }
    }

    public Direction isNeighbor(@NotNull Cell cell) {
        for(var i : neighborCells.entrySet()) {
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
        return Objects.hash(battery, robot, neighborCells, neighborWalls);
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
