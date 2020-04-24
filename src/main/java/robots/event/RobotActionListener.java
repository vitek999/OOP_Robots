package robots.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface RobotActionListener extends EventListener {
    void robotIsMoved(@NotNull RobotActionEvent event);

    void robotIsSkipStep(@NotNull RobotActionEvent event);

    void robotChangeActive(@NotNull RobotActionEvent event);
}
