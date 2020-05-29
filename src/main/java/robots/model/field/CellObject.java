package robots.model.field;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class CellObject {

    protected Cell position;

    public Cell getPosition() {
        return position;
    }

    boolean setPosition(Cell position) {
        if (position != null && !canLocateAtPosition(position)) return false;
        this.position = position;
        return true;
    }

    public abstract boolean canLocateAtPosition(@NotNull Cell cell);

}
