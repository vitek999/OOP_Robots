package robots.ui;

import org.jetbrains.annotations.NotNull;
import robots.model.*;
import robots.model.field.BetweenCellObject;
import robots.model.field.Cell;
import robots.model.event.FieldActionEvent;
import robots.model.event.FieldActionListener;
import robots.model.event.RobotActionEvent;
import robots.model.event.RobotActionListener;
import robots.model.field.Field;
import robots.model.field.cell_objects.Robot;
import robots.ui.block.BetweenCellsWidget;
import robots.ui.block.BlockWidget;
import robots.ui.cell.*;

import javax.swing.*;
import java.util.List;

public class FieldWidget extends JPanel {

    private final Field field;
    private final WidgetFactory widgetFactory;

    public FieldWidget(@NotNull Field field, @NotNull  WidgetFactory widgetFactory) {
        this.field = field;
        this.widgetFactory = widgetFactory;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        fillField();
        subscribeOnRobots();
        field.addFieldActionListener(new FieldController());
    }

    private void fillField() { // !!! Сделать процедурную декомпозицию
                               // DONE: Сделал порцедурную декомпозицию. Добавил методы createRow и createRowWalls.
        if(field.getHeight() > 0) {
            JPanel startRowWalls = createRowWalls(0, Direction.NORTH);
            add(startRowWalls);
        }

        for (int i = 0; i < field.getHeight(); ++i) {
            JPanel row = createRow(i); // !!! Не могу понять назначение трех панелей
                                       // DONE: Третья панель перемещена в условие startRowWalls нужна для добавления самой первой строки из стен.
                                       // Сейас она вынесена из цикла.
            add(row);
            JPanel rowWalls = createRowWalls(i, Direction.SOUTH);
            add(rowWalls);
        }
    }

    private JPanel createRow(int rowIndex) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < field.getWidth(); ++i) {
            Point point = new Point(i, rowIndex); // !!! Зачем упоминание пакета?
                                                  // DONE: Убрал упоминание пакета.
            Cell cell = field.getCell(point);
            CellWidget cellWidget = widgetFactory.create(cell); // !!! В жэтом методе много разных виджетов, такое название мало что говорит
                                                                // DONE: переименовал widget -> cellWidget

            if(i == 0)  {
                BetweenCellsWidget westCellWidget = new BetweenCellsWidget(Orientation.VERTICAL);
                BetweenCellObject wallSegment = cell.getNeighborBetweenCellObject(Direction.WEST);
                if( wallSegment != null) {
                    BlockWidget wallWidget = widgetFactory.create(wallSegment, Orientation.VERTICAL);
                    westCellWidget.setItem(wallWidget);
                }
                row.add(westCellWidget);
            }

            row.add(cellWidget);

            BetweenCellsWidget eastCellWidget = new BetweenCellsWidget(Orientation.VERTICAL);
            BetweenCellObject eastWallSegment = cell.getNeighborBetweenCellObject(Direction.EAST);
            if(eastWallSegment != null) {
                BlockWidget wallWidget = widgetFactory.create(eastWallSegment, Orientation.VERTICAL);
                eastCellWidget.setItem(wallWidget);
            }

            row.add(eastCellWidget);
        }
        return row;
    }

    private JPanel createRowWalls(int rowIndex, Direction direction) {
        if(direction == Direction.EAST || direction == Direction.WEST) throw new IllegalArgumentException();
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < field.getWidth(); ++i) {
            Point point = new Point(i, rowIndex);
            Cell cell = field.getCell(point);

            BetweenCellsWidget southCellWidget = new BetweenCellsWidget(Orientation.HORIZONTAL);
            BetweenCellObject southWallSegment =  cell.getNeighborBetweenCellObject(direction);

            if(southWallSegment != null) {
                BlockWidget wallWidget = widgetFactory.create(southWallSegment, Orientation.HORIZONTAL);
                southCellWidget.setItem(wallWidget);
            }

            row.add(southCellWidget);
        }
        return row;
    }

    private void subscribeOnRobots() {
        List<Robot> robots = field.getRobotsOnField();
        for(Robot robot : robots) {
            robot.addRobotActionListener(new RobotController());
        }
    }

    private class RobotController implements RobotActionListener {

        @Override
        public void robotIsMoved(@NotNull RobotActionEvent event) {
            CellItemWidget robotWidget = widgetFactory.getWidget(event.getRobot());
            CellWidget from = widgetFactory.getWidget(event.getFromCell());
            CellWidget to = widgetFactory.getWidget(event.getToCell());
            from.removeItem(robotWidget);
            to.addItem(robotWidget);
            robotWidget.requestFocus();
        }

        @Override
        public void robotSkippedStep(@NotNull RobotActionEvent event) {
            Robot robot = event.getRobot();
            CellItemWidget robotWidget = widgetFactory.getWidget(robot);
            robotWidget.repaint();
        }

        @Override
        public void robotActivityChanged(@NotNull RobotActionEvent event) {
            Robot robot = event.getRobot();
            RobotWidget robotWidget = (RobotWidget) widgetFactory.getWidget(robot);
            robotWidget.setActive(robot.isActive());
        }

        @Override
        public void robotChangedPowerSupply(@NotNull RobotActionEvent event) {
            Robot robot = event.getRobot();
            CellWidget cellWidget = widgetFactory.getWidget(robot.getPosition());
            CellItemWidget batteryWidget = widgetFactory.getWidget(event.getPowerSupply());
            cellWidget.removeItem(batteryWidget);
            widgetFactory.remove(event.getPowerSupply());
        }

        @Override
        public void robotChargedPowerSupply(@NotNull RobotActionEvent event) {
            CellItemWidget powerSupplyWidget = widgetFactory.getWidget(event.getPowerSupply());
            CellItemWidget robotWidget = widgetFactory.getWidget(event.getRobot());
            powerSupplyWidget.repaint();
            robotWidget.repaint();
        }
    }

    private class FieldController implements FieldActionListener {

        @Override
        public void robotIsTeleported(@NotNull FieldActionEvent event) {
            Robot robot = event.getRobot();
            Cell teleport = event.getTeleport();
            CellWidget teleportWidget = widgetFactory.getWidget(teleport);
            CellItemWidget robotWidget = widgetFactory.getWidget(robot);
            teleportWidget.removeItem(robotWidget);
        }
    }
}
