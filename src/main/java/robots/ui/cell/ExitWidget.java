package robots.ui.cell;

import robots.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Виджет ячейки выхода.
 * @see robots.model.field.cells.ExitCell
 */
public class ExitWidget extends CellWidget{
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            BufferedImage image = ImageIO.read(new File(ImageUtils.IMAGE_PATH + "exit.png"));
            image = ImageUtils.resizeImage(image, 120, 120);
            g.drawImage(image, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
