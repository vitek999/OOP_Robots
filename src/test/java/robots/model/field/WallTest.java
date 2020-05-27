package robots.model.field;

import org.junit.jupiter.api.BeforeEach;
import robots.model.Direction;
import robots.model.field.Cell;

class WallTest {

    private Cell cell;
    private Cell neighborCell;
    private final Direction direction = Direction.NORTH;

    @BeforeEach
    public void testSetup() {
        cell = new Cell();
        neighborCell = new Cell();
    }

}