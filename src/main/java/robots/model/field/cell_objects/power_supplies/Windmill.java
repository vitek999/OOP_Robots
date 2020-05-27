package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;
import robots.model.field.Cell;

public class Windmill extends RenewablePowerSupply {

    private static final int RENEWABLE_CHARGE_VALUE = 2;

    public Windmill(int charge, int maxCharge) {
        super(charge, maxCharge);
    }

    @Override
    public void update() {
        if(charge < maxCharge) {
            charge += (charge + RENEWABLE_CHARGE_VALUE > maxCharge)? maxCharge - charge : RENEWABLE_CHARGE_VALUE;
        }
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell cell) {
        return cell.getBattery() == null && cell.getRobot() == null;
    }
}
