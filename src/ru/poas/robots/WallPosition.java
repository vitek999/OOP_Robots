package ru.poas.robots;

import java.util.HashMap;
import java.util.Map;

public class WallPosition {

    private Map<Direction, Cell> neighborCells;

    public WallPosition(Cell cell, Cell neighborCell) {

    }

    public WallPosition(Cell cell, Direction direction) {

    }

    public Map<Direction, Cell> getNeighborCells () {
        return new HashMap<>(neighborCells);
    }

    public static Boolean canWallSet(Cell cell, Direction direction) {
        throw new UnsupportedOperationException();
    }

    public static Boolean canWallSet(Cell cell, Cell neighborCell) {
        throw new UnsupportedOperationException();
    }
}
