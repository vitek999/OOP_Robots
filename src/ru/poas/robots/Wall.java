package ru.poas.robots;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Wall {

    private WallPosition position;

    public Wall(@NotNull WallPosition position)
    {
        this.position = position;

        Map<Direction, Cell> neighborCells = position.getNeighborCells();

        for(var i : neighborCells.entrySet()){
            i.getValue().setNeighbor(this, i.getKey().getOppositeDirection());
        }
    }

    public WallPosition getPosition() {
        return position;
    }
}
