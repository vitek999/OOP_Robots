package robots;

import org.jetbrains.annotations.NotNull;
import robots.model.event.GameActionEvent;
import robots.model.event.GameActionListener;
import robots.model.Game;
import robots.model.GameStatus;
import robots.model.labirints.SmallLabirint;
import robots.ui.FieldWidget;
import robots.ui.WidgetFactory;
import robots.ui.utils.GameWidgetsUtils;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame {

        private Game game;
        private WidgetFactory widgetFactory;

        public GamePanel() throws HeadlessException {
            setVisible(true);

            widgetFactory = new WidgetFactory();
            game = new Game(new SmallLabirint());

            game.addGameActionListener(new GameController());

            JPanel content = (JPanel) this.getContentPane();
            content.add(new FieldWidget(game.getGameField(), widgetFactory));

            widgetFactory.getWidget(game.getActiveRobot()).requestFocus();

            pack();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private final class GameController implements GameActionListener {

            @Override
            public void robotIsMoved(@NotNull GameActionEvent event) {

            }

            @Override
            public void robotIsSkipStep(@NotNull GameActionEvent event) {

            }

            @Override
            public void robotIsTeleported(@NotNull GameActionEvent event) {

            }

            @Override
            public void gameStatusChanged(@NotNull GameActionEvent event) {
                GameStatus status = event.getStatus();
                switch (status) {
                    case WINNER_FOUND:
                        JOptionPane.showMessageDialog(GamePanel.this, "Выйграл робот: " +
                                GameWidgetsUtils.colorName(widgetFactory.getWidget(game.getWinner()).getColor()));
                        break;
                    case GAME_ABORTED:
                        JOptionPane.showMessageDialog(GamePanel.this, "Игра завершена досрочно");
                        break;
                    case ALL_ROBOTS_HAVE_LOW_CHARGE:
                        JOptionPane.showMessageDialog(GamePanel.this, "Все роботы имеют нулевой заряд");
                        break;
                    case ALL_ROBOTS_OUT:
                        JOptionPane.showMessageDialog(GamePanel.this, "Все роботы вышли");
                        break;
                }
            }
        }
    }
}
