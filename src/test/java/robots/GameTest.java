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
        game = new Game(new TestLabirint());
    }

    @Test
    public void test_passMoveNextRobot() {
        game.activeRobot().move(Direction.EAST);
    }
}
