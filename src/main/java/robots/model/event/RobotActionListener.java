package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.field.cell_objects.Robot;

import java.util.EventListener;

/**
 * Интерфейс слушателя события класса робот {@link robots.model.field.cell_objects.Robot}.
 */
public interface RobotActionListener extends EventListener {

    /**
     * Робот переместился.
     * @param event объект события класса робот.
     */
    void robotIsMoved(@NotNull RobotActionEvent event);

    /**
     * Робот пропустил ход.
     * @param event объект события класса робот.
     */
    void robotSkippedStep(@NotNull RobotActionEvent event); // !!! Должна быть утвердительная форма
                                                            // DONE: переименовал robotSkipStep в robotSkippedStep

    /**
     * Состояние активности робота {@link Robot#isActive()} изменилось.
     * @param event объект события класса робот.
     */
    void robotActivityChanged(@NotNull RobotActionEvent event); // !!! Не понял назначение события
                                                                // DONE: переименовал robotChangeActive в robotActivityChanged

    /**
     * Робот заменил источник питания.
     * @param event объект события класса робот.
     */
    void robotChangedPowerSupply(@NotNull RobotActionEvent event);  // !!! Должна быть утвердительная форма
                                                                // DONE: переименовал robotChangeBattery в robotChangedBattery

    /**
     * Робот зарядил источник питания.
     * @param event объект события класса робот.
     */
    void robotChargedPowerSupply(@NotNull RobotActionEvent event);
}
