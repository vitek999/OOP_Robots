package robots.model.field;

import org.jetbrains.annotations.NotNull;
import robots.model.Direction;
import robots.model.Point;
import robots.model.event.*;
import robots.model.field.cell_objects.Robot;
import robots.model.field.cell_objects.power_supplies.PowerSupply;
import robots.model.field.cell_objects.power_supplies.RenewablePowerSupply;
import robots.model.field.cells.CellWithPowerSupply;
import robots.model.field.cells.ExitCell;

import java.util.*;
import java.util.Map.Entry;

/**
 * Поле.
 */
public class Field {

    /**
     * Ячейки поля.
     */
    private final Map<Point, Cell> cells = new HashMap<>();

    /**
     * Ширина поля.
     */
    private final int width;

    /**
     * Высота поля.
     */
    private final int height;

    /**
     * Ячейка выхода.
     */
    private final ExitCell exitCell; // !!! Нужна позиция, а не сама ячейка??
                                 // DONE: Вместо позиции выхода храню ячейку

    /**
     * Конструтор.
     * @param width ширина. Должна быть > 0.
     * @param height высота. Должна быть > 0.
     * @param exitPoint координата ячейки выхода.
     * @throws IllegalArgumentException если ширина, высота или координата ячейки переданы некорректные.
     */
    public Field(int width, int height, @NotNull Point exitPoint) {
        if(width <= 0) throw new IllegalArgumentException("Field width must be more than 0");
        if(height <= 0) throw new IllegalArgumentException("Field height must be more than 0");
        if(exitPoint.getX() >= width || exitPoint.getY() >= height)
            throw new IllegalArgumentException("exit point coordinates must be in range from 0 to weight or height");

        this.width = width;
        this.height = height;

        buildField(exitPoint);
        this.exitCell = (ExitCell) getCell(exitPoint);

        // Subscribe on exit cell
        ((ExitCell) getCell(exitPoint)).addExitCellActionListener(new ExitCellObserver());
    }

    /**
     * Построить игровое поле.
     * @param exitPoint координата ячйки выхода.
     */
    private void buildField(Point exitPoint) { // !!! Непонятное название метода
                                // DONE: Переименовал метод setupField -> buildField
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                Point p = new Point(x, y);
                Cell cell = p.equals(exitPoint)? new ExitCell() : new CellWithPowerSupply();
                // !!! Так сложно, потому что Point ничего не умеет
                // DONE: Упрости, добавив метод to в класс Point.
                // !!! Почему соседей устанавливает не созданная ячейка, а её сосдеи?
                // DONE: Теперь соседей устанавливает созданная ячейка.
                if(x > 0) cell.setNeighbor(getCell(p.to(Direction.WEST, 1)), Direction.WEST);
                if(y > 0) cell.setNeighbor(getCell(p.to(Direction.NORTH, 1)), Direction.NORTH);
                cells.put(p, cell);
            }
        }
    }

    /**
     * Получить ширину поля {@link Field#width}.
     * @return ширина поля.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Получить высоту поля {@link Field#height}.
     * @return высота поля.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Получить ячейку по заданной координате.
     * @param point координата.
     * @return ячейка.
     */
    public Cell getCell(@NotNull Point point) {
        return cells.get(point);
    }

    /**
     * Получить роботов на поле.
     * @return список роботов на поле.
     */
    public List<Robot> getRobotsOnField() {
        List<Robot> robots = new ArrayList<>();
        for(var i : cells.entrySet()) { // !!! Не лучше ли организовать итератор ячеек
            Robot robot = (Robot) i.getValue().getMobileCellObject();
            if(robot != null) robots.add(robot);
        }
        return robots;
    }

    /**
     * Обновить возобновляемые источники питания на поле.
     */
    public void updateRenewablePowerSupplies() {
        for(Entry<Point, Cell> item : cells.entrySet()) {
            Cell cell = item.getValue();
            if(cell instanceof CellWithPowerSupply) {
                PowerSupply powerSupply = ((CellWithPowerSupply) cell).getPowerSupply();
                if(powerSupply instanceof RenewablePowerSupply) ((RenewablePowerSupply) powerSupply).update();
            }
        }
    }

    /**
     * Получить телепортированных роботов.
     * @return телепортированные роботы.
     */
    public List<Robot> getTeleportedRobots() {
        return exitCell.getTeleportedRobots();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return width == field.width &&
                height == field.height &&
                Objects.equals(cells, field.cells) &&
                Objects.equals(exitCell, field.exitCell);
    }

    // !!! Метод нужен???
    // DONE: Нет, удалил hashCode

    @Override
    public String toString() {
        return "Field{" +
                "cells=" + cells +
                ", width=" + width +
                ", height=" + height +
                ", exitPoint=" + exitCell +
                '}';
    }


    /**
     * Класс, реализующий наблюдение за событиями {@link ExitCellActionListener}.
     */
    class ExitCellObserver implements ExitCellActionListener {

        @Override
        public void robotIsTeleported(@NotNull ExitCellActionEvent event) {
            fireRobotIsTeleported(event.getRobot(), event.getTeleport());
        }
    }

    /**
     * Список слушателей, подписанных на события поля.
     */
    private final ArrayList<FieldActionListener> fieldListListener = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями поля.
     * @param listener слушатель.
     */
    public void addFieldActionListener(FieldActionListener listener) {
        fieldListListener.add(listener);
    }

    /**
     * Удалить слушателя за событиями поля.
     * @param listener слушатель.
     */
    public void removeFieldCellActionListener(FieldActionListener listener) {
        fieldListListener.remove(listener);
    }

    /**
     * Оповестиьт слушателей {@link Field#fieldListListener}, что робот телепортировался.
     * @param robot телепорированный робот.
     * @param teleport телпорт.
     */
    private void fireRobotIsTeleported(@NotNull Robot robot, @NotNull Cell teleport) {
        for(FieldActionListener listener: fieldListListener) {
            FieldActionEvent event = new FieldActionEvent(listener);
            event.setRobot(robot);
            event.setTeleport(teleport);
            listener.robotIsTeleported(event);
        }
    }
}
