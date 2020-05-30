package robots.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * Интерфейс слушателя события класса поля {@link robots.model.field.Field}.
 */
public interface FieldActionListener extends EventListener {
    /**
     * Робот телеропортировался.
     * @param event объект события класса поляю.
     */
    void robotIsTeleported(@NotNull FieldActionEvent event);
}
