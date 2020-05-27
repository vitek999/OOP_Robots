package robots.model;

import org.jetbrains.annotations.NotNull;

public abstract class MobileCellObject extends CellObject{

    public abstract void move(@NotNull Direction direction);

    protected abstract Cell canMove(@NotNull Direction direction);
}
