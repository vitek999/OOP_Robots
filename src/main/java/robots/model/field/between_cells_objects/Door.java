package robots.model.field.between_cells_objects;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import robots.model.event.DoorActionEvent;
import robots.model.event.DoorActionListener;

public class Door extends BetweenCellObjectWithAction {

    private final static int ACTION_COST = 1;

    private boolean isOpen;

    public Door(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void perform() {
        isOpen = !isOpen;
        fireDoorIsOpenChanged();
    }

    @Override
    public int actionCost() {
        return ACTION_COST;
    }

    // -------------------- События --------------------

    private final List<DoorActionListener> doorActionListenerList = new ArrayList<>();

    public void addDoorActionListener(@NotNull DoorActionListener doorActionListener) {
        doorActionListenerList.add(doorActionListener);
    }

    public void removeDoorActionListener(@NotNull DoorActionListener doorActionListener) {
        doorActionListenerList.remove(doorActionListener);
    }

    private void fireDoorIsOpenChanged() {
        for(DoorActionListener listener: doorActionListenerList) {
            DoorActionEvent event = new DoorActionEvent(listener);
            event.setDoor(this);
            listener.doorIsOpenChanged(event);
        }
    }
}
