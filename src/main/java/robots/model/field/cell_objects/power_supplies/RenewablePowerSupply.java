package robots.model.field.cell_objects.power_supplies;

public abstract class RenewablePowerSupply extends PowerSupply {

    public RenewablePowerSupply(int charge, int maxCharge) {
        super(charge, maxCharge);
    }

    public abstract void update();
}
