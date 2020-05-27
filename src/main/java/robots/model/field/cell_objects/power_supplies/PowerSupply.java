package robots.model.field.cell_objects.power_supplies;

import robots.model.field.CellObject;

public abstract class PowerSupply extends CellObject {

    protected int charge;
    protected int maxCharge;

    public PowerSupply(int charge, int maxCharge) {
        this.charge = charge;
        this.maxCharge = maxCharge;
    }

    public int getCharge() {
        return charge;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public int releaseCharge(int chargeAmount) {
        if(chargeAmount > charge) return 0;
        charge -= chargeAmount;
        return chargeAmount;
    }
}
