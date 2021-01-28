package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;
import robots.model.field.Cell;
import robots.model.field.cells.CellWithPowerSupply;

/**
 * Аккумулятор.
 */
public class Accumulator extends RechargeablePowerSupply implements Portable {

    /**
     * Конструктор
     * @param charge заряд.
     * @param maxCharge максимальный заряд.
     */
    public Accumulator(int charge, int maxCharge) {
        super(charge, maxCharge);
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell cell) {
        return position == null && (cell instanceof CellWithPowerSupply)
                && ((CellWithPowerSupply) cell).getPowerSupply() == null;
    }
}
