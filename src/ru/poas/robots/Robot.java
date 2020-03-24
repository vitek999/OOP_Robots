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

    void setPosition(@NotNull Cell position) {
        throw new UnsupportedOperationException();
    }

    public void move(@NotNull Direction direction) {
        throw new UnsupportedOperationException();
    }

    public void changeBattery() {
        if(position.getBattery() != null){
            battery = position.getBattery();
        }
    }

    public void skipStep() {
        throw new UnsupportedOperationException();
    }

    public boolean isAcitive() {
        throw new UnsupportedOperationException();
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public int getCharge() {
        return battery.charge();
    }

    private Cell whereCanMove(Direction direction) {
        throw new UnsupportedOperationException();
    }

    private int amountOfChargeForMove() {
        return AMOUNT_OF_CHARGE_FOR_MOVE;
    }

    private int amountOfChargeForSkipStep() {
        return AMOUNT_OF_CHARGE_FOR_SKIP_STEP;
    }

    private boolean spendBatteryCharge(int amountOfCharge, boolean ignoreShortage) {
        throw new UnsupportedOperationException();
    }

    public static boolean canStayAtPostion(Cell position) {
        throw new UnsupportedOperationException();
    }
}
