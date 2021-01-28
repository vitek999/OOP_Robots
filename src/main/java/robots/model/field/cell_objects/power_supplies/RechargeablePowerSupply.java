package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;

/**
 * Перезаряжаемый источник питания.
 */
public abstract class RechargeablePowerSupply extends PowerSupply{

    /**
     * Конструтор.
     * @param charge заряд. Должен быть > 0 и < максимального зраяда.
     * @param maxCharge максмальный заряд. Должен быть > 0.
     * @throws IllegalArgumentException если заряд или максимальный заряд некорректны.
     */
    public RechargeablePowerSupply(int charge, int maxCharge) {
        super(charge, maxCharge);
    }

    /**
     * Зарядить от источника питания.
     * @param source источник питания для зарядки.
     */
    public void charge(@NotNull PowerSupply source) {
        if(source instanceof RenewablePowerSupply) {
            int amountCharge = maxCharge - charge;
            int released = source.releaseCharge(amountCharge);
            if (released != amountCharge) {
                released = source.releaseCharge(source.getCharge());
            }
            charge += released;
        }
    }

}
