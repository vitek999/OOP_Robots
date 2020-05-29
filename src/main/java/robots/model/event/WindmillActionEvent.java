package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.field.cell_objects.power_supplies.Windmill;

import java.util.EventObject;

public class WindmillActionEvent extends EventObject {

    private Windmill windmill;

    public void setWindmill(@NotNull Windmill windmill) {
        this.windmill = windmill;
    }

    public Windmill getWindmill() {
        return windmill;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public WindmillActionEvent(Object source) {
        super(source);
    }
}
