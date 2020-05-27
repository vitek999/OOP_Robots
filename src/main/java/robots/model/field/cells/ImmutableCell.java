package robots.model.field.cells;

import org.jetbrains.annotations.NotNull;
import robots.model.Direction;
import robots.model.field.BetweenCellObject;
import robots.model.field.Cell;
import robots.model.field.MobileCellObject;

public interface ImmutableCell {

    MobileCellObject getMobileCellObject();

    Cell getNeighborCell(@NotNull Direction direction);

    Direction getNeighborDirection(@NotNull Cell cell);

    BetweenCellObject getNeighborBetweenCellObject(@NotNull Direction direction);
}
