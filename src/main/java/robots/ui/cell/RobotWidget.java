package robots.ui.cell;

import robots.Robot;
import robots.ui.utils.ChargeUtils;
import robots.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RobotWidget extends CellItemWidget {

    private final Robot robot;
    private final Color color;

    public RobotWidget(Robot robot, Color color) {
        super();
        this.robot = robot;
        this.color = color;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getRobotFileByColor(color));
            image = ImageUtils.resizeImage(image, 60, 96);
            image = robotImageWithChargeText(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    CellWidget.Layer getLayer() {
        return CellWidget.Layer.BOTTOM;
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60, 120);
    }

    private BufferedImage robotImageWithChargeText(BufferedImage robotImage) {
        BufferedImage img = new BufferedImage(robotImage.getWidth(), 120, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.drawImage(robotImage, 0, 0, null);

        if(cellItemState == State.DEFAULT) {
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(robotChargeTextColor());
            g.drawString(robotChargeText(), 5, 112);
        }

        return img;
    }

    private String robotChargeText() {
        return robot.getCharge() + "/" + robot.getMaxCharge();
    }

    private Color robotChargeTextColor() {
        return ChargeUtils.chargeTextColor(robot.getCharge(), robot.getMaxCharge());
    }

    private static File getRobotFileByColor(Color robotColor) {
        File file = null;
        if (robotColor == Color.RED) file = new File("RRB.png");
        if (robotColor == Color.BLUE) file = new File("RBB.png");
        return file;
    }
}
