package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;
import robots.model.field.Cell;
import robots.model.field.cells.CellWithPowerSupply;

public class Accumulator extends RechargeablePowerSupply implements Portable {

    public Accumulator(int charge, int maxCharge) {
        super(charge, maxCharge);
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell cell) {
        return (cell instanceof CellWithPowerSupply) && ((CellWithPowerSupply) cell).getPowerSupply() == null;
    }
}
