package robots.model.field.cells;

import org.jetbrains.annotations.NotNull;
import robots.Utils.BuildConfig;
import robots.model.event.ExitCellActionEvent;
import robots.model.event.ExitCellActionListener;
import robots.model.field.Cell;
import robots.model.field.CellObject;
import robots.model.field.MobileCellObject;
import robots.model.field.cell_objects.Robot;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExitCell extends Cell implements ImmutableExitCell {

    private static final long SLEEP_TIME = 100L;

    private List<Robot> teleportedRobots = new ArrayList<>();

    @Override
    public List<Robot> getTeleportedRobots() {
        return Collections.unmodifiableList(teleportedRobots); // !!! Можно использовать неизменяемый контейнер
        // DONE: Возвращаю неизменяемый контейнер
    }

    @Override
    public void addObject(@NotNull CellObject robot) {
        // !!! Кто решает, что робот может находиться в ячейке??
        // DONE: Решает робот (см. метод Robot.setPosition()).
        if(!(robot instanceof Robot)) throw new IllegalArgumentException();
        super.addObject(robot);
        if (BuildConfig.buildType == BuildConfig.BuildType.RELEASE) {
            Timer timer = new Timer(1000, e -> teleportRobot());
            timer.setRepeats(false);
            timer.start();
        } else {
            teleportRobot();
        }
    }

    private void teleportRobot() {
        Robot robot = (Robot) takeObject(getMobileCellObject());
        teleportedRobots.add(robot);
        fireRobotIsTeleported(robot);
    }

    // !!! Это где-то используется?
    // Done: Уадлил метод waitTeleportation, необходимый для проходждения тестов, добавил BuildConfig и рзвилку в setRobot

    // -------------------- События --------------------
    private ArrayList<ExitCellActionListener> exitCellListListener = new ArrayList<>();

    public void addExitCellActionListener(ExitCellActionListener listener) {
        exitCellListListener.add(listener);
    }

    public void removeExitCellActionListener(ExitCellActionListener listener) {
        exitCellListListener.remove(listener);
    }

    private void fireRobotIsTeleported(Robot robot) {
        for (ExitCellActionListener listener : exitCellListListener) {
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
