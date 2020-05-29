package robots.ui.cell;

import robots.model.field.cell_objects.power_supplies.PowerSupply;
import robots.ui.utils.GameWidgetsUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class PowerSupplyWidget extends CellItemWidget {

    private final PowerSupply powerSupply;

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
            image = batteryImageWithChargeText(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private BufferedImage batteryImageWithChargeText(BufferedImage batteryImage) {
        BufferedImage img = new BufferedImage(batteryImage.getWidth(), 120, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.drawImage(batteryImage, 0, 0, null);

        if(cellItemState == State.DEFAULT) {
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(batteryChargeTextColor());
            g.drawString(batteryChargeText(), 40, 100);
        }

        return img;
    }

    private String batteryChargeText() {
        return powerSupply.getCharge() + "/" + powerSupply.getMaxCharge();
    }

    private Color batteryChargeTextColor() {
        return GameWidgetsUtils.chargeTextColor(powerSupply.getCharge(), powerSupply.getMaxCharge());
    }

    protected abstract File getImageFile();
}
