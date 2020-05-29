package robots.ui;

import org.jetbrains.annotations.NotNull;
import robots.model.*;
import robots.model.field.BetweenCellObject;
import robots.model.field.CellObject;
import robots.model.field.MobileCellObject;
import robots.model.field.between_cells_objects.Door;
import robots.model.field.cell_objects.power_supplies.Accumulator;
import robots.model.field.cell_objects.power_supplies.PowerSupply;
import robots.model.field.cell_objects.power_supplies.Windmill;
import robots.model.field.cells.CellWithPowerSupply;
import robots.model.field.cells.ExitCell;
import robots.model.field.cell_objects.power_supplies.Battery;
import robots.model.field.cell_objects.Robot;
import robots.model.field.Cell;
import robots.model.field.between_cells_objects.WallSegment;
import robots.ui.block.BetweenCellsWidget;
import robots.ui.block.BlockWidget;
import robots.ui.block.DoorWidget;
import robots.ui.block.WallWidget;
import robots.ui.cell.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetFactory {

    private final Map<Cell, CellWidget> cells = new HashMap<>();
    private final Map<CellObject, CellItemWidget> cellObjects = new HashMap<>();
    private final Map<BetweenCellObject, BlockWidget> betweenCellObjects = new HashMap<>();
    private final List<Color> usedColors = new ArrayList<>();

    public CellWidget create(@NotNull Cell cell) {
        if(cells.containsKey(cell)) return cells.get(cell);

        CellWidget item = (cell instanceof ExitCell) ? new ExitWidget() : new CellWidget();

        MobileCellObject mobileCellObject = cell.getMobileCellObject();
        if(mobileCellObject != null) {
            CellItemWidget robotWidget = create(mobileCellObject);
            item.addItem(robotWidget);
        }

        if(cell instanceof CellWithPowerSupply) {
            PowerSupply powerSupply = ((CellWithPowerSupply) cell).getPowerSupply();
            if (powerSupply != null) {
                CellItemWidget powerSupplyWidget = create(powerSupply);
                item.addItem(powerSupplyWidget);
            }
        }

        cells.put(cell, item);
        return item;
    }

    public CellWidget getWidget(@NotNull Cell cell) {
        return cells.get(cell);
    }

    public void remove(@NotNull Cell cell) { cells.remove(cell); }

    public CellItemWidget create(@NotNull CellObject cellObject) {
        if(cellObjects.containsKey(cellObject)) return cellObjects.get(cellObject);

        CellItemWidget createdWidget = null;
        if(cellObject instanceof Robot) {
            Color color = (usedColors.contains(Color.RED)) ? Color.BLUE : Color.RED;
            usedColors.add(color);
            createdWidget = new RobotWidget((Robot) cellObject, color);
        } else if(cellObject instanceof Battery) {
            createdWidget = new BatteryWidget((Battery) cellObject);
        } else if(cellObject instanceof Accumulator) {
            createdWidget = new AccumulatorWidget((Accumulator) cellObject);// TODO: create accumulator widget
        } else if(cellObject instanceof Windmill) {
            createdWidget = new WindmillWidget((Windmill) cellObject);
        } else {
            throw new IllegalArgumentException();
        }

        cellObjects.put(cellObject, createdWidget);
        return createdWidget;
    }

    public CellItemWidget getWidget(@NotNull CellObject cellObject) {
        return cellObjects.get(cellObject);
    }

    public void remove(@NotNull CellObject cellObject) { cellObjects.remove(cellObject); }

    public BlockWidget create(@NotNull BetweenCellObject betweenCellObject, Orientation orientation) {
        if (betweenCellObjects.containsKey(betweenCellObject)) return betweenCellObjects.get(betweenCellObject);

        BlockWidget createdBlockWidget = null;

        if(betweenCellObject instanceof WallSegment) {
            createdBlockWidget = new WallWidget(orientation);
        } else if (betweenCellObject instanceof Door) {
            createdBlockWidget = new DoorWidget((Door) betweenCellObject, orientation);
        } else {
            throw new IllegalArgumentException();
        }

        betweenCellObjects.put(betweenCellObject, createdBlockWidget);
        return createdBlockWidget;
    }

    public BlockWidget getWidget(@NotNull BetweenCellObject betweenCellObject) {
        return betweenCellObjects.get(betweenCellObject);
    }

    public void remove(@NotNull BetweenCellObject betweenCellObject) { betweenCellObjects.remove(betweenCellObject); }
}
