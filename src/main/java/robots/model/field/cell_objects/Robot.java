package robots.model.field.cell_objects;

import org.jetbrains.annotations.NotNull;
import robots.model.Direction;
import robots.model.event.RobotActionEvent;
import robots.model.event.RobotActionListener;
import robots.model.field.BetweenCellObject;
import robots.model.field.Cell;
import robots.model.field.MobileCellObject;
import robots.model.field.between_cells_objects.BetweenCellObjectWithAction;
import robots.model.field.between_cells_objects.Door;
import robots.model.field.cell_objects.power_supplies.Portable;
import robots.model.field.cell_objects.power_supplies.PowerSupply;
import robots.model.field.cell_objects.power_supplies.RechargeablePowerSupply;
import robots.model.field.cell_objects.power_supplies.Windmill;
import robots.model.field.cells.CellWithPowerSupply;
import robots.model.field.cells.ExitCell;

import java.util.ArrayList;
import java.util.Map;

/**
 * Робот.
 */
public class Robot extends MobileCellObject {

    /**
     * Количество заряда для перемещения.
     */
    private static final int AMOUNT_OF_CHARGE_FOR_MOVE = 1;

    /**
     * Количество заряда для пропуска хода.
     */
    private static final int AMOUNT_OF_CHARGE_FOR_SKIP_STEP = 2;

    /**
     * Внутренний источник питания робота.
     */
    private PowerSupply innerPowerSupply;

    /**
     * Состояние активности робота.
     */
    private boolean isActive;

    /**
     * Констрктор.
     * @param innerPowerSupply внутренний источник питания.
     */
    public Robot(@NotNull PowerSupply innerPowerSupply) {
        this.innerPowerSupply = innerPowerSupply;
    }

    @Override
    public void move(@NotNull Direction direction) {
        if (isActive) {
            Cell oldPosition = position;
            Cell newPosition = canMove(direction);
            if (newPosition != null) { // !!! Запах кода - использование операции с побочным эффектом в условии
                // DONE: Вынес вызов spendBatteryCharge из условия и сохрняю резуьтат вызова в переменную isSpendChargeSuccess
                boolean isSpendChargeSuccess = spendPowerSupplyCharge(AMOUNT_OF_CHARGE_FOR_MOVE, false);
                if (isSpendChargeSuccess) {
                    fireRobotIsMoved(oldPosition, newPosition);
                    position.takeObject(position.getMobileCellObject());
                    newPosition.addObject(this);
                }
            }
        }
    }

    @Override
    protected Cell canMove(@NotNull Direction direction) {
        Cell result = null;

        Cell neighborCell = position.getNeighborCell(direction);
        BetweenCellObject betweenCellObject = position.getNeighborBetweenCellObject(direction);
        if (neighborCell != null && canLocateAtPosition(neighborCell) && ((betweenCellObject instanceof Door &&
                ((Door) betweenCellObject).isOpen()) || betweenCellObject == null)) {
            result = neighborCell;
        }

        return result;
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell newPosition) { // !!! Странное название - почему "могу оставаться"?
        // DONE Переименовал метод canStayAtPosition -> canLocateAtPosition
        boolean withCorrectPowerSupply = !(newPosition instanceof CellWithPowerSupply) || !(((CellWithPowerSupply) newPosition).getPowerSupply() instanceof Windmill);
        if(!withCorrectPowerSupply) return false;
        if ((newPosition instanceof ExitCell) && (((ExitCell) newPosition).getTeleportedRobots().contains(this))) return false;
        return newPosition.getMobileCellObject() == null;
    }

    /**
     * Заменить источник питания {@link Robot#innerPowerSupply}.
     */
    public void changePowerSupply() {
        if (isActive && (position instanceof CellWithPowerSupply)
                && ((CellWithPowerSupply) position).getPowerSupply() != null
                && (((CellWithPowerSupply) position).getPowerSupply() instanceof Portable)) { // !!! Не соответствует диаграмме - ранее активность робота запрашивали у игры
            // DONE: Исправил диаграмму смены батарейки, теперь робот не узнаёт свою актиыность у игры.
            innerPowerSupply = (PowerSupply) position.takeObject(((CellWithPowerSupply) position).getPowerSupply());
            fireRobotChangeBattery(innerPowerSupply);
        }
    }

    /**
     * Зарядить источник питания {@link Robot#innerPowerSupply}.
     */
    public void chargePowerSupply() {
        if (isActive && innerPowerSupply instanceof RechargeablePowerSupply) {
            Map<Direction, Cell> neighborCells = position.getNeighborCells();
            for (Map.Entry<Direction, Cell> item : neighborCells.entrySet()) {
                if (item.getValue() instanceof CellWithPowerSupply) {
                    PowerSupply cellPowerSupply = ((CellWithPowerSupply) item.getValue()).getPowerSupply();
                    if (cellPowerSupply != null) {
                        ((RechargeablePowerSupply) innerPowerSupply).charge(cellPowerSupply);
                        fireRobotChargedPowerSupply(cellPowerSupply);
                    }
                }
            }
        }
    }

    /**
     * Пропустить ход.
     */
    public void skipStep() {
        if (isActive) {
            spendPowerSupplyCharge(AMOUNT_OF_CHARGE_FOR_SKIP_STEP, true);
            fireRobotIsSkipStep();
        }
    }

    /**
     * Сделать робота активным {@link Robot#isActive}.
     * @param value состояние активности.
     */
    public void setActive(boolean value) { // !!! Не соответствует диаграмме - ранее активность робота запрашивали у игры
        // DONE: Добавил метод на диаграммуы.
        if (getCharge() > 0 || !value) {
            isActive = value;
            fireRobotChangeActive();
        }
    }

    /**
     * Получить состояние активности робота {@link Robot#isActive}.
     * @return состояние активности робота.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Установить источник питания {@link Robot#innerPowerSupply}
     * @param powerSupply источник питания.
     */
    public void setPowerSupply(@NotNull PowerSupply powerSupply) { // !!! А как же проверка, что это возможно
        // DONE: Добавил not-null проверку, все остальные варианты аргментна допустимы
        this.innerPowerSupply = powerSupply;
    }

    /**
     * Получить заряд {@link PowerSupply#getCharge()}.
     * @return заряд.
     */
    public Integer getCharge() { // !!! Если батарейки нет??
        // DONE: Батрейка теперь есть всегда, добавил параметр в конструктор и @NotNull в setBattery
        return innerPowerSupply.getCharge();
    }

    /**
     * Получить максимальный заряд {@link PowerSupply#getMaxCharge()}.
     * @return максимальный заряд.
     */
    public Integer getMaxCharge() { // !!! Если батарейки нет??
        // DONE: Батрейка теперь есть всегда, добавил параметр в конструктор и @NotNull в setBattery
        return innerPowerSupply.getMaxCharge();
    }

    /**
     * Получить источник питания {@link Robot#innerPowerSupply}.
     * @return источник питания.
     */
    public PowerSupply getPowerSupply() {
        return innerPowerSupply;
    }

    /**
     * Потратить заряд источника питания {@link Robot#innerPowerSupply}.
     * @param amountOfCharge запрашиваемое кол-во заряда.
     * @param ignoreShortage игнорировать ли нехватку заряда.
     * @return отдал ли источник питания запрашиваемое кол-во заряда.
     */
    private boolean spendPowerSupplyCharge(int amountOfCharge, boolean ignoreShortage) {
        boolean result = true;

        int released = innerPowerSupply.releaseCharge(amountOfCharge);
        if (released != amountOfCharge) { // !!! Не соответствует диаграмме - 02_сделать шаг. (Робот думает за батарейку)
            // DONE: Теперь батарейка возвращает сколько было потрачено при releaseCharge
            // и если оно не совпадает с запросом то робот может потратить весь заряд, если требуется.
            if (ignoreShortage) {
                innerPowerSupply.releaseCharge(innerPowerSupply.getCharge());
            } else {
                result = false;
            }
        }

        return result;
    }

    /**
     * Выполнить дейстиве с объектами {@link BetweenCellObjectWithAction} вокруг позиции робота {@link Robot#position}
     */
    public void performAction() {
        if (isActive) {
            Map<Direction, BetweenCellObject> neighborBetweenCellObjects = position.getNeighborBetweenCellObjects();
            for (Map.Entry<Direction, BetweenCellObject> item : neighborBetweenCellObjects.entrySet()) {
                BetweenCellObject betweenCellObject = item.getValue();
                if (betweenCellObject instanceof BetweenCellObjectWithAction) {
                    BetweenCellObjectWithAction betweenCellObjectWithAction = (BetweenCellObjectWithAction) betweenCellObject;
                    int actionChargeAmount = betweenCellObjectWithAction.actionCost();
                    boolean isActionDone = spendPowerSupplyCharge(actionChargeAmount, false);
                    if (isActionDone) betweenCellObjectWithAction.perform();
                }
            }
        }
    }

    /**
     * Список слушателей, подписанных на события игры.
     */
    private final ArrayList<RobotActionListener> robotListListener = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями игры.
     * @param listener слушатель.
     */
    public void addRobotActionListener(RobotActionListener listener) {
        robotListListener.add(listener);
    }

    /**
     * Удалить слушателя за событиями игры.
     * @param listener слушатель.
     */
    public void removeRobotActionListener(RobotActionListener listener) {
        robotListListener.remove(listener);
    }

    /**
     * Оповестить сулшателей {@link Robot#robotListListener}, что робот переместился.
     * @param oldPosition ячейка откуда переместился робот.
     * @param newPosition ячейка куда переместился робот.
     */
    private void fireRobotIsMoved(@NotNull Cell oldPosition, @NotNull Cell newPosition) {
        for (RobotActionListener listener : robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            event.setFromCell(oldPosition);
            event.setToCell(newPosition);
            listener.robotIsMoved(event);
        }
    }

    /**
     * Оповестить сулшателей {@link Robot#robotListListener}, что робот пропустил ход.
     */
    private void fireRobotIsSkipStep() {
        for (RobotActionListener listener : robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            listener.robotSkippedStep(event);
        }
    }

    /**
     * Оповестить сулшателей {@link Robot#robotListListener}, что состояние активности робота изменилось.
     */
    private void fireRobotChangeActive() {
        for (RobotActionListener listener : robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            listener.robotActivityChanged(event);
        }
    }

    /**
     * Оповестить сулшателей {@link Robot#robotListListener}, что робот сменил источник питания.
     * @param changedPowerSupply новый источник питания.
     */
    private void fireRobotChangeBattery(PowerSupply changedPowerSupply) {
        for (RobotActionListener listener : robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            event.setPowerSupply(changedPowerSupply);
            listener.robotChangedPowerSupply(event);
        }
    }

    /**
     * Оповестить сулшателей {@link Robot#robotListListener}, что робот зарядил источник питания.
     * @param sourcePowerSupply источник питания, от которого происходила зарядка.
     */
    private void fireRobotChargedPowerSupply(@NotNull PowerSupply sourcePowerSupply) {
        for (RobotActionListener listener : robotListListener) {
            RobotActionEvent event = new RobotActionEvent(listener);
            event.setRobot(this);
            event.setPowerSupply(sourcePowerSupply);
            listener.robotChargedPowerSupply(event);
        }
    }

}
