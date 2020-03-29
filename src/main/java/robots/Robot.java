package robots;

import org.jetbrains.annotations.NotNull;
import robots.event.RobotActionEvent;
import robots.event.RobotActionListener;

import java.util.ArrayList;

public class Robot {

    private static final int AMOUNT_OF_CHARGE_FOR_MOVE = 1;
    private static final int AMOUNT_OF_CHARGE_FOR_SKIP_STEP = 2;

    private Cell position;

    private Battery battery;

    private boolean isActive;

    public Cell getPosition() {
        return position;
    }

    void setPosition(Cell position) {
        this.position = position;
    }

    public void move(@NotNull Direction direction) {
        if(isActive) {
            Cell newPosition = canMove(direction);
            if (newPosition != null && spendBatteryCharge(amountOfChargeForMove(), false)) {
                position.takeRobot();
                newPosition.setRobot(this);
                fireRobotIsMoved();
            }
        }
    }

    public void changeBattery() {
        if(isActive && position.getBattery() != null){
            battery = position.takeBattery();
        }
    }

    public void skipStep() {
        if(isActive) {
            spendBatteryCharge(amountOfChargeForSkipStep(), true);
            fireRobotIsSkipStep();
        }
    }

    void setActive(boolean value) {
        isActive = value;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public int getCharge() {
        return battery.charge();
    }

    private Cell canMove(@NotNull Direction direction) {
        Cell result = null;

        Cell neighborCell = position.neighborCell(direction);
        if(neighborCell != null && canStayAtPosition(neighborCell) && position.neighborWall(direction) == null) {
            result = neighborCell;
        }

        return result;
    }

    private int amountOfChargeForMove() {
        return AMOUNT_OF_CHARGE_FOR_MOVE;
    }

    private int amountOfChargeForSkipStep() {
        return AMOUNT_OF_CHARGE_FOR_SKIP_STEP;
    }

    private boolean spendBatteryCharge(int amountOfCharge, boolean ignoreShortage) {
        boolean result = true;

        if (getCharge() < amountOfCharge) {
            if(ignoreShortage) {
                battery.releaseCharge(battery.charge());
            } else {
                result = false;
            }
        } else {
            battery.releaseCharge(amountOfCharge);
        }

        return result;
    }

    public static boolean canStayAtPosition(@NotNull Cell position) {
        return position.getRobot() == null;
    }

    // -------------------- События --------------------
    private ArrayList<RobotActionListener> robotListListener = new ArrayList<>();

    public void addRobotActionListener(RobotActionListener listener) {
        robotListListener.add(listener);
    }

    public void removeRoborActionListener(RobotActionListener listener) {
        robotListListener.remove(listener);
    }

    private void fireRobotIsMoved() {
        for(RobotActionListener listener: robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            listener.robotIsMoved(event);
        }
    }

    private void fireRobotIsSkipStep() {
        for(RobotActionListener listener: robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            listener.robotIsSkipStep(event);
        }
    }
}
