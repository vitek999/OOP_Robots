package robots.ui.block;

import robots.model.Orientation;
import robots.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WallWidget extends BlockWidget {

    public WallWidget(Orientation orientation) {
        super(orientation);
    }

    @Override
    protected File getImageFile() {
        return (orientation == Orientation.VERTICAL) ? new File("WV.png") : new File("WH.png");
    }
}
