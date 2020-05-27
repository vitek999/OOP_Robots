package robots.model.field.cell_objects;

import org.jetbrains.annotations.NotNull;
import robots.model.Direction;
import robots.model.field.cells.ExitCell;
import robots.model.event.RobotActionEvent;
import robots.model.event.RobotActionListener;
import robots.model.field.Cell;
import robots.model.field.MobileCellObject;
import robots.model.field.cell_objects.power_supplies.Battery;

import java.util.ArrayList;

public class Robot extends MobileCellObject {

    private static final int AMOUNT_OF_CHARGE_FOR_MOVE = 1;
    private static final int AMOUNT_OF_CHARGE_FOR_SKIP_STEP = 2;

    private Battery innerBattery;
    private boolean isActive;

    public Robot(@NotNull Battery innerBattery) {
        this.innerBattery = innerBattery;
    }

    @Override
    public void move(@NotNull Direction direction) {
        if (isActive) {
            Cell oldPosition = position;
            Cell newPosition = canMove(direction);
            if (newPosition != null) { // !!! Запах кода - использование операции с побочным эффектом в условии
                // DONE: Вынес вызов spendBatteryCharge из условия и сохрняю резуьтат вызова в переменную isSpendChargeSuccess
                boolean isSpendChargeSuccess = spendBatteryCharge(AMOUNT_OF_CHARGE_FOR_MOVE, false);
                if (isSpendChargeSuccess) {
                    fireRobotIsMoved(oldPosition, newPosition);
                    position.takeRobot();
                    newPosition.setRobot(this);
                }
            }
        }
    }

    @Override
    protected Cell canMove(@NotNull Direction direction) {
        Cell result = null;

        Cell neighborCell = position.neighborCell(direction);
        if (neighborCell != null && canLocateAtPosition(neighborCell) && position.neighborBetweenCellObject(direction) == null) {
            result = neighborCell;
        }

        return result;
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell newPosition) { // !!! Странное название - почему "могу оставаться"?
        // DONE Переименовал метод canStayAtPosition -> canLocateAtPosition
        if ((newPosition instanceof ExitCell) && (((ExitCell) newPosition).getTeleportedRobots().contains(this))) return false;
        return newPosition.getRobot() == null;
    }

    public void changeBattery() {
        if (isActive && position.getBattery() != null) { // !!! Не соответствует диаграмме - ранее активность робота запрашивали у игры
            // DONE: Исправил диаграмму смены батарейки, теперь робот не узнаёт свою актиыность у игры.
            innerBattery = position.takeBattery();
            fireRobotChangeBattery(innerBattery);
        }
    }

    public void skipStep() {
        if (isActive) {
            spendBatteryCharge(AMOUNT_OF_CHARGE_FOR_SKIP_STEP, true);
            fireRobotIsSkipStep();
        }
    }

    public void setActive(boolean value) { // !!! Не соответствует диаграмме - ранее активность робота запрашивали у игры
        // DONE: Добавил метод на диаграммуы.
        if(getCharge() > 0 || !value) {
            isActive = value;
            fireRobotChangeActive();
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setBattery(@NotNull Battery battery) { // !!! А как же проверка, что это возможно
        // DONE: Добавил not-null проверку, все остальные варианты аргментна допустимы
        this.innerBattery = battery;
    }

    public Integer getCharge() { // !!! Если батарейки нет??
        // DONE: Батрейка теперь есть всегда, добавил параметр в конструктор и @NotNull в setBattery
        return innerBattery.getCharge();
    }

    public Integer getMaxCharge() { // !!! Если батарейки нет??
        // DONE: Батрейка теперь есть всегда, добавил параметр в конструктор и @NotNull в setBattery
        return innerBattery.getMaxCharge();
    }

    // !!! Зачем нужен метод???
    // DONE: Удалил метод amountOfChargeForMove. Вместо него использую константу AMOUNT_OF_CHARGE_FOR_MOVE

    // !!! Зачем нужен метод???
    // DONE: Удалил метод amountOfChargeForSkipStep. Вместо него использую константу AMOUNT_OF_CHARGE_FOR_SKIP_STEP

    private boolean spendBatteryCharge(int amountOfCharge, boolean ignoreShortage) {
        boolean result = true;

        int released = innerBattery.releaseCharge(amountOfCharge);
        if (released != amountOfCharge) { // !!! Не соответствует диаграмме - 02_сделать шаг. (Робот думает за батарейку)
            // DONE: Теперь батарейка возвращает сколько было потрачено при releaseCharge
            // и если оно не совпадает с запросом то робот может потратить весь заряд, если требуется.
            if (ignoreShortage) {
                innerBattery.releaseCharge(innerBattery.getCharge());
            } else {
                result = false;
            }
        }

        return result;
    }

    // -------------------- События --------------------
    private ArrayList<RobotActionListener> robotListListener = new ArrayList<>();

    public void addRobotActionListener(RobotActionListener listener) {
        robotListListener.add(listener);
    }

    public void removeRobotActionListener(RobotActionListener listener) {
        robotListListener.remove(listener);
    }

    private void fireRobotIsMoved(@NotNull Cell oldPosition, @NotNull Cell newPosition) {
        for (RobotActionListener listener : robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            event.setFromCell(oldPosition);
            event.setToCell(newPosition);
            listener.robotIsMoved(event);
        }
    }

    private void fireRobotIsSkipStep() {
        for (RobotActionListener listener : robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            listener.robotSkippedStep(event);
        }
    }

    private void fireRobotChangeActive() {
        for (RobotActionListener listener : robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            listener.robotActivityChanged(event);
        }
    }

    private void fireRobotChangeBattery(Battery changedBattery) {
        for (RobotActionListener listener : robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            event.setBattery(changedBattery);
            listener.robotChangedBattery(event);
        }
    }
}
