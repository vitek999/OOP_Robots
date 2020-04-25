package robots.event;

import org.jetbrains.annotations.NotNull;
import robots.Cell;
import robots.Robot;

import java.util.EventObject;

public class FieldActionEvent extends EventObject {

    private Robot robot;

    public void setRobot(@NotNull Robot robot) {
        this.robot = robot;
    }

    public Robot getRobot() {
        return robot;
    }

    private Cell teleport;

    public Cell getTeleport() {
        return teleport;
    }

    public void setTeleport(Cell teleport) {
        this.teleport = teleport;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public FieldActionEvent(Object source) {
        super(source);
    }
}
