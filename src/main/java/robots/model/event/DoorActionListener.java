package robots.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * Интерфейс слушателя события класса двери {@link robots.model.field.between_cells_objects.Door}.
 */
public interface DoorActionListener extends EventListener {

    /**
     * Состояние открытости двери изменилось.
     * @param doorActionEvent объект собтыия класса двери.
     */
    void doorIsOpenChanged(@NotNull DoorActionEvent doorActionEvent);
}
