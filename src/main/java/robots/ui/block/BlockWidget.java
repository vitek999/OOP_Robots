package robots.ui.block;

import robots.model.Orientation;
import robots.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class BlockWidget extends JPanel {

    protected final Orientation orientation;

    public Orientation getOrientation() {
        return orientation;
    }

    public BlockWidget(Orientation orientation) {
        this.orientation = orientation;
        setPreferredSize(getDimensionByOrientation());
    }

    private BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            Dimension dimension = getDimensionByOrientation();
            image = ImageUtils.resizeImage(image, dimension.width, dimension.height);
        } catch (IOException e) {
            e.printStackTrace();    // !!! Для конечного пользователя это не лучшее решение (более дружественное сообщение для пользователя) TODO
        }
        return image;
    }

    protected abstract File getImageFile();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }

    protected Dimension getDimensionByOrientation() {
        return (orientation == Orientation.VERTICAL) ? new Dimension(5, 120) : new Dimension(125, 5);
    }
}
