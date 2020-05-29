package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;
import robots.model.event.WindmillActionEvent;
import robots.model.event.WindmillActionListener;
import robots.model.field.Cell;
import robots.model.field.cells.CellWithPowerSupply;

import java.util.ArrayList;
import java.util.List;

public class Windmill extends RenewablePowerSupply {

    private static final int RENEWABLE_CHARGE_VALUE = 2;

    public Windmill(int charge, int maxCharge) {
        super(charge, maxCharge);
    }

    @Override
    public void update() {
        if(charge < maxCharge) {
            charge += (charge + RENEWABLE_CHARGE_VALUE > maxCharge)? maxCharge - charge : RENEWABLE_CHARGE_VALUE;
            fireChargeIsUpdated();
        }
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell cell) {
        return position == null && (cell instanceof CellWithPowerSupply) && ((CellWithPowerSupply) cell).getPowerSupply() == null
                && cell.getMobileCellObject() == null;
    }

    // ----------- Cобытия -----------------
    private final List<WindmillActionListener> windmillActionListenerList = new ArrayList<>();

    public void addWindmillActionListener(@NotNull WindmillActionListener listener) {
        windmillActionListenerList.add(listener);
    }

    public void removeWindmillActionListener(@NotNull WindmillActionListener listener) {
        windmillActionListenerList.remove(listener);
    }

    private void fireChargeIsUpdated() {
        for(WindmillActionListener listener: windmillActionListenerList) {
            WindmillActionEvent event = new WindmillActionEvent(listener);
            event.setWindmill(this);
            listener.windmillChargeIsUpdated(event);
        }
    }
}
