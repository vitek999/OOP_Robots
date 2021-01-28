package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.field.Cell;
import robots.model.field.cell_objects.Robot;

import java.util.EventObject;

/**
 * Объект собтыия класса ячейки выхода {@link robots.model.field.cells.ExitCell}.
 */
public class ExitCellActionEvent extends EventObject {

    /**
     * Робот.
     */
    private Robot robot;

    /**
     * Установить робота {@link ExitCellActionEvent#robot}.
     * @param robot робот.
     */
    public void setRobot(@NotNull Robot robot) {
        this.robot = robot;
    }

    /**
     * Получить робота {@link ExitCellActionEvent#robot}.
     * @return робот.
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * Ячейка выхода.
     */
    private Cell teleport;

    /**
     * Получить ячейку выхода {@link ExitCellActionEvent#teleport}.
     * @return ячейка выхода.
     */
    public Cell getTeleport() {
        return teleport;
    }

    /**
     * Установить ячейку выхода {@link ExitCellActionEvent#teleport}.
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
    public ExitCellActionEvent(Object source) {
        super(source);
    }
}
