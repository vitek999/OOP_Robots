package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;
import robots.model.field.Cell;

public class Accumulator extends RechargeablePowerSupply implements Portable {

    public Accumulator(int charge, int maxCharge) {
        super(charge, maxCharge);
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell cell) {
        return cell.getBattery() == null;
    }
}
