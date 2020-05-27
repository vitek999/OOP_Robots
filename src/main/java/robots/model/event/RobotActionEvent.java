package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.field.cell_objects.power_supplies.Battery;
import robots.model.field.Cell;
import robots.model.field.cell_objects.Robot;

import java.util.EventObject;

public class RobotActionEvent extends EventObject {
    // ------------------ Robot ------------------
    private Robot robot;
    private Cell fromCell;
    private Cell toCell;
    private Battery battery;

    public void setFromCell(Cell fromCell) {
        this.fromCell = fromCell;
    }

    public Cell getFromCell() {
        return fromCell;
    }

    public void setToCell(Cell toCell) {
        this.toCell = toCell;
    }

    public Cell getToCell() {
        return toCell;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setRobot(@NotNull Robot robot) {
        this.robot = robot;
    }

    public Robot getRobot() {
        return robot;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public RobotActionEvent(Object source) {
        super(source);
    }
}
