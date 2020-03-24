package ru.poas.robots;

public class Wall {

    private WallPosition position;

    public Wall(WallPosition position) {
        this.position = position;
    }

    public WallPosition getPosition() {
        return position;
    }
}
