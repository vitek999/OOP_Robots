package robots.labirints;

import robots.Field;
import robots.Point;

public abstract class Labirint { // !!! Предполагалось, что лабиринт будет помогать строить протяженные стены
                                 // DONE: Данный класс "лабиринт" теперь отвечает за построение поля.

    protected Field field; // !!! Зачем нужно постоянное знание о создаваемом поле???

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
