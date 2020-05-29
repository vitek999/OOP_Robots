package robots.model.field.cell_objects.power_supplies;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import robots.model.event.WindmillActionEvent;
import robots.model.event.WindmillActionListener;
import robots.model.field.Cell;
import robots.model.field.CellTestModel;
import robots.model.field.MobileCellObject;
import robots.model.field.cell_objects.Robot;
import robots.model.field.cells.CellWithPowerSupply;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WindmillTest {

    private final static int UPDATE_CHARGE_DELTA = 2;
    private final static int MAX_CHARGE = 10;

    private int eventCount = 0;

    private class WindmillObserver implements WindmillActionListener {

        @Override
        public void windmillChargeIsUpdated(@NotNull WindmillActionEvent event) {
            eventCount++;
        }
    }

    @Test
    public void test_update_deltaLessThanDifferenceBetweenChargeAndMaxCharge() {
        int windmillCharge = 5;
        Windmill windmill = new Windmill(windmillCharge, MAX_CHARGE);
        windmill.addWindmillActionListener(new WindmillObserver());

        windmill.update();

        assertEquals(windmillCharge + UPDATE_CHARGE_DELTA, windmill.getCharge());
        assertEquals(1, eventCount);
    }

    @Test
    public void test_update_deltaEqualsToDifferenceBetweenChargeAndMaxCharge() {
        int windmillCharge = MAX_CHARGE - UPDATE_CHARGE_DELTA;
        Windmill windmill = new Windmill(windmillCharge, MAX_CHARGE);
        windmill.addWindmillActionListener(new WindmillObserver());

        windmill.update();

        assertEquals(MAX_CHARGE, windmill.getCharge());
        assertEquals(1, eventCount);
    }

    @Test
    public void test_update_deltaMoreThanDifferenceBetweenChargeAndMaxCharge() {
        int windMillCharge = 9;
        Windmill windmill = new Windmill(windMillCharge, MAX_CHARGE);
        windmill.addWindmillActionListener(new WindmillObserver());

        windmill.update();

        assertEquals(MAX_CHARGE, windmill.getCharge());
        assertEquals(1, eventCount);
    }

    @Test
    public void test_update_windmillIsFull() {
        Windmill windmill = new Windmill(MAX_CHARGE, MAX_CHARGE);
        windmill.addWindmillActionListener(new WindmillObserver());

        windmill.update();

        assertEquals(MAX_CHARGE, windmill.getCharge());
        assertEquals(0, eventCount);
    }

    @Test
    public void test_canLocateAtPosition_inEmptyCell() {
        Windmill windmill = new Windmill(MAX_CHARGE, MAX_CHARGE);
        CellWithPowerSupply cellWithPowerSupply = new CellWithPowerSupply();

        boolean result = windmill.canLocateAtPosition(cellWithPowerSupply);

        assertTrue(result);
    }

    @Test
    public void test_canLocateAtPosition_inCellWithBattery() {
        Windmill windmill = new Windmill(MAX_CHARGE, MAX_CHARGE);
        Battery anotherBattery = new Battery(10);
        CellWithPowerSupply cellWithPowerSupply = new CellWithPowerSupply();
        cellWithPowerSupply.addObject(anotherBattery);

        boolean result = windmill.canLocateAtPosition(cellWithPowerSupply);

        assertFalse(result);
    }

    @Test
    public void test_canLocateAtPosition_alreadyHavePosition() {
        Windmill windmill = new Windmill(MAX_CHARGE, MAX_CHARGE);
        CellWithPowerSupply cellWithPowerSupply = new CellWithPowerSupply();
        cellWithPowerSupply.addObject(windmill);

        boolean result = windmill.canLocateAtPosition(cellWithPowerSupply);

        assertFalse(result);
    }

    @Test
    public void test_canLocateAtPosition_inNotCellWithPowerSupply() {
        Windmill windmill = new Windmill(MAX_CHARGE, MAX_CHARGE);
        Cell cell = new CellTestModel();

        boolean result = windmill.canLocateAtPosition(cell);

        assertFalse(result);
    }

    @Test
    public void test_canLocateAtPosition_inCellWithMobileCellObject() {
        Windmill windmill = new Windmill(MAX_CHARGE, MAX_CHARGE);
        MobileCellObject mobileCellObject = new Robot(new Battery(MAX_CHARGE));
        Cell cell = new CellTestModel();
        cell.addObject(mobileCellObject);

        boolean result = windmill.canLocateAtPosition(cell);

        assertFalse(result);
    }
}
