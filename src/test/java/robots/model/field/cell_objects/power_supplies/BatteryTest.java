package robots.model.field.cell_objects.power_supplies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.model.field.Cell;
import robots.model.field.CellTestModel;
import robots.model.field.cells.CellWithPowerSupply;

import static org.junit.jupiter.api.Assertions.*;

class BatteryTest {

    private static final int DEFAULT_TEST_BATTERY_CHARGE = 10;

    private Battery battery;

    @BeforeEach
    public void testSetup() {
        battery = new Battery(DEFAULT_TEST_BATTERY_CHARGE);
    }

    @Test
    public void test_crete_withNegativeCharge() {
        assertThrows(IllegalArgumentException.class, () -> new Battery(-1));
    }

    @Test
    public void test_Battery_createAndGetCharge() {
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, battery.getCharge());
    }

    @Test
    public void test_releaseCharge_whenChargeAmountLessCharge() {
        int chargeAmount = 5;
        assertEquals(chargeAmount, battery.releaseCharge(chargeAmount));
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE - chargeAmount, battery.getCharge());
    }

    @Test
    public void test_releaseCharge_whenChargeEqualsCharge() {
        int chargeAmount = DEFAULT_TEST_BATTERY_CHARGE;
        assertEquals(chargeAmount, battery.releaseCharge(chargeAmount));
        assertEquals(0, battery.getCharge());
    }

    @Test
    public void test_canLocateAtPosition_inEmptyCell() {
        CellWithPowerSupply cellWithPowerSupply = new CellWithPowerSupply();

        boolean result = battery.canLocateAtPosition(cellWithPowerSupply);

        assertTrue(result);
    }

    @Test
    public void test_canLocateAtPosition_inCellWithBattery() {
        Battery anotherBattery = new Battery(10);
        CellWithPowerSupply cellWithPowerSupply = new CellWithPowerSupply();
        cellWithPowerSupply.addObject(anotherBattery);

        boolean result = battery.canLocateAtPosition(cellWithPowerSupply);

        assertFalse(result);
    }

    @Test
    public void test_canLocateAtPosition_alreadyHavePosition() {
        CellWithPowerSupply cellWithPowerSupply = new CellWithPowerSupply();
        cellWithPowerSupply.addObject(battery);

        boolean result = battery.canLocateAtPosition(cellWithPowerSupply);

        assertFalse(result);
    }

    @Test
    public void test_canLocateAtPosition_inNotCellWithPowerSupply() {
        Cell cell = new CellTestModel();

        boolean result = battery.canLocateAtPosition(cell);

        assertFalse(result);
    }

    @Test
    public void test_releaseCharge_whenChargeAmountMoreThanCharge() {
        int chargeAmount = 11;
        assertEquals(0, battery.releaseCharge(chargeAmount));
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, battery.getCharge());
    }
}