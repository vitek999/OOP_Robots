package robots.model.field.cells;

import robots.model.field.Cell;
import robots.model.field.cell_objects.power_supplies.PowerSupply;

public class CellWithPowerSupply extends Cell {

    public PowerSupply getPowerSupply() {
        return (PowerSupply) objectList.stream().filter(i -> i instanceof PowerSupply).findFirst().orElse(null);
    }
}
