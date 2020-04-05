package robots;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.event.GameActionEvent;
import robots.event.GameActionListener;
import robots.labirint.TestLabirint;
import robots.utils.Pare;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;

    private enum Event {ROBOT_MOVED, ROBOT_SKIP_STEP, ROBOT_TELEPORTED}

    private List<Pare<Event, Robot>> events = new ArrayList<>();
    private List<Pare<Event, Robot>> expectedEvents = new ArrayList<>();

    private class EventListener implements GameActionListener {

        @Override
        public void robotIsMoved(@NotNull GameActionEvent event) {
            events.add(new Pare<>(Event.ROBOT_MOVED, event.getRobot()));
        }

        @Override
        public void robotIsSkipStep(@NotNull GameActionEvent event) {
            events.add(new Pare<>(Event.ROBOT_SKIP_STEP, event.getRobot()));
        }

        @Override
        public void robotIsTeleported(@NotNull GameActionEvent event) {
            events.add(new Pare<>(Event.ROBOT_TELEPORTED, event.getRobot()));
        }
    }

    @BeforeEach
    public void testSetup() {
        events.clear();
        expectedEvents.clear();

        game = new Game(new TestLabirint());
        game.addGameActionListener(new EventListener());
    }

    @Test
    public void test_robotMoved_success() {
        Robot robot = game.activeRobot();
        expectedEvents.add(new Pare<>(Event.ROBOT_MOVED, robot));

        game.activeRobot().move(Direction.EAST);

        assertNotEquals(robot, game.activeRobot());
        assertFalse(robot.isActive());
        assertEquals(expectedEvents, events);
        assertEquals(GameStatus.GAME_IS_ON, game.status());
    }

    @Test
    public void test_robotMoved_incorrectDirection() {
        Robot robot = game.activeRobot();
        game.activeRobot().move(Direction.WEST);

        assertEquals(robot, game.activeRobot());
        assertTrue(robot.isActive());
        assertEquals(expectedEvents, events);
        assertEquals(GameStatus.GAME_IS_ON, game.status());
    }

    @Test
    public void test_robotSkipStep() {
        Robot robot = game.activeRobot();
        expectedEvents.add(new Pare<>(Event.ROBOT_SKIP_STEP, robot));

        game.activeRobot().skipStep();

        assertNotEquals(robot, game.activeRobot());
        assertFalse(robot.isActive());
        assertEquals(expectedEvents, events);
        assertEquals(GameStatus.GAME_IS_ON, game.status());
    }
}
