package robots.model;

import org.junit.jupiter.api.BeforeEach;
import robots.model.Cell;
import robots.model.Direction;

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