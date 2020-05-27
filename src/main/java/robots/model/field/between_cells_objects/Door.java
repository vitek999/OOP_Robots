package robots.model.field.between_cells_objects;

public class Door extends BetweenCellObjectWithAction {

    private final static int ACTION_COST = 1;

    private boolean isOpen;

    public Door(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void perform() {
        isOpen = !isOpen;
    }

    @Override
    public int actionCost() {
        return ACTION_COST;
    }
}
