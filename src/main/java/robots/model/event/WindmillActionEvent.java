package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.field.cell_objects.power_supplies.Windmill;

import java.util.EventObject;

/**
 * Объект события класса мельницы {@link Windmill}.
 */
public class WindmillActionEvent extends EventObject {

    /**
     * Мельница.
     */
    private Windmill windmill;

    /**
     * Установить мельницу {@link WindmillActionEvent#windmill}.
     * @param windmill мельница.
     */
    public void setWindmill(@NotNull Windmill windmill) {
        this.windmill = windmill;
    }

    /**
     * Получить мельницу {@link WindmillActionEvent#windmill}.
     * @return мельница.
     */
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
