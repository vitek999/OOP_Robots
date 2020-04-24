package robots;

import robots.labirints.SmallLabirint;
import robots.ui.FieldWidget;
import robots.ui.WidgetFactory;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        SwingUtilities.invokeLater(Wind::new);
    }

    static class Wind extends JFrame {

        public Wind() throws HeadlessException {
            setVisible(true);

            SmallLabirint smallLabirint = new SmallLabirint();
            Field field = smallLabirint.buildField();
            WidgetFactory widgetFactory = new WidgetFactory();

            JPanel content = (JPanel) this.getContentPane();
            content.add(new FieldWidget(field, widgetFactory));

            //Game
//            Game game = new Game(new SmallLabirint());
//            game.activeRobot().move(Direction.EAST);

            pack();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            Robot robot = field.getRobotsOnField().get(0);
            robot.setActive(true);
            //robot.move(Direction.EAST);
        }
    }
}
