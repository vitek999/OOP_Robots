package robots.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface RobotActionListener extends EventListener {
    void robotIsMoved(@NotNull RobotActionEvent event);

    void robotSkippedStep(@NotNull RobotActionEvent event); // !!! Должна быть утвердительная форма
                                                            // DONE: переименовал robotSkipStep в robotSkippedStep

    void robotActivityChanged(@NotNull RobotActionEvent event); // !!! Не понял назначение события
                                                                // DONE: переименовал robotChangeActive в robotActivityChanged

    void robotChangedBattery(@NotNull RobotActionEvent event);  // !!! Должна быть утвердительная форма
                                                                // DONE: переименовал robotChangeBattery в robotChangedBattery
}
