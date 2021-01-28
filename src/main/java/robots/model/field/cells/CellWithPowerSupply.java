package robots.model.field.cells;

import robots.model.field.Cell;
import robots.model.field.cell_objects.power_supplies.PowerSupply;

/**
 * Ячейка с источником питания.
 */
public class CellWithPowerSupply extends Cell {

    /**
     * Получить источник питания.
     * @return источник питания.
     */
    public PowerSupply getPowerSupply() {
        return (PowerSupply) objectList.stream().filter(i -> i instanceof PowerSupply).findFirst().orElse(null);
    }
}
