package robots.ui;

import org.jetbrains.annotations.NotNull;
import robots.*;
import robots.Point;
import robots.Robot;
import robots.event.RobotActionEvent;
import robots.event.RobotActionListener;
import robots.ui.block.BetweenCellsWidget;
import robots.ui.block.WallWidget;
import robots.ui.cell.BatteryWidget;
import robots.ui.cell.CellWidget;
import robots.ui.cell.RobotWidget;

import javax.swing.*;
import java.awt.*;
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
    }

    private void fillField() {
        for (int i = 0; i < field.getHeight(); ++i) {
            JPanel row = new JPanel();
            JPanel rowWalls = new JPanel();
            JPanel startRowWalls = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            rowWalls.setLayout(new BoxLayout(rowWalls, BoxLayout.X_AXIS));
            startRowWalls.setLayout(new BoxLayout(startRowWalls, BoxLayout.X_AXIS));

            for (int j = 0; j < field.getWidth(); ++j) {
                robots.Point point = new Point(j, i);
                Cell cell = field.getCell(point);
                CellWidget widget = widgetFactory.create(cell);

                if(j == 0)  {
                    BetweenCellsWidget westCellWidget = new BetweenCellsWidget(Orientation.VERTICAL);
                    Wall wall = cell.neighborWall(Direction.WEST);
                    if( wall != null) {
                        WallWidget wallWidget = widgetFactory.create(wall, Orientation.VERTICAL);
                        westCellWidget.addItem(wallWidget);
                    }
                    row.add(westCellWidget);
                }

                if(i == 0) {
                    BetweenCellsWidget northCellWidget = new BetweenCellsWidget(Orientation.HORIZONTAL);
                    Wall wall = cell.neighborWall(Direction.NORTH);
                    if( wall != null) {
                        WallWidget wallWidget = widgetFactory.create(wall, Orientation.HORIZONTAL);
                        northCellWidget.addItem(wallWidget);
                    }
                    startRowWalls.add(northCellWidget);
                }

                BetweenCellsWidget southCellWidget = new BetweenCellsWidget(Orientation.HORIZONTAL);
                Wall southWall = cell.neighborWall(Direction.SOUTH);

                if(southWall != null) {
                    WallWidget wallWidget = widgetFactory.create(southWall, Orientation.HORIZONTAL);
                    southCellWidget.addItem(wallWidget);
                }

                rowWalls.add(southCellWidget);

                row.add(widget);

                BetweenCellsWidget eastCellWidget = new BetweenCellsWidget(Orientation.VERTICAL);
                Wall eastWall = cell.neighborWall(Direction.EAST);
                if(eastWall != null) {
                    WallWidget wallWidget = widgetFactory.create(eastWall, Orientation.VERTICAL);
                    eastCellWidget.addItem(wallWidget);
                }

                row.add(eastCellWidget);
            }

            if(i == 0) add(startRowWalls);
            add(row);
            add(rowWalls);
        }
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
            RobotWidget robotWidget = widgetFactory.getWidget(event.getRobot());
            CellWidget from = widgetFactory.getWidget(event.getFromCell());
            CellWidget to = widgetFactory.getWidget(event.getToCell());
            from.removeItem(robotWidget);
            to.addItem(robotWidget);
            //TODO: kostyl!
            robotWidget.requestFocus();
        }

        @Override
        public void robotIsSkipStep(@NotNull RobotActionEvent event) {
            Robot robot = event.getRobot();
            RobotWidget robotWidget = widgetFactory.getWidget(robot);
            robotWidget.repaint();
            //TODO: kostyl!
            robotWidget.requestFocus();
        }

        @Override
        public void robotChangeActive(@NotNull RobotActionEvent event) {
            Robot robot = event.getRobot();
            RobotWidget robotWidget = widgetFactory.getWidget(robot);
            robotWidget.setActive(robot.isActive());
        }

        @Override
        public void robotChangeBattery(@NotNull RobotActionEvent event) {
            Robot robot = event.getRobot();
            RobotWidget robotWidget = widgetFactory.getWidget(robot);
            CellWidget cellWidget = widgetFactory.getWidget(robot.getPosition());
            BatteryWidget batteryWidget = widgetFactory.getWidget(event.getBattery());
            cellWidget.removeItem(batteryWidget);
            widgetFactory.remove(event.getBattery());
            //TODO: kostyl!
            robotWidget.requestFocus();
        }
    }
}
