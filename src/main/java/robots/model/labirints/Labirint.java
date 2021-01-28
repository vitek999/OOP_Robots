package robots.model.labirints;

import org.jetbrains.annotations.NotNull;
import robots.model.field.Field;
import robots.model.Point;

/**
 * Лабиринт.
 */
public abstract class Labirint { // !!! Предполагалось, что лабиринт будет помогать строить протяженные стены
                                 // DONE: Данный класс "лабиринт" теперь отвечает за построение поля.

    // !!! Зачем нужно постоянное знание о создаваемом поле???
    // DONE (требует обсуждения): Теперь поле передаётся как изменяемый параметр в методах addRobots, addBatteries, addWalls

    /**
     * Построить поле.
     * @return поле.
     */
    public Field buildField() {

        Field field = new Field(fieldWidth(), fieldHeight(), exitPoint());

        addRobots(field);
        addPowerSupplies(field);
        addBetweenCellObjects(field);

        return field;
    }

    /**
     * Высота поля.
     * @return высота поля.
     */
    protected abstract int fieldHeight();

    /**
     * Ширина поля.
     * @return ширина поля.
     */
    protected abstract int fieldWidth();

    /**
     * Координаты ячейки выхода.
     * @return координаты ячейки выхода.
     */
    protected abstract Point exitPoint();

    /**
     * Добавить роботов на поле.
     * @param field поле.
     */
    protected abstract void addRobots(@NotNull Field field);

    /**
     * Добавить истчоники питания на поле.
     * @param field поле.
     */
    protected abstract void addPowerSupplies(@NotNull Field field);

    /**
     * Добавить объекты между ячейками на поле.
     * @param field поле.
     */
    protected abstract void addBetweenCellObjects(@NotNull Field field);

}
