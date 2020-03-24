package ru.poas.robots;

public enum Direction {
    NORTH, SOUTH, WEST, EAST;

    private Direction opposite;

    static  {
        NORTH.opposite = SOUTH;
        SOUTH.opposite = NORTH;
        WEST.opposite = EAST;
        EAST.opposite = WEST;
    }

    public Direction getOppositeDirection() {
        return opposite;
    }
}
