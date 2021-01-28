package robots.ui.cell;

import robots.model.field.cell_objects.power_supplies.Battery;
import robots.ui.utils.ImageUtils;

import java.awt.*;
import java.io.File;

/**
 * Виджет батареи.
 * @see Battery
 */
public class BatteryWidget extends PowerSupplyWidget{

    public BatteryWidget(Battery battery) {
        super(battery);
    }

    @Override
    protected Dimension getDimension() {
        Dimension dimension = null;

        if(cellItemState == State.SMALL) {
            dimension = new Dimension(36, 66);
        } else if (cellItemState == State.DEFAULT) {
            dimension = new Dimension(120, 120);
        }

        return dimension;
    }

    @Override
    protected File getImageFile() {
        File file = null;
        if (cellItemState == State.SMALL) {
            file = new File(ImageUtils.IMAGE_PATH + "BS.png");
        } else if (cellItemState == State.DEFAULT) {
            file = new File(ImageUtils.IMAGE_PATH + "BD.png");
        }
        return file;
    }
}
