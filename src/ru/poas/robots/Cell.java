package ru.poas.robots;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class Cell {

    /**
     * Battery
     */
    private Battery battery;

    public Battery getBattery() {
        return battery;
    }

    public Battery takeBatter() {
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
        var tmp = robot;
        robot = null;
        return tmp;
    }

    public void setRobot(Robot robot) {
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

}
