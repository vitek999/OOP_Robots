package robots;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.labirint.TestLabirint;

public class GameTest {
    private Game game;

    @BeforeEach
    public void testSetup() {
        game = new Game(new TestLabirint());
    }

    @Test
    public void test_passMoveNextRobot() {
        game.activeRobot().move(Direction.EAST);
    }
}
