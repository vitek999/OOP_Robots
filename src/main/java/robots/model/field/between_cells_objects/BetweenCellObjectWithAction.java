package robots.model.field.between_cells_objects;

import robots.model.field.BetweenCellObject;

/**
 * Объект, распологающийся между ячейками {@link robots.model.field.Cell}, имеющий действия.
 */
public abstract class BetweenCellObjectWithAction extends BetweenCellObject {

    /**
     * Выполнить действие.
     */
    public abstract void perform();

    /**
     * Стоимость выполнения действия.
     * @return стоимость выполнения действия.
     */
    public abstract int actionCost();
}
