package robots.ui.cell;

import robots.model.field.cell_objects.power_supplies.Battery;
import robots.ui.utils.GameWidgetsUtils;
import robots.ui.cell.CellWidget.Layer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BatteryWidget extends CellItemWidget{

    private final Battery battery;

    public BatteryWidget(Battery battery) {
        this.battery = battery;
        setToolTipText("Заряд: " + battery.getCharge() + "/" + battery.getMaxCharge());
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getBatteryImageFile());
            image = batteryImageWithChargeText(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public Layer getLayer() {
        return CellWidget.Layer.TOP;
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
        return battery.getCharge() + "/" + battery.getMaxCharge();
    }

    private Color batteryChargeTextColor() {
        return GameWidgetsUtils.chargeTextColor(battery.getCharge(), battery.getMaxCharge());
    }

    private File getBatteryImageFile() {
        File file = null;
        if (cellItemState == State.SMALL) {
            file = new File("BS.png");
        } else if (cellItemState == State.DEFAULT) {
            file = new File("BD.png");
        }
        return file;
    }
}
