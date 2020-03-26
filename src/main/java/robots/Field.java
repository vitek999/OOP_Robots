package robots;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Field {

    private Map<Point, Cell> cells = new HashMap<>();

    private int weight;
    private int height;

    private Point exitPoint;

    public Field(int weight, int height, @NotNull Point exitPoint) {
        this.weight = weight;
        this.height = height;
        this.exitPoint = exitPoint;

        setupField();
    }

    private void setupField() {
        // TODO: implement
    }

    public Cell getCell(@NotNull Point point) {
        return cells.get(point);
    }

    public List<Robot> getRobotsOnField() {
        List<Robot> robots = new ArrayList<>();
        for(var i : cells.entrySet()) {
            Robot robot = i.getValue().getRobot();
            if(robot != null) robots.add(robot);
        }
        return robots;
    }

    public List<Robot> getTeleportedRobots() {
        return ((ExitCell) getCell(exitPoint)).getTeleportedRobots();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return weight == field.weight &&
                height == field.height &&
                Objects.equals(cells, field.cells) &&
                Objects.equals(exitPoint, field.exitPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells, weight, height, exitPoint);
    }

    @Override
    public String toString() {
        return "Field{" +
                "cells=" + cells +
                ", weight=" + weight +
                ", height=" + height +
                ", exitPoint=" + exitPoint +
                '}';
    }
}
