package robots.ui.block;

import robots.model.Orientation;
import robots.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Виджет стены.
 * @see robots.model.field.between_cells_objects.WallSegment
 */
public class WallWidget extends BlockWidget {

    /**
     * Конструктор.
     * @param orientation ориентация.
     */
    public WallWidget(Orientation orientation) {
        super(orientation);
    }

    @Override
    protected File getImageFile() {
        return (orientation == Orientation.VERTICAL) ? new File("WV.png") : new File("WH.png");
    }
}
