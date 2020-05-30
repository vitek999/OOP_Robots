package robots.ui.cell;

import robots.model.Direction;
import robots.model.field.cell_objects.Robot;
import robots.model.field.cell_objects.power_supplies.Battery;
import robots.ui.utils.GameWidgetsUtils;
import robots.ui.utils.ImageUtils;
import robots.ui.cell.CellWidget.Layer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Виджет робота.
 * @see Robot
 */
public class RobotWidget extends CellItemWidget {

    /**
     * Робот.
     */
    private final Robot robot;

    /**
     * Цвет.
     */
    private final Color color;

    /**
     * Конструтор.
     * @param robot робот.
     * @param color цвет.
     */
    public RobotWidget(Robot robot, Color color) {
        super();
        this.robot = robot;
        this.color = color;
        setFocusable(true);
        addKeyListener(new KeyController());
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            image = ImageUtils.resizeImage(image, 60, 96);
            image = robotImageWithChargeText(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public Layer getLayer() {
        return CellWidget.Layer.BOTTOM;
    }

    /**
     * Сделать вижет актиынм
     * @param state состояние активности.
     */
    public void setActive(boolean state) {
        setFocusable(state);
        requestFocus();
        repaint();
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60, 120);
    }

    /**
     * Получить цвет робота {@link RobotWidget#color}.
     * @return цвет робота.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Получить изобаржение с текстом заряда.
     * @param robotImage изображение робота.
     * @return изобаржение с текстом заряда.
     */
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

    /**
     * Получить текст заряда робота.
     * @return текст заряда робота.
     */
    private String robotChargeText() {
        return robot.getCharge() + "/" + robot.getMaxCharge();
    }

    /**
     * Получить цвет текста заряда.
     * @return цвет текста заряда.
     */
    private Color robotChargeTextColor() {
        return GameWidgetsUtils.chargeTextColor(robot.getCharge(), robot.getMaxCharge());
    }

    /**
     * Получить файл изображения робота.
     * @return файл изображения робота.
     */
    private File getImageFile() {
        File file = null;
        if(robot.getPowerSupply() instanceof Battery) {
            if (color == Color.RED) {
                file = robot.isActive() ? new File("RRBA.png") : new File("RRB.png");
            }
            if (color == Color.BLUE) {
                file = robot.isActive() ? new File("RBBA.png") : new File("RBB.png");
            }
        } else {
            if (color == Color.RED) {
                file = robot.isActive() ? new File("RRAA.png") : new File("RRA.png");
            }
            if (color == Color.BLUE) {
                file = robot.isActive() ? new File("RBAA.png") : new File("RBA.png");
            }
        }
        return file;
    }

    /**
     * Внутренний класс-обработчик событий. Придает специфицеское поведение виджету.
     */
    private class KeyController implements KeyListener {

        @Override
        public void keyTyped(KeyEvent arg0) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int keyCode = ke.getKeyCode();

            moveAction(keyCode);
            changeBatteryAction(keyCode);
            skipStepAction(keyCode);
            performAction(keyCode);
            chargeBatteryAction(keyCode);

            repaint();
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
        }

        private void chargeBatteryAction(int keyCode) {
            if(keyCode == KeyEvent.VK_C) {
                robot.chargePowerSupply();
            }
        }

        private void performAction(int keyCode) {
            if(keyCode == KeyEvent.VK_O) {
                robot.performAction();
                repaint();
            }
        }

        private void changeBatteryAction(int keyCode) {
            if(keyCode == KeyEvent.VK_G) {
                robot.changePowerSupply();
            }
        }

        private void skipStepAction(int keyCode) {
            if(keyCode == KeyEvent.VK_F) {
                robot.skipStep();
            }
        }

        private void moveAction(int keyCode){
            Direction direction = directionByKeyCode(keyCode);
            System.out.println(color + " go to " + direction);
            if(direction != null && robot.isActive()) {
                robot.move(direction);
            }
        }

        private Direction directionByKeyCode(int keyCode) {
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
