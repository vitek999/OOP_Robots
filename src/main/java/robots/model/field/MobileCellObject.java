package robots.model.field;

import org.jetbrains.annotations.NotNull;
import robots.model.Direction;

public abstract class MobileCellObject extends CellObject {

    public abstract void move(@NotNull Direction direction);

    protected abstract Cell canMove(@NotNull Direction direction);
}
