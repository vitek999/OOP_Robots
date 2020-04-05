package robots.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface ExitCellActionListener extends EventListener {
    void robotIsTeleported(@NotNull ExitCellActionEvent event);
}
