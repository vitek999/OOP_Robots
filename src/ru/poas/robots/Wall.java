package ru.poas.robots;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return Objects.equals(position, wall.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "Wall{" +
                "position=" + position +
                '}';
    }
}
