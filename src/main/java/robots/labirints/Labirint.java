package robots.labirints;

import org.jetbrains.annotations.NotNull;
import robots.Field;
import robots.Point;

public abstract class Labirint { // !!! Предполагалось, что лабиринт будет помогать строить протяженные стены
                                 // DONE: Данный класс "лабиринт" теперь отвечает за построение поля.

    // !!! Зачем нужно постоянное знание о создаваемом поле???
    // DONE (требует обсуждения): Теперь поле передаётся как изменяемый параметр в методах addRobots, addBatteries, addWalls

    public Field buildField() {

        Field field = new Field(fieldWidth(), fieldHeight(), exitPoint());

        addRobots(field);
        addBatteries(field);
        addWalls(field);

        return field;
    }

    protected abstract int fieldHeight();

    protected abstract int fieldWidth();

    protected abstract Point exitPoint();

    protected abstract void addRobots(@NotNull Field field);

    protected abstract void addBatteries(@NotNull Field field);

    protected abstract void addWalls(@NotNull Field field);

}
