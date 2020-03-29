package robots;

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
        // TODO: send event, that robot is teleported;
    }

    private void waitTeleportation() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
