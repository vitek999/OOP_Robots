package ru.poas.robots;

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
        throw new UnsupportedOperationException();
    }

    public void setBattery(Battery battery) {
        throw new UnsupportedOperationException();
    }

    /**
     * Robot
     */
    private Robot robot;

    public Robot getRobot() {
        return robot;
    }

    public Robot takeRobot() {
        throw new UnsupportedOperationException();
    }

    public void setRobot(Robot robot) {
        throw new UnsupportedOperationException();
    }

    /**
     * Neighbor cells
     */
    private Map<Direction, Cell> neighborCells;

    public Cell neighborCell(Direction direction) {
        return neighborCells.get(direction);
    }

    void setNeighbor(Cell cell, Direction direction) {
        neighborCells.put(direction, cell);
    }

    public Direction isNeighbor(Cell cell) {
        throw new UnsupportedOperationException();
    }

    /**
     * Neighbor walls
     */
    private Map<Direction, Wall> neighborWalls;

    public Wall neighborWall(Direction direction) {
        return neighborWalls.get(direction);
    }

    void setNeighbor(Wall wall, Direction direction) {
        neighborWalls.put(direction, wall);
    }

}
