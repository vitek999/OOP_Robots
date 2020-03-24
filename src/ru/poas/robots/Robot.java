package ru.poas.robots;

public class Robot {

    private Cell position;

    private Battery battery;

    public Cell getPosition() {
        return position;
    }

    void setPosition(Cell position) {
        throw new UnsupportedOperationException();
    }

    public void move(Direction direction) {
        throw new UnsupportedOperationException();
    }

    public void changeBattery() {
        throw new UnsupportedOperationException();
    }

    public void skipStep() {
        throw new UnsupportedOperationException();
    }

    public Boolean isAcitive() {
        throw new UnsupportedOperationException();
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public Integer getCharge() {
        throw new UnsupportedOperationException();
    }

    private Cell whereCanMove(Direction direction) {
        throw new UnsupportedOperationException();
    }

    private Integer amountOfChargeForMove() {
        throw new UnsupportedOperationException();
    }

    private Integer amountOfChargeForSkipStep() {
        throw new UnsupportedOperationException();
    }

    private Boolean spendBatteryCharge() {
        throw new UnsupportedOperationException();
    }

    public static Boolean canStayAtPostion(Cell position) {
        throw new UnsupportedOperationException();
    }
}
