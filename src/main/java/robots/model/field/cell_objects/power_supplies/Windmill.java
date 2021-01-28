package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;
import robots.model.event.WindmillActionEvent;
import robots.model.event.WindmillActionListener;
import robots.model.field.Cell;
import robots.model.field.between_cells_objects.Door;
import robots.model.field.cells.CellWithPowerSupply;

import java.util.ArrayList;
import java.util.List;

/**
 * Мельница.
 */
public class Windmill extends RenewablePowerSupply {

    /**
     * Кол-во восстаналливаемого заряда за раз.
     */
    private static final int RENEWABLE_CHARGE_VALUE = 2;

    /**
     * Конструтор.
     * @param charge заряд. Должен быть > 0 и < максимального зраяда.
     * @param maxCharge максмальный заряд. Должен быть > 0.
     * @throws IllegalArgumentException если заряд или максимальный заряд некорректны.
     */
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

    /**
     * Список слушателей, подписанных на события мельницы.
     */
    private final List<WindmillActionListener> windmillActionListenerList = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями мельницы.
     * @param listener слушатель.
     */
    public void addWindmillActionListener(@NotNull WindmillActionListener listener) {
        windmillActionListenerList.add(listener);
    }

    /**
     * Удалить слушателя за событиями мельницы.
     * @param listener слушатель.
     */
    public void removeWindmillActionListener(@NotNull WindmillActionListener listener) {
        windmillActionListenerList.remove(listener);
    }

    /**
     * Оповестить сулшателей {@link Windmill#windmillActionListenerList}, что мельница восстановила заряд.
     */
    private void fireChargeIsUpdated() {
        for(WindmillActionListener listener: windmillActionListenerList) {
            WindmillActionEvent event = new WindmillActionEvent(listener);
            event.setWindmill(this);
            listener.windmillChargeIsUpdated(event);
        }
    }
}
