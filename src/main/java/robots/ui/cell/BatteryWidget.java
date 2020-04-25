package robots.ui.cell;

import robots.Battery;
import robots.ui.utils.GameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BatteryWidget extends CellItemWidget{

    private final Battery battery;

    public BatteryWidget(Battery battery) {
        this.battery = battery;
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
    CellWidget.Layer getLayer() {
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
        return battery.charge() + "/" + battery.maxCharge();
    }

    private Color batteryChargeTextColor() {
        return GameUtils.chargeTextColor(battery.charge(), battery.maxCharge());
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
