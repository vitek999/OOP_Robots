package robots.ui.block;

import robots.model.Orientation;
import robots.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Виджет препятствия, расположенного между ячейками.
 */
public abstract class BlockWidget extends JPanel {

    /**
     * Ориентация.
     */
    protected final Orientation orientation;

    /**
     * Получить ориентацию {@link BlockWidget#orientation}.
     * @return ориентация.
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Констрктор.
     * @param orientation ориентация.
     */
    public BlockWidget(Orientation orientation) {
        this.orientation = orientation;
        setPreferredSize(getDimensionByOrientation());
    }

    /**
     * Получить изображение виджета.
     * @return изображене виджета.
     */
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

    /**
     * Получить файл изображения.
     * @return файл изображения.
     */
    protected abstract File getImageFile();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }

    /**
     * Получить размеры виджеты по ориентации.
     * @return размеры виджета.
     */
    protected Dimension getDimensionByOrientation() {
        return (orientation == Orientation.VERTICAL) ? new Dimension(5, 120) : new Dimension(125, 5);
    }
}
