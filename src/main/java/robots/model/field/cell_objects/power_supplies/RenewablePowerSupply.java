package robots.model.field.cell_objects.power_supplies;

/**
 * Возобнавляемый источник питания.
 */
public abstract class RenewablePowerSupply extends PowerSupply {

    /**
     * Конструтор.
     * @param charge заряд. Должен быть > 0 и < максимального зраяда.
     * @param maxCharge максмальный заряд. Должен быть > 0.
     * @throws IllegalArgumentException если заряд или максимальный заряд некорректны.
     */
    public RenewablePowerSupply(int charge, int maxCharge) {
        super(charge, maxCharge);
    }

    /**
     * Восстановить заряд.
     */
    public abstract void update();
}
