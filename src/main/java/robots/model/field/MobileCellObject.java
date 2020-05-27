package robots.model.field;

import org.jetbrains.annotations.NotNull;
import robots.model.Direction;
import robots.model.field.Cell;
import robots.model.field.CellObject;

public abstract class MobileCellObject extends CellObject {

    public abstract void move(@NotNull Direction direction);

    protected abstract Cell canMove(@NotNull Direction direction);
}
