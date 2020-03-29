package robots;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.event.ExitCellActionEvent;
import robots.event.ExitCellActionListener;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ExitCellTest {

    private ExitCell exitCell;
    private Robot robot;

    private int countEvents = 0;

    private class EventListener implements ExitCellActionListener {

        @Override
        public void robotIsTeleported(ExitCellActionEvent event) {
            countEvents += 1;
        }
    }

    @BeforeEach
    public void testSetup() {
        // Clear events count
        countEvents = 0;

        // setting up robot
        robot = new Robot();

        exitCell = new ExitCell();
        exitCell.addExitCellActionListener(new EventListener());
    }

    @Test
    public void test_setRobot_oneRobot() {
        exitCell.setRobot(robot);

        int expectedCountEvents = 1;

        assertEquals(expectedCountEvents, countEvents);
        assertNull(robot.getPosition());
        assertTrue(exitCell.getTeleportedRobots().contains(robot));
        assertEquals(1, exitCell.getTeleportedRobots().size());
    }

    @Test
    public void test_setRobot_setTeleportedRobot() {
        exitCell.setRobot(robot);

        int expectedCountEvents = 1;

        assertThrows(IllegalArgumentException.class, () -> exitCell.setRobot(robot));
        assertEquals(expectedCountEvents, countEvents);
        assertNull(robot.getPosition());
        assertTrue(exitCell.getTeleportedRobots().contains(robot));
        assertEquals(1, exitCell.getTeleportedRobots().size());
    }

    @Test
    public void test_setRobot_setSeveralRobots() {
        Robot anotherRobot = new Robot();

        exitCell.setRobot(robot);
        exitCell.setRobot(anotherRobot);

        int expectedCountEvents = 2;

        assertEquals(expectedCountEvents, countEvents);
        assertNull(robot.getPosition());
        assertTrue(exitCell.getTeleportedRobots().containsAll(Arrays.asList(robot, anotherRobot)));
        assertEquals(2, exitCell.getTeleportedRobots().size());
    }

    @Test
    public void test_getTeleportedRobots_empty() {
        assertTrue(exitCell.getTeleportedRobots().isEmpty());
    }
}
