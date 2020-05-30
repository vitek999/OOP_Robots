package robots.ui.block;

import org.jetbrains.annotations.NotNull;
import robots.model.Orientation;
import robots.model.event.DoorActionEvent;
import robots.model.event.DoorActionListener;
import robots.model.field.between_cells_objects.Door;
import robots.ui.utils.ImageUtils;

import java.io.File;

/**
 * Виджет двери.
 * @see Door
 */
public class DoorWidget extends BlockWidget implements DoorActionListener {

    /**
     * Дверь.
     */
    private final Door door;

    /**
     * Конструтор.
     * @param door дверь.
     * @param orientation ориентация.
     */
    public DoorWidget(@NotNull Door door, @NotNull Orientation orientation) {
        super(orientation);
        door.addDoorActionListener(this);
        this.door = door;
    }

    @Override
    protected File getImageFile() {
        File doorFile = null;

        if (door.isOpen()) {
            doorFile = (orientation == Orientation.VERTICAL) ? new File(ImageUtils.IMAGE_PATH + "ODV.png") : new File(ImageUtils.IMAGE_PATH + "ODH.png");
        } else {
            doorFile = (orientation == Orientation.VERTICAL) ? new File(ImageUtils.IMAGE_PATH + "CDV.png") : new File(ImageUtils.IMAGE_PATH + "CDH.png");
        }
        return doorFile;
    }

    @Override
    public void doorIsOpenChanged(@NotNull DoorActionEvent doorActionEvent) {
        if(doorActionEvent.getDoor() == door) repaint();
    }
}
