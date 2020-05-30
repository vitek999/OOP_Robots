package robots.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * Интерфейс слушателя события класса мельницы {@link robots.model.field.cell_objects.power_supplies.Windmill}.
 */
public interface WindmillActionListener extends EventListener {

    /**
     * Заряд мельницы обновился.
     * @param event объект события класса мельницы.
     */
    void windmillChargeIsUpdated(@NotNull WindmillActionEvent event);
}
