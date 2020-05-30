package robots.ui.cell;

import robots.model.field.cell_objects.power_supplies.Accumulator;
import robots.model.field.cell_objects.power_supplies.PowerSupply;
import robots.ui.utils.ImageUtils;

import java.awt.*;
import java.io.File;

/**
 * Виджет аккумулятора.
 * @see Accumulator
 */
public class AccumulatorWidget extends PowerSupplyWidget{

    /**
     * Конструтор.
     * @param accumulator аккумулятор.
     */
    public AccumulatorWidget(Accumulator accumulator) {
        super(accumulator);
    }

    @Override
    protected Dimension getDimension() {
        Dimension dimension = null;

        if(cellItemState == State.SMALL) {
            dimension = new Dimension(36, 36);
        } else if (cellItemState == State.DEFAULT) {
            dimension = new Dimension(120, 120);
        }

        return dimension;
    }

    @Override
    protected File getImageFile() {
        File file = null;
        if (cellItemState == State.SMALL) {
            file = new File(ImageUtils.IMAGE_PATH + "AS.png");
        } else if (cellItemState == State.DEFAULT) {
            file = new File(ImageUtils.IMAGE_PATH + "AB.png");
        }
        return file;
    }

}
