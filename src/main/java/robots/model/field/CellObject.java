package robots.model.field;

import org.jetbrains.annotations.NotNull;

/**
 * Объект, располагающийся в ячейке.
 */
public abstract class CellObject {

    /**
     * Позиция объекта.
     */
    protected Cell position;

    /**
     * Получить позицию объекта {@link CellObject#position}.
     * @return позиция объекта.
     */
    public Cell getPosition() {
        return position;
    }

    /**
     * Усновить позицию объекта {@link CellObject#position}.
     * @param position позиция.
     * @return установлена ли позиция.
     */
    boolean setPosition(Cell position) {
        if (position != null && !canLocateAtPosition(position)) return false;
        this.position = position;
        return true;
    }

    /**
     * Может ли объект располагаться в указанной позиции.
     * @param cell позиция.
     * @return может ли объект располагаться в указанной позиции.
     */
    public abstract boolean canLocateAtPosition(@NotNull Cell cell);

}
