package robots.ui.cell;

import org.jetbrains.annotations.NotNull;
import robots.Direction;
import robots.Robot;
import robots.ui.utils.ChargeUtils;
import robots.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
        //setFocusable(true);
        addKeyListener(new KeyController());
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getRobotFileByColorAndActive(color, robot.isActive()));
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

    public void setActive(boolean state) {
        setFocusable(state);
        repaint();
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

    private static File getRobotFileByColorAndActive(Color robotColor, boolean active) {
        File file = null;
        if (robotColor == Color.RED)  {
            file = active ? new File("RRBA.png") : new File("RRB.png");
        }
        if (robotColor == Color.BLUE) {
            file = active ? new File("RBBA.png") : new File("RBB.png");
        }
        return file;
    }

    // Внутренний класс-обработчик событий. Придает специфицеское поведение виджету
    private class KeyController implements KeyListener {

        @Override
        public void keyTyped(KeyEvent arg0) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int keyCode = ke.getKeyCode();

            move(keyCode);
            changeBattery(keyCode);

            repaint();
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
        }

        private void changeBattery(@NotNull int keyCode) {
            if(keyCode == KeyEvent.VK_G) {
                robot.changeBattery();
            }
        }

        private void move(@NotNull int keyCode){
            Direction direction = directionByKeyCode(keyCode);
            System.out.println(color + " go to " + direction);
            if(direction != null && robot.isActive()) {
                robot.move(direction);
            }
        }

        private Direction directionByKeyCode(@NotNull int keyCode) {
            Direction direction = null;
            switch (keyCode) {
                case KeyEvent.VK_W:
                    direction = Direction.NORTH;
                    break;
                case KeyEvent.VK_S:
                    direction = Direction.SOUTH;
                    break;
                case KeyEvent.VK_A:
                    direction = Direction.WEST;
                    break;
                case KeyEvent.VK_D:
                    direction = Direction.EAST;
                    break;
            }
            return direction;
        }
    }
}
