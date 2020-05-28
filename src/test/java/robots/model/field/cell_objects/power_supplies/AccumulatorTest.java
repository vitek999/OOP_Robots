package robots.model.field.cell_objects.power_supplies;

import org.junit.jupiter.api.Test;
import robots.model.field.Cell;
import robots.model.field.CellTestModel;
import robots.model.field.cells.CellWithPowerSupply;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AccumulatorTest {

    private static final int MAX_CHARGE = 10;

    @Test
    public void test_create_withChargeMoreThenMaxCharge() {
        assertThrows(IllegalArgumentException.class, () -> new Accumulator(2,1));
    }

    @Test
    public void test_create_withNegativeCharge() {
        assertThrows(IllegalArgumentException.class, () -> new Accumulator(-1, MAX_CHARGE));
    }

    @Test
    public void test_create_withNegativeMaxCharge() {
        assertThrows(IllegalArgumentException.class, () -> new Accumulator(0, -1));
    }

    @Test
    public void test_charge_SourceHaveEnoughChargeAndIsRenewable() {
        Accumulator accumulator = new Accumulator(5, MAX_CHARGE);
        int chargeDifference = accumulator.getMaxCharge() - accumulator.getCharge();
        Windmill windmill = new Windmill(MAX_CHARGE, MAX_CHARGE);

        accumulator.charge(windmill);

        assertEquals(MAX_CHARGE, accumulator.getCharge());
        assertEquals(windmill.getMaxCharge() - chargeDifference, windmill.getCharge());
    }

    @Test
    public void test_charge_SourceEmptyAndIsRenewable() {
        int currentCharge = 5;
        Accumulator accumulator = new Accumulator(currentCharge, MAX_CHARGE);
        Windmill windmill = new Windmill(0, MAX_CHARGE);

        accumulator.charge(windmill);

        assertEquals(currentCharge, accumulator.getCharge());
        assertEquals(0, windmill.getCharge());
    }

    @Test
    public void test_charge_SourceHaveChargeLessThenDifferenceBetweenAccumulatorChargeAndMaxCharge() {
        int currentCharge = 5;
        int windmillCharge = 2;
        Accumulator accumulator = new Accumulator(currentCharge, MAX_CHARGE);
        Windmill windmill = new Windmill(windmillCharge, MAX_CHARGE);

        accumulator.charge(windmill);

        assertEquals(currentCharge + windmillCharge, accumulator.getCharge());
        assertEquals(0, windmill.getCharge());
    }

    @Test
    public void test_charge_fullAccumulatorFromNotEmptyAndRenewableSource() {
        Accumulator accumulator = new Accumulator(MAX_CHARGE, MAX_CHARGE);
        int windmillCharge = 5;
        Windmill windmill = new Windmill(windmillCharge, MAX_CHARGE);

        accumulator.charge(windmill);

        assertEquals(MAX_CHARGE, accumulator.getCharge());
        assertEquals(windmillCharge, windmill.getCharge());
    }

    @Test
    public void test_canLocateAtPosition_inEmptyCell() {
        Accumulator accumulator = new Accumulator(MAX_CHARGE, MAX_CHARGE);
        CellWithPowerSupply cellWithPowerSupply = new CellWithPowerSupply();

        boolean result = accumulator.canLocateAtPosition(cellWithPowerSupply);

        assertTrue(result);
    }

    @Test
    public void test_canLocateAtPosition_inCellWithBattery() {
        Accumulator accumulator = new Accumulator(MAX_CHARGE, MAX_CHARGE);
        Battery anotherBattery = new Battery(10);
        CellWithPowerSupply cellWithPowerSupply = new CellWithPowerSupply();
        cellWithPowerSupply.addObject(anotherBattery);

        boolean result = accumulator.canLocateAtPosition(cellWithPowerSupply);

        assertFalse(result);
    }

    @Test
    public void test_canLocateAtPosition_alreadyHavePosition() {
        Accumulator accumulator = new Accumulator(MAX_CHARGE, MAX_CHARGE);
        CellWithPowerSupply cellWithPowerSupply = new CellWithPowerSupply();
        cellWithPowerSupply.addObject(accumulator);

        boolean result = accumulator.canLocateAtPosition(cellWithPowerSupply);

        assertFalse(result);
    }

    @Test
    public void test_canLocateAtPosition_inNotCellWithPowerSupply() {
        Accumulator accumulator = new Accumulator(MAX_CHARGE, MAX_CHARGE);
        Cell cell = new CellTestModel();

        boolean result = accumulator.canLocateAtPosition(cell);

        assertFalse(result);
    }

    @Test
    public void test_charge_fromIsNotRenewableSource() {
        int accumulatorCharge = 5;
        Accumulator accumulator = new Accumulator(accumulatorCharge, MAX_CHARGE);
        Accumulator sourceAccumulator = new Accumulator(MAX_CHARGE, MAX_CHARGE);

        accumulator.charge(sourceAccumulator);

        assertEquals(accumulatorCharge, accumulator.getCharge());
        assertEquals(MAX_CHARGE, sourceAccumulator.getCharge());
    }
}
