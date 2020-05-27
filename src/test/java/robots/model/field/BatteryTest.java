package robots.model.field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.model.field.cell_objects.power_supplies.Battery;

import static org.junit.jupiter.api.Assertions.*;

class BatteryTest {

    private static final int DEFAULT_TEST_BATTERY_CHARGE = 10;

    private Battery battery;

    @BeforeEach
    public void testSetup() {
        battery = new Battery(DEFAULT_TEST_BATTERY_CHARGE);
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
    public void test_releaseCharge_whenChargeAmountMoreThanCharge() {
        int chargeAmount = 11;
        assertEquals(0, battery.releaseCharge(chargeAmount));
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, battery.getCharge());
    }
}