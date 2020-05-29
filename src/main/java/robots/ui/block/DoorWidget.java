package robots.ui.block;

import org.jetbrains.annotations.NotNull;
import robots.model.Orientation;
import robots.model.event.DoorActionEvent;
import robots.model.event.DoorActionListener;
import robots.model.field.between_cells_objects.Door;

import java.io.File;

public class DoorWidget extends BlockWidget implements DoorActionListener {

    private final Door door;

    public DoorWidget(@NotNull Door door, @NotNull Orientation orientation) {
        super(orientation);
        door.addDoorActionListener(this);
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

    @Override
    public void doorIsOpenChanged(@NotNull DoorActionEvent doorActionEvent) {
        if(doorActionEvent.getDoor() == door) repaint();
    }
}
