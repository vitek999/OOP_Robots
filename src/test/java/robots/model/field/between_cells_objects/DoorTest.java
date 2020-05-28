package robots.model.field.between_cells_objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DoorTest {

    private Door door;

    @BeforeEach
    public void testSetup() {
        door = new Door(false);
    }

    @Test
    public void test_getActionCost() {
        assertEquals(1, door.actionCost());
    }

    @Test
    public void test_perform() {
        door.perform();
        assertTrue(door.isOpen());
    }
}
