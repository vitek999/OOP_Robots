package robots.ui.cell;

import robots.model.field.cell_objects.power_supplies.PowerSupply;
import robots.ui.utils.GameWidgetsUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Виджет источника питания.
 * @see PowerSupply
 */
public abstract class PowerSupplyWidget extends CellItemWidget {

    /**
     * Источник питания.
     */
    protected final PowerSupply powerSupply;

    /**
     * Конструтор.
     * @param powerSupply источник питания.
     */
    public PowerSupplyWidget(PowerSupply powerSupply) {
        this.powerSupply = powerSupply;
        setToolTipText("Заряд: " + powerSupply.getCharge() + "/" + powerSupply.getMaxCharge());
    }

    @Override
    public CellWidget.Layer getLayer() {
        return CellWidget.Layer.TOP;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            image = powerSupplyImageWithChargeText(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Получить изображение источника питания с наложенным текстом заряда.
     * @param powerSupplyImage изображение источник питания.
     * @return изображение источника питания с наложенным текстом заряда.
     */
    private BufferedImage powerSupplyImageWithChargeText(BufferedImage powerSupplyImage) {
        BufferedImage img = new BufferedImage(powerSupplyImage.getWidth(), 120, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.drawImage(powerSupplyImage, 0, 0, null);

        if(cellItemState == State.DEFAULT) {
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(powerSupplyChargeTextColor());
            g.drawString(powerSupplyChargeText(), 40, 100);
        }

        return img;
    }

    /**
     * Получить текст заряда источника питания.
     * @return текст заряда источника питания.
     */
    private String powerSupplyChargeText() {
        return powerSupply.getCharge() + "/" + powerSupply.getMaxCharge();
    }

    /**
     * Получить цвет текста заряда источника питания.
     * @return цвет текста заряда источника питания.
     */
    private Color powerSupplyChargeTextColor() {
        return GameWidgetsUtils.chargeTextColor(powerSupply.getCharge(), powerSupply.getMaxCharge());
    }

    /**
     * Получить файл изображения.
     * @return файл изображения.
     */
    protected abstract File getImageFile();
}
