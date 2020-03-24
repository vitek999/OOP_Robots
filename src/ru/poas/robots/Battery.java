package ru.poas.robots;

public class Battery {

    private Integer charge;

    public Battery(Integer charge) {
        this.charge = charge;
    }

    public Integer releaseCharge(int chargeAmount) {
        var delta = (chargeAmount > charge) ? chargeAmount : charge;
        charge -= delta;
        return delta;
    }

    public Integer charge() {
        return charge;
    }

}
