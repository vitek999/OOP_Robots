package robots.model.field.cell_objects.power_supplies;

import robots.model.field.CellObject;

/**
 * Источник питания.
 */
public abstract class PowerSupply extends CellObject {

    /**
     * Заряд.
     */
    protected int charge;

    /**
     * Максимальный заряд.
     */
    protected int maxCharge;

    /**
     * Конструтор.
     * @param charge заряд. Должен быть > 0 и < максимального зраяда.
     * @param maxCharge максмальный заряд. Должен быть > 0.
     * @throws IllegalArgumentException если заряд или максимальный заряд некорректны.
     */
    public PowerSupply(int charge, int maxCharge) {
        if(charge < 0 || maxCharge < 0 || charge > maxCharge) throw new IllegalArgumentException();

        this.charge = charge;
        this.maxCharge = maxCharge;
    }

    /**
     * Получить заряд {@link PowerSupply#charge}.
     * @return заряд.
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Получить максимальный заряд {@link PowerSupply#maxCharge}.
     * @return максимальный заряд.
     */
    public int getMaxCharge() {
        return maxCharge;
    }

    /**
     * Отдать заряд.
     * @param chargeAmount запрашиваемое кол-во заряда.
     * @return отданное кол-во заряда.
     */
    public int releaseCharge(int chargeAmount) {
        if(chargeAmount > charge) return 0;
        charge -= chargeAmount;
        return chargeAmount;
    }
}
