package robots.ui;

import org.jetbrains.annotations.NotNull;
import robots.model.*;
import robots.model.field.ExitCell;
import robots.model.field.cell_objects.Battery;
import robots.model.field.cell_objects.Robot;
import robots.model.field.Cell;
import robots.model.field.between_cells_objects.WallSegment;
import robots.ui.block.WallWidget;
import robots.ui.cell.BatteryWidget;
import robots.ui.cell.CellWidget;
import robots.ui.cell.ExitWidget;
import robots.ui.cell.RobotWidget;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetFactory {

    private final Map<Cell, CellWidget> cells = new HashMap<>();
    private final Map<Robot, RobotWidget> robots = new HashMap<>();
    private final Map<Battery, BatteryWidget> batteries = new HashMap<>();
    private final Map<WallSegment, WallWidget> walls = new HashMap<>();
    private final List<Color> usedColors = new ArrayList<>();

    public CellWidget create(@NotNull Cell cell) {
        if(cells.containsKey(cell)) return cells.get(cell);

        CellWidget item = (cell instanceof ExitCell) ? new ExitWidget() : new CellWidget();

        Robot robot = cell.getRobot();
        if(robot != null) {
            RobotWidget robotWidget = create(robot);
            item.addItem(robotWidget);
        }

        Battery battery = cell.getBattery();
        if(battery != null) {
            BatteryWidget batteryWidget = create(battery);
            item.addItem(batteryWidget);
        }

        cells.put(cell, item);
        return item;
    }

    public CellWidget getWidget(@NotNull Cell cell) {
        return cells.get(cell);
    }

    public RobotWidget create(@NotNull Robot robot) {
        if(robots.containsKey(robot)) return robots.get(robot);

        Color color = (usedColors.contains(Color.RED)) ? Color.BLUE : Color.RED;
        usedColors.add(color);

        RobotWidget item = new RobotWidget(robot, color);
        robots.put(robot, item);
        return item;
    }

    public RobotWidget getWidget(@NotNull Robot robot) {
        return robots.get(robot);
    }

    public BatteryWidget create(@NotNull Battery battery) {
        if(batteries.containsKey(battery)) return batteries.get(battery);

        BatteryWidget item = new BatteryWidget(battery);
        batteries.put(battery, item);
        return item;
    }

    public BatteryWidget getWidget(@NotNull Battery battery) {
        return batteries.get(battery);
    }

    public void remove(Battery battery) {
        batteries.remove(battery);
    }

    public WallWidget create(@NotNull WallSegment wallSegment, Orientation orientation) {
        if(walls.containsKey(wallSegment)) return walls.get(wallSegment);

        WallWidget item = new WallWidget(orientation);
        walls.put(wallSegment, item);
        return item;
    }

    public WallWidget getWidget(@NotNull WallSegment wallSegment) {
        return walls.get(wallSegment);
    }
}
