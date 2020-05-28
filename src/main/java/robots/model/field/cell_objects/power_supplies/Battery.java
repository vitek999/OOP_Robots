package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;
import robots.model.field.Cell;
import robots.model.field.cells.CellWithPowerSupply;

import java.util.Objects;

public class Battery extends PowerSupply implements Portable {

    private static final int MAX_CHARGE = 10;

    public Battery(int charge) {
        super(charge, MAX_CHARGE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Battery battery = (Battery) o;
        return Objects.equals(charge, battery.charge);
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell cell) {
        return position == null && (cell instanceof CellWithPowerSupply)
                && ((CellWithPowerSupply) cell).getPowerSupply() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(charge);
    }

    @Override
    public String toString() {
        return "Battery{" +
                "charge=" + charge +
                '}';
    }
}
