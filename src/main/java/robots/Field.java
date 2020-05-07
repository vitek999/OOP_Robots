package robots;

import org.jetbrains.annotations.NotNull;
import robots.event.ExitCellActionEvent;
import robots.event.ExitCellActionListener;
import robots.event.FieldActionEvent;
import robots.event.FieldActionListener;

import java.util.*;

public class Field {

    private final Map<Point, Cell> cells = new HashMap<>();

    private final int width;
    private final int height;

    private final Point exitPoint; // !!! Нужна позиция, а не сама ячейка??

    public Field(int width, int height, @NotNull Point exitPoint) {
        if(width <= 0) throw new IllegalArgumentException("Field width must be more than 0");
        if(height <= 0) throw new IllegalArgumentException("Field height must be more than 0");
        if(exitPoint.getX() >= width || exitPoint.getY() >= height)
            throw new IllegalArgumentException("exit point coordinates must be in range from 0 to weight or height");

        this.width = width;
        this.height = height;
        this.exitPoint = exitPoint;

        setupField();

        // Subscribe on exit cell
        ((ExitCell) getCell(exitPoint)).addExitCellActionListener(new ExitCellObserver());
    }

    private void setupField() { // !!! Непонятное название метода
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                Point p = new Point(x, y);
                Cell cell = p.equals(exitPoint)? new ExitCell() : new Cell();
                if(x > 0) getCell(new Point(p.getX() - 1, p.getY())).setNeighbor(cell, Direction.EAST); // !!! Так сложно, потому что Point ничего не умеет
                if(y > 0) getCell(new Point(p.getX(), p.getY() - 1)).setNeighbor(cell, Direction.SOUTH); // !!! Почему соседей устанавливает не созданная ячейка, а её сосдеи?
                cells.put(p, cell);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell getCell(@NotNull Point point) {
        return cells.get(point);
    }

    public List<Robot> getRobotsOnField() {
        List<Robot> robots = new ArrayList<>();
        for(var i : cells.entrySet()) { // !!! Не лучше ли организовать итератор ячеек
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
        return width == field.width &&
                height == field.height &&
                Objects.equals(cells, field.cells) &&
                Objects.equals(exitPoint, field.exitPoint);
    }

    @Override
    public int hashCode() { // !!! Метод нужен???
        return Objects.hash(cells, width, height, exitPoint);
    }

    @Override
    public String toString() {
        return "Field{" +
                "cells=" + cells +
                ", width=" + width +
                ", height=" + height +
                ", exitPoint=" + exitPoint +
                '}';
    }

    // -------------------- События --------------------

    class ExitCellObserver implements ExitCellActionListener {

        @Override
        public void robotIsTeleported(@NotNull ExitCellActionEvent event) {
            fireRobotIsTeleported(event.getRobot(), event.getTeleport());
        }
    }

    private ArrayList<FieldActionListener> fieldListListener = new ArrayList<>();

    public void addFieldlActionListener(FieldActionListener listener) {
        fieldListListener.add(listener);
    }

    public void removeFieldCellActionListener(FieldActionListener listener) {
        fieldListListener.remove(listener);
    }

    private void fireRobotIsTeleported(@NotNull Robot robot, @NotNull Cell teleport) {
        for(FieldActionListener listener: fieldListListener) {
            FieldActionEvent event = new FieldActionEvent(listener);
            event.setRobot(robot);
            event.setTeleport(teleport);
            listener.robotIsTeleported(event);
        }
    }
}
