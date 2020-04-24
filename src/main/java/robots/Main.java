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

            pack();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }
}
