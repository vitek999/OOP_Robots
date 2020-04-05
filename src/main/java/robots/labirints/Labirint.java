package robots.labirints;

import robots.Field;
import robots.Point;

public abstract class Labirint {

    protected Field field;

    public Field buildField() {

        field = new Field(fieldWidth(), fieldHeight(), exitPoint());

        addRobots();
        addBatteries();
        addWalls();

        return field;
    }

    protected abstract int fieldHeight();

    protected abstract int fieldWidth();

    protected abstract Point exitPoint();

    protected abstract void addRobots();

    protected abstract void addBatteries();

    protected abstract void addWalls();

}
