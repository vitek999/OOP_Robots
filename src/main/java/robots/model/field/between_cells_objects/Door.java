package robots.model.field.between_cells_objects;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import robots.model.Game;
import robots.model.event.DoorActionEvent;
import robots.model.event.DoorActionListener;

/**
 * Дверь.
 */
public class Door extends BetweenCellObjectWithAction {

    /**
     * Стоимость открытия двери.
     */
    private final static int ACTION_COST = 1;

    /**
     * Состояние открытости двери.
     */
    private boolean isOpen;

    public Door(boolean isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * Получить состояние открытости двери {@link Door#isOpen}.
     * @return состояние открытости двери.
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Открыть дверь.
     */
    @Override
    public void perform() {
        isOpen = !isOpen;
        fireDoorIsOpenChanged();
    }

    /**
     * Получить количество энерги для открытия двери.
     * @return количество энерги для открытия двери
     */
    @Override
    public int actionCost() {
        return ACTION_COST;
    }


    /**
     * Список слушателей, подписанных на события двери.
     */
    private final List<DoorActionListener> doorActionListenerList = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями двери.
     * @param doorActionListener слушатель.
     */
    public void addDoorActionListener(@NotNull DoorActionListener doorActionListener) {
        doorActionListenerList.add(doorActionListener);
    }

    /**
     * Удалить слушателя за событиями двери.
     * @param doorActionListener слушатель.
     */
    public void removeDoorActionListener(@NotNull DoorActionListener doorActionListener) {
        doorActionListenerList.remove(doorActionListener);
    }

    /**
     * Оповестить сулшателей {@link Door#doorActionListenerList}, что статус
     * открытости двери {@link Door#isOpen} изменился
     */
    private void fireDoorIsOpenChanged() {
        for(DoorActionListener listener: doorActionListenerList) {
            DoorActionEvent event = new DoorActionEvent(listener);
            event.setDoor(this);
            listener.doorIsOpenChanged(event);
        }
    }
}
