package robots.ui.block;

import org.jetbrains.annotations.NotNull;
import robots.model.Orientation;
import robots.model.field.between_cells_objects.Door;

import java.io.File;

public class DoorWidget extends BlockWidget {

    private final Door door;

    public DoorWidget(@NotNull Door door, @NotNull Orientation orientation) {
        super(orientation);
        this.door = door;
    }

    @Override
    protected File getImageFile() {
        File doorFile = null;

        if (door.isOpen()) {
            doorFile = (orientation == Orientation.VERTICAL) ? new File("ODV.png") : new File("ODH.png");
        } else {
            doorFile = (orientation == Orientation.VERTICAL) ? new File("CDV.png") : new File("CDH.png");
        }
        return doorFile;
    }
}
