package robots.event;

import java.util.EventListener;

public interface ExitCellActionListener extends EventListener {
    void robotIsTeleported(ExitCellActionEvent event);
}
