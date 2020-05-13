package robots;

import org.jetbrains.annotations.NotNull;
import robots.event.RobotActionEvent;
import robots.event.RobotActionListener;

import java.util.ArrayList;

public class Robot {

    private static final int AMOUNT_OF_CHARGE_FOR_MOVE = 1;
    private static final int AMOUNT_OF_CHARGE_FOR_SKIP_STEP = 2;

    private Cell position;

    private Battery innerBattery; // !!! Не понятно, о какой, батарейке идет речь, робот взаимодействует с многими батарейками
                                  // DONE: Переименовал battery -> innerBattery

    private boolean isActive;

    public Cell getPosition() {
        return position;
    }

    void setPosition(Cell position) { // !!! Не соответсвует диаграмме
        this.position = position;
    }

    public void move(@NotNull Direction direction) {
        if(isActive) {
            Cell oldPosition = position;
            Cell newPosition = canMove(direction);
            if (newPosition != null && spendBatteryCharge(amountOfChargeForMove(), false)) { // !!! Запах кода - использование операции с побочным эффектом в условии
                fireRobotIsMoved(oldPosition, newPosition);
                position.takeRobot();
                newPosition.setRobot(this);
            }
        }
    }

    public void changeBattery() {
        if(isActive && position.getBattery() != null){ // !!! Не соответствует диаграмме - ранее активность робота запрашивали у игры
            innerBattery = position.takeBattery();
            fireRobotChangeBattery(innerBattery);
        }
    }

    public void skipStep() {
        if(isActive) {
            spendBatteryCharge(amountOfChargeForSkipStep(), true);
            fireRobotIsSkipStep();
        }
    }

    void setActive(boolean value) { // !!! Не соответствует диаграмме - ранее активность робота запрашивали у игры
        isActive = value;
        fireRobotChangeActive();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setBattery(Battery battery) { // !!! А как же проверка, что это возможно
        this.innerBattery = battery;
    }

    public Integer getCharge() { // !!! Если батарейки нет??
        return innerBattery.charge();
    }

    public Integer getMaxCharge() { // !!! Если батарейки нет??
        return innerBattery.maxCharge();
    }

    private Cell canMove(@NotNull Direction direction) {
        Cell result = null;

        Cell neighborCell = position.neighborCell(direction);
        if(neighborCell != null && canStayAtPosition(neighborCell) && position.neighborWall(direction) == null) {
            result = neighborCell;
        }

        return result;
    }

    private int amountOfChargeForMove() { // !!! Зачем нужен метод???
        return AMOUNT_OF_CHARGE_FOR_MOVE;
    }

    private int amountOfChargeForSkipStep() { // !!! Зачем нужен метод???
        return AMOUNT_OF_CHARGE_FOR_SKIP_STEP;
    }

    private boolean spendBatteryCharge(int amountOfCharge, boolean ignoreShortage) {
        boolean result = true;

        int released = innerBattery.releaseCharge(amountOfCharge);
        if(released != amountOfCharge) { // !!! Не соответствует диаграмме - 02_сделать шаг. (Робот думает за батарейку)
                                         // DONE: Теперь батарейка возвращает сколько было потрачено при releaseCharge
                                         // и если оно не совпадает с запросом то робот может потратить весь заряд, если требуется.
            if (ignoreShortage) {
                innerBattery.releaseCharge(innerBattery.charge());
            } else {
                result = false;
            }
        }

        return result;
    }

    public static boolean canStayAtPosition(@NotNull Cell position) { // !!! Странное название - почему "могу оставаться"?
        return position.getRobot() == null;
    }

    // -------------------- События --------------------
    private ArrayList<RobotActionListener> robotListListener = new ArrayList<>();

    public void addRobotActionListener(RobotActionListener listener) {
        robotListListener.add(listener);
    }

    public void removeRoborActionListener(RobotActionListener listener) {
        robotListListener.remove(listener);
    }

    private void fireRobotIsMoved(@NotNull Cell oldPosition,@NotNull Cell newPosition) {
        for(RobotActionListener listener: robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            event.setFromCell(oldPosition);
            event.setToCell(newPosition);
            listener.robotIsMoved(event);
        }
    }

    private void fireRobotIsSkipStep() {
        for(RobotActionListener listener: robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            listener.robotSkippedStep(event);
        }
    }

    private void fireRobotChangeActive() {
        for(RobotActionListener listener: robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            listener.robotActivityChanged(event);
        }
    }

    private void fireRobotChangeBattery(Battery changedBattery) {
        for(RobotActionListener listener: robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            event.setBattery(changedBattery);
            listener.robotChangedBattery(event);
        }
    }
}
