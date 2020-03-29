package robots.event;

import robots.Robot;

import java.util.EventObject;

public class FieldActionEvent extends EventObject {

    private Robot robot;

    public void setRobot(Robot robot) {
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
    public FieldActionEvent(Object source) {
        super(source);
    }
}
