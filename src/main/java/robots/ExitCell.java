package robots;

import robots.event.ExitCellActionEvent;
import robots.event.ExitCellActionListener;
import robots.event.RobotActionEvent;
import robots.event.RobotActionListener;

import java.util.ArrayList;
import java.util.List;

public class ExitCell extends Cell {

    private static final long SLEEP_TIME = 1000L;

    private List<Robot> teleportedRobots = new ArrayList<>();

    public List<Robot> getTeleportedRobots() {
        return new ArrayList<>(teleportedRobots);
    }

    @Override
    public void setRobot(Robot robot) {
        super.setRobot(robot);
        waitTeleportation();
        teleportRobot();
    }

    private void teleportRobot() {
        Robot robot = takeRobot();
        teleportedRobots.add(robot);
        fireRobotIsTeleported(robot);
    }

    private void waitTeleportation() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // -------------------- События --------------------
    private ArrayList<ExitCellActionListener> exitCellListListener = new ArrayList<>();

    public void addExitCellActionListener(ExitCellActionListener listener) {
        exitCellListListener.add(listener);
    }

    public void removeExitCellActionListener(ExitCellActionListener listener) {
        exitCellListListener.remove(listener);
    }

    private void fireRobotIsTeleported(Robot robot) {
        for(ExitCellActionListener listener: exitCellListListener) {
            ExitCellActionEvent event = new ExitCellActionEvent(listener);
            event.setRobot(robot);
            listener.robotIsTeleported(event);
        }
    }
}
