package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;

public abstract class RechargeablePowerSupply extends PowerSupply{
    public RechargeablePowerSupply(int charge, int maxCharge) {
        super(charge, maxCharge);
    }

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
