package robots.model.field.cells;

import robots.model.field.cell_objects.Robot;
import java.util.List;

public interface ImmutableExitCell extends ImmutableCell {

    List<Robot> getTeleportedRobots();
}
