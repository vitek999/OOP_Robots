package robots;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
    private Game game;

    @BeforeEach
    public void testSetup() {
        game = new Game();
    }

    @Test
    public void test_passMoveNextRobot() {
        game.activeRobot().move(Direction.NORTH);
        game.activeRobot();
    }
}
