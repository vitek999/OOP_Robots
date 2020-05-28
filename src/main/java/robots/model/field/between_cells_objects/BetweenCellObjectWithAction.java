package robots.model.field.between_cells_objects;

import robots.model.field.BetweenCellObject;

public abstract class BetweenCellObjectWithAction extends BetweenCellObject {

    public abstract void perform();

    public abstract int actionCost();
}
