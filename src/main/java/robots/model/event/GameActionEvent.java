package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.GameStatus;
import robots.model.field.cell_objects.Robot;

import java.util.EventObject;

/**
 * Объект события класса игры {@link robots.model.Game}.
 */
public class GameActionEvent extends EventObject {

    /**
     * Робот.
     */
    private Robot robot;

    /**
     * Установить робота {@link GameActionEvent#robot}.
     * @param robot робот.
     */
    public void setRobot(@NotNull Robot robot) {
        this.robot = robot;
    }

    /**
     * Получить робота {@link GameActionEvent#robot}.
     * @return робот.
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * Статус игры.
     */
    private GameStatus status;

    /**
     * Получить статус игры {@link GameActionEvent#status}.
     * @return статус игры.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Установить статус игры {@link GameActionEvent#status}.
     * @param status статус игры.
     */
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