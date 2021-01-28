package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.field.between_cells_objects.Door;

import java.util.EventObject;

/**
 * Объект собтыия класса двери {@link Door}.
 */
public class DoorActionEvent extends EventObject {

    /**
     * дверь.
     */
    private Door door;

    /**
     * Установить дверь {@link DoorActionEvent#door}.
     * @param door дверь.
     */
    public void setDoor(@NotNull Door door) {
        this.door = door;
    }

    /**
     * Получить дверь {@link DoorActionEvent#door}.
     * @return дверь.
     */
    public Door getDoor() {
        return door;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public DoorActionEvent(Object source) {
        super(source);
    }
}
