package robots.event;

import org.jetbrains.annotations.NotNull;
import robots.Robot;

import java.util.EventObject;

public class ExitCellActionEvent extends EventObject {

    private Robot robot;

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
    public ExitCellActionEvent(Object source) {
        super(source);
    }
}
