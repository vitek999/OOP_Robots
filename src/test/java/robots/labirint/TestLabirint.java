package robots.labirint;

import org.jetbrains.annotations.NotNull;
import robots.*;
import robots.labirints.Labirint;

public class TestLabirint extends Labirint {

    private static final int FIELD_HEIGHT = 3;
    private static final int FIELD_WIDTH = 3;
    private static final int DEFAULT_BATTERY_CHARGE = 10;

    @Override
    protected int fieldHeight() {
        return FIELD_HEIGHT;
    }

    @Override
    protected int fieldWidth() {
        return FIELD_WIDTH;
    }

    @Override
    protected Point exitPoint() {
        return new Point(2,2);
    }

    @Override
    protected void addRobots(@NotNull Field field) {
        Robot firstRobot = new Robot();
        firstRobot.setBattery(new Battery(10));
        Robot secondRobot = new Robot();
        secondRobot.setBattery(new Battery(10));

        field.getCell(new Point(0,2)).setRobot(firstRobot);
        field.getCell(new Point(2,0)).setRobot(secondRobot);
    }

    @Override
    protected void addBatteries(@NotNull Field field) {
        Battery battery = new Battery(DEFAULT_BATTERY_CHARGE);

        field.getCell(new Point(1, 2)).setBattery(battery);
    }

    @Override
    protected void addWalls(@NotNull Field field) {
        BetweenCellsPosition betweenCellsPosition = new BetweenCellsPosition(
                field.getCell(new Point(2, 0)),
                field.getCell(new Point(2, 1))
        );

        new Wall(betweenCellsPosition);
    }
}
