package robots.event;

import java.util.EventListener;

public interface GameActionListemer extends EventListener {

    void robotIsMoved(GameActionEvent event);

    void robotIsSkipStep(GameActionEvent event);

    void robotIsTeleported(GameActionEvent event);
}
