package robots;

import org.jetbrains.annotations.NotNull;
import robots.event.ExitCellActionEvent;
import robots.event.ExitCellActionListener;
import robots.event.FieldActionEvent;
import robots.event.FieldActionListener;

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
        ((ExitCell) getCell(exitPoint)).addExitCellActionListener(new ExitCellObserver());
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

    // -------------------- События --------------------

    class ExitCellObserver implements ExitCellActionListener {

        @Override
        public void robotIsTeleported(ExitCellActionEvent event) {
            fireRobotIsTeleported(event.getRobot());
        }
    }

    private ArrayList<FieldActionListener> fieldListListener = new ArrayList<>();

    public void addExitCellActionListener(FieldActionListener listener) {
        fieldListListener.add(listener);
    }

    public void removeExitCellActionListener(FieldActionListener listener) {
        fieldListListener.remove(listener);
    }

    private void fireRobotIsTeleported(Robot robot) {
        for(FieldActionListener listener: fieldListListener) {
            FieldActionEvent event = new FieldActionEvent(listener);
            event.setRobot(robot);
            listener.robotIsTeleported(event);
        }
    }
}
