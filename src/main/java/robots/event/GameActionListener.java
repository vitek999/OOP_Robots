package robots.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface GameActionListener extends EventListener {

    void robotIsMoved(@NotNull GameActionEvent event);

    void robotIsSkipStep(@NotNull GameActionEvent event);

    void robotIsTeleported(@NotNull GameActionEvent event);

    void gameStatusChanged(@NotNull GameActionEvent event);
}
