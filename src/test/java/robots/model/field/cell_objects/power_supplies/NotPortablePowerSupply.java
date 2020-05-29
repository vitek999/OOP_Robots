package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;
import robots.model.field.Cell;

public class NotPortablePowerSupply extends PowerSupply{
    public NotPortablePowerSupply(int charge, int maxCharge) {
        super(charge, maxCharge);
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell cell) {
        return true;
    }
}
