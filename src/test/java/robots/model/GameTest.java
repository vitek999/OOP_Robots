package robots.model;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.model.event.GameActionEvent;
import robots.model.event.GameActionListener;
import robots.model.labirint.TestLabirint;
import robots.model.field.cell_objects.Robot;
import robots.utils.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;

    private enum Event {ROBOT_MOVED, ROBOT_SKIP_STEP, ROBOT_TELEPORTED}

    private List<Pair<Event, Robot>> events = new ArrayList<>();
    private List<Pair<Event, Robot>> expectedEvents = new ArrayList<>();

    private class EventListener implements GameActionListener {

        @Override
        public void robotIsMoved(@NotNull GameActionEvent event) {
            events.add(new Pair<>(Event.ROBOT_MOVED, event.getRobot()));
        }

        @Override
        public void robotIsSkipStep(@NotNull GameActionEvent event) {
            events.add(new Pair<>(Event.ROBOT_SKIP_STEP, event.getRobot()));
        }

        @Override
        public void robotIsTeleported(@NotNull GameActionEvent event) {
            events.add(new Pair<>(Event.ROBOT_TELEPORTED, event.getRobot()));
        }

        @Override
        public void gameStatusChanged(@NotNull GameActionEvent event) {

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
    public void test_finishGame() {
        game.abort();

        assertEquals(GameStatus.GAME_ABORTED, game.getStatus());
    }

    @Test
    public void test_robotMoved_success() {
        Robot robot = game.getActiveRobot();
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, robot));

        game.getActiveRobot().move(Direction.EAST);

        assertNotEquals(robot, game.getActiveRobot());
        assertFalse(robot.isActive());
        assertEquals(expectedEvents, events);
        assertEquals(GameStatus.GAME_IS_ON, game.getStatus());
    }

    @Test
    public void test_robotMoved_incorrectDirection() {
        Robot robot = game.getActiveRobot();
        game.getActiveRobot().move(Direction.WEST);

        assertEquals(robot, game.getActiveRobot());
        assertTrue(robot.isActive());
        assertEquals(expectedEvents, events);
        assertEquals(GameStatus.GAME_IS_ON, game.getStatus());
    }

    @Test
    public void test_robotSkipStep() {
        Robot robot = game.getActiveRobot();
        expectedEvents.add(new Pair<>(Event.ROBOT_SKIP_STEP, robot));

        game.getActiveRobot().skipStep();

        assertNotEquals(robot, game.getActiveRobot());
        assertFalse(robot.isActive());
        assertEquals(expectedEvents, events);
        assertEquals(GameStatus.GAME_IS_ON, game.getStatus());
    }

    @Test
    public void test_robotTeleported() {
        Robot robot = game.getActiveRobot();

        game.getActiveRobot().move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, robot));

        Robot secondRobot = game.getActiveRobot();
        game.getActiveRobot().move(Direction.WEST);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, secondRobot));

        game.getActiveRobot().move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, robot));

        expectedEvents.add(new Pair<>(Event.ROBOT_TELEPORTED, robot));

        assertNotEquals(robot, game.getActiveRobot());
        assertEquals(expectedEvents, events);
        assertFalse(robot.isActive());
        assertEquals(GameStatus.GAME_IS_ON, game.getStatus());
    }

    @Test
    public void test_allRobotTeleported() {
        Robot robot = game.getActiveRobot();

        game.getActiveRobot().move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, robot));

        Robot secondRobot = game.getActiveRobot();
        game.getActiveRobot().move(Direction.WEST);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, secondRobot));

        game.getActiveRobot().move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, robot));

        expectedEvents.add(new Pair<>(Event.ROBOT_TELEPORTED, robot));

        game.getActiveRobot().move(Direction.SOUTH);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, secondRobot));

        game.getActiveRobot().move(Direction.SOUTH);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, secondRobot));

        game.getActiveRobot().move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, secondRobot));

        expectedEvents.add(new Pair<>(Event.ROBOT_TELEPORTED, secondRobot));

        assertNull(game.getActiveRobot());
        assertEquals(expectedEvents, events);
        assertFalse(robot.isActive());
        assertFalse(secondRobot.isActive());
        assertEquals(GameStatus.ALL_ROBOTS_OUT, game.getStatus());
    }

    @Test
    public void test_allRobotsHasLowBattery() {
        Robot robot = game.getActiveRobot();

        game.getActiveRobot().skipStep();
        expectedEvents.add(new Pair<>(Event.ROBOT_SKIP_STEP, robot));

        Robot secondRobot = game.getActiveRobot();
        game.getActiveRobot().skipStep();
        expectedEvents.add(new Pair<>(Event.ROBOT_SKIP_STEP, secondRobot));

        for(int i = 0; i < 4; i++) {
            game.getActiveRobot().skipStep();
            expectedEvents.add(new Pair<>(Event.ROBOT_SKIP_STEP, robot));

            game.getActiveRobot().skipStep();
            expectedEvents.add(new Pair<>(Event.ROBOT_SKIP_STEP, secondRobot));
        }

        assertNull(game.getActiveRobot());
        assertEquals(expectedEvents, events);
        assertFalse(robot.isActive());
        assertFalse(secondRobot.isActive());
        assertEquals(GameStatus.ALL_ROBOTS_HAVE_LOW_CHARGE, game.getStatus());
    }

    @Test
    public void test_winnerFound() {
        Robot robot = game.getActiveRobot();

        game.getActiveRobot().move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, robot));

        Robot secondRobot = game.getActiveRobot();
        game.getActiveRobot().skipStep();
        expectedEvents.add(new Pair<>(Event.ROBOT_SKIP_STEP, secondRobot));

        game.getActiveRobot().move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.ROBOT_MOVED, robot));

        expectedEvents.add(new Pair<>(Event.ROBOT_TELEPORTED, robot));

        for(int i = 0; i < 4; i++) {
            game.getActiveRobot().skipStep();
            expectedEvents.add(new Pair<>(Event.ROBOT_SKIP_STEP, secondRobot));
        }


        assertNull(game.getActiveRobot());
        assertEquals(expectedEvents, events);
        assertFalse(robot.isActive());
        assertFalse(secondRobot.isActive());
        assertEquals(robot, game.getWinner());
        assertEquals(GameStatus.WINNER_FOUND, game.getStatus());
    }
}
