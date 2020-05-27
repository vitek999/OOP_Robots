package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.GameStatus;
import robots.model.field.cell_objects.Robot;

import java.util.EventObject;

public class GameActionEvent extends EventObject {
    // ------------------ Robot ------------------
    private Robot robot;

    public void setRobot(@NotNull Robot robot) {
        this.robot = robot;
    }

    public Robot getRobot() {
        return robot;
    }

    private GameStatus status;

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameActionEvent(Object source) {
        super(source);
    }
}