package robots.event;

import java.util.EventListener;

public interface GameActionListener extends EventListener {

    void robotIsMoved(GameActionEvent event);

    void robotIsSkipStep(GameActionEvent event);

    void robotIsTeleported(GameActionEvent event);
}
