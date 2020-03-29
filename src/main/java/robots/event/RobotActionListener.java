package robots.event;

import java.util.EventListener;

public interface RobotActionListener extends EventListener {
    void robotIsMoved(RobotActionEvent event);

    void robotIsSkipStep(RobotActionEvent event);
}
