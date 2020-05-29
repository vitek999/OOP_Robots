package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.field.between_cells_objects.Door;

import java.util.EventObject;

public class DoorActionEvent extends EventObject {

    private Door door;

    public void setDoor(@NotNull Door door) {
        this.door = door;
    }

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
