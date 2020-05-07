package robots;

import robots.event.ExitCellActionEvent;
import robots.event.ExitCellActionListener;
import robots.event.RobotActionEvent;
import robots.event.RobotActionListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExitCell extends Cell {

    private static final long SLEEP_TIME = 100L;

    private List<Robot> teleportedRobots = new ArrayList<>();

    public List<Robot> getTeleportedRobots() {
        return new ArrayList<>(teleportedRobots); // !!! Можно использовать неизменяемый контейнер
    }

    @Override
    public void setRobot(Robot robot) {
        if(teleportedRobots.contains(robot)) throw new IllegalArgumentException(); // !!! Кто решает, что робот может находиться в ячейке??
        super.setRobot(robot);
        //waitTeleportation();
        Timer timer = new Timer(1000, e -> teleportRobot());
        timer.setRepeats(false);
        timer.start();
        //teleportRobot();
    }

    private void teleportRobot() {
        Robot robot = takeRobot();
        teleportedRobots.add(robot);
        fireRobotIsTeleported(robot);
    }

    private void waitTeleportation() { // !!! Это где-то используется?
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
            event.setTeleport(this);
            listener.robotIsTeleported(event);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ExitCell exitCell = (ExitCell) o;
        return Objects.equals(teleportedRobots, exitCell.teleportedRobots) &&
                Objects.equals(exitCellListListener, exitCell.exitCellListListener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "ExitCell{" +
                "teleportedRobots=" + teleportedRobots +
                ", exitCellListListener=" + exitCellListListener +
                '}';
    }
}
