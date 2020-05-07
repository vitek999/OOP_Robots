package robots.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface RobotActionListener extends EventListener {
    void robotIsMoved(@NotNull RobotActionEvent event);

    void robotIsSkipStep(@NotNull RobotActionEvent event); // !!! Должна быть утвердительная форма

    void robotChangeActive(@NotNull RobotActionEvent event); // !!! Не понял назначение события

    void robotChangeBattery(@NotNull RobotActionEvent event); // !!! Должна быть утвердительная форма
}
