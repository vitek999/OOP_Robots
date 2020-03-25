package ru.poas.robots;

import org.jetbrains.annotations.NotNull;

public class Robot {

    private static final int AMOUNT_OF_CHARGE_FOR_MOVE = 1;
    private static final int AMOUNT_OF_CHARGE_FOR_SKIP_STEP = 2;

    private Cell position;

    private Battery battery;

    public Cell getPosition() {
        return position;
    }

    void setPosition(Cell position) {
        this.position = position;
    }

    public void move(@NotNull Direction direction) {
        Cell newPosition = canMove(direction);
        if(newPosition != null && spendBatteryCharge(amountOfChargeForMove(), false)) {
            newPosition.takeRobot();
            newPosition.setRobot(this);
            // TODO: send event that robot moved.
        }
    }

    public void changeBattery() {
        if(position.getBattery() != null){
            battery = position.takeBattery();
        }
    }

    public void skipStep() {
        spendBatteryCharge(amountOfChargeForSkipStep(), true);
        // TODO: send event that robot skip step
    }

    public boolean isAcitive() {
        boolean result = true;
        // TODO: inplementation
        return result;
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

        if (getCharge() < amountOfCharge && !ignoreShortage ) {
            result = false;
        } else {
            battery.releaseCharge(amountOfCharge);
        }

        return result;
    }

    public static boolean canStayAtPosition(@NotNull Cell position) {
        return position.getRobot() == null;
    }
}
