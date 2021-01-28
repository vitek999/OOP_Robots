package robots.model.field.between_cells_objects;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.model.event.DoorActionEvent;
import robots.model.event.DoorActionListener;

import static org.junit.jupiter.api.Assertions.*;


public class DoorTest {

    private int eventCount = 0;

    private class DoorObserver implements DoorActionListener {

        @Override
        public void doorIsOpenChanged(@NotNull DoorActionEvent doorActionEvent) {
            eventCount++;
        }
    }

    private Door door;

    @BeforeEach
    public void testSetup() {
        door = new Door(false);
        eventCount = 0;
        door.addDoorActionListener(new DoorObserver());
    }

    @Test
    public void test_getActionCost() {
        assertEquals(1, door.actionCost());
    }

    @Test
    public void test_perform() {
        door.perform();
        assertTrue(door.isOpen());
        int expectedEventCount = 1;
        assertEquals(expectedEventCount, eventCount);
    }
}
