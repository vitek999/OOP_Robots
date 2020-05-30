package robots.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * Интерфейс слушателя события класса игры {@link robots.model.Game}.
 */
public interface GameActionListener extends EventListener {

    /**
     * Робот переместился.
     * @param event объект события класса игры.
     */
    void robotIsMoved(@NotNull GameActionEvent event);

    /**
     * Робот пропустил ход.
     * @param event объект события класса игры.
     */
    void robotIsSkipStep(@NotNull GameActionEvent event);

    /**
     * Робот телепортировался.
     * @param event объект события класса игры.
     */
    void robotIsTeleported(@NotNull GameActionEvent event);

    /**
     * Статус игры изменился.
     * @param event объект события класса игры.
     */
    void gameStatusChanged(@NotNull GameActionEvent event);
}
