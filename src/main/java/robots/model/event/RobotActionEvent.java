package robots.model.event;

import org.jetbrains.annotations.NotNull;
import robots.model.field.Cell;
import robots.model.field.cell_objects.Robot;
import robots.model.field.cell_objects.power_supplies.PowerSupply;

import java.util.EventObject;

/**
 * Объект события класса робот {@link Robot}.
 */
public class RobotActionEvent extends EventObject {

    /**
     * Робот.
     */
    private Robot robot;

    /**
     * Ячейка откуда переместился робот {@link RobotActionEvent#robot}.
     */
    private Cell fromCell;

    /**
     * Ячейка куда переместился робот {@link RobotActionEvent#robot}.
     */
    private Cell toCell;

    /**
     * Источник питания.
     */
    private PowerSupply powerSupply;

    /**
     * Установить ячейку {@link RobotActionEvent#fromCell} откуда переместился робот {@link RobotActionEvent#robot}.
     * @param fromCell ячейка откуда переместился робот.
     */
    public void setFromCell(Cell fromCell) {
        this.fromCell = fromCell;
    }

    /**
     * Получить ячейку {@link RobotActionEvent#fromCell} откуда переместился робот {@link RobotActionEvent#robot}.
     * @return ячейка откуда переместился робот.
     */
    public Cell getFromCell() {
        return fromCell;
    }

    /**
     * Установить ячейку {@link RobotActionEvent#toCell} куда переместился робот {@link RobotActionEvent#robot}.
     * @param toCell ячейка куда переместился робот.
     */
    public void setToCell(Cell toCell) {
        this.toCell = toCell;
    }

    /**
     * Получить ячейку {@link RobotActionEvent#toCell} куда переместился робот {@link RobotActionEvent#robot}.
     * @return ячейка куда переместился робот.
     */
    public Cell getToCell() {
        return toCell;
    }

    /**
     * Установить источник питания {@link RobotActionEvent#powerSupply}.
     * @param powerSupply источник питания.
     */
    public void setPowerSupply(@NotNull PowerSupply powerSupply) {
        this.powerSupply = powerSupply;
    }

    /**
     * Получить источник питания {@link RobotActionEvent#powerSupply}.
     * @return источник питания.
     */
    public PowerSupply getPowerSupply() {
        return powerSupply;
    }

    /**
     * Установить робота {@link RobotActionEvent#robot}.
     * @param robot робот.
     */
    public void setRobot(@NotNull Robot robot) {
        this.robot = robot;
    }

    /**
     * Получить робота {@link RobotActionEvent#robot}.
     * @return робот.
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public RobotActionEvent(Object source) {
        super(source);
    }
}
