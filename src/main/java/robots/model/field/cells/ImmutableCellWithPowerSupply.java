package robots.model.field.cells;

import robots.model.field.cell_objects.power_supplies.PowerSupply;

public interface ImmutableCellWithPowerSupply extends ImmutableCell {

    PowerSupply getPowerSupply();
}
