package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.field.Cell;
import robots.model.field.cell_objects.Robot;

import java.util.EventObject;

/**
 * Объект события класса поля {@link robots.model.field.Field}.
 */
public class FieldActionEvent extends EventObject {

    /**
     * Робот.
     */
    private Robot robot;

    /**
     * Установить робота {@link FieldActionEvent#robot}.
     * @param robot робот.
     */
    public void setRobot(@NotNull Robot robot) {
        this.robot = robot;
    }

    /**
     * Получить робота {@link FieldActionEvent#robot}.
     * @return робот
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * Ячейка выхода.
     */
    private Cell teleport;

    /**
     * Получить ячейку выхода {@link FieldActionEvent#teleport}.
     * @return ячейка выхода.
     */
    public Cell getTeleport() {
        return teleport;
    }

    /**
     * Установить ячейку выхода {@link FieldActionEvent#teleport}.
     * @param teleport ячейка выхода.
     */
    public void setTeleport(Cell teleport) {
        this.teleport = teleport;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public FieldActionEvent(Object source) {
        super(source);
    }
}
