package robots.model.field.cells;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import robots.model.field.cell_objects.power_supplies.Battery;
import robots.model.field.cell_objects.power_supplies.PowerSupply;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CellWithPowerSupplyTest {

    private CellWithPowerSupply cell;
    private PowerSupply powerSupply;

    @BeforeEach
    public void testSetup() {
        cell = new CellWithPowerSupply();
        powerSupply = new Battery(10);
    }

    @Test
    public void test_setPowerSupply_inEmptyCell() {

        cell.addObject(powerSupply);

        assertEquals(powerSupply, cell.getPowerSupply());
    }

    @Test
    public void test_setPowerSupply_inCellWithPowerSupply() {
        cell.addObject(powerSupply);
        PowerSupply anotherPowerSupply = new Battery(10);

        assertThrows(IllegalArgumentException.class, () -> cell.addObject(anotherPowerSupply));
    }

    @Test
    public void test_setPowerSupply_alreadySetPowerSupplyToAnotherCell() {
        cell.addObject(powerSupply);

        CellWithPowerSupply anotherCell = new CellWithPowerSupply();

        assertThrows(IllegalArgumentException.class, () -> anotherCell.addObject(powerSupply));
    }

    @Test
    public void test_takePowerSupply_fromCellWithPowerSupply(){
        cell.addObject(powerSupply);

        assertEquals(powerSupply, cell.takeObject(cell.getPowerSupply()));
        assertNull(cell.getPowerSupply());
        assertNull(powerSupply.getPosition());
    }

    @Test
    public void test_takePowerSupply_fromCellWithoutPowerSupply() {
        assertNull(cell.takeObject(cell.getPowerSupply()));
    }
}
