package robots;

import org.jetbrains.annotations.NotNull;
import robots.event.GameActionEvent;
import robots.event.GameActionListener;
import robots.labirints.SmallLabirint;
import robots.ui.FieldWidget;
import robots.ui.WidgetFactory;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame {

        private Game game;

        public GamePanel() throws HeadlessException {
            setVisible(true);

            WidgetFactory widgetFactory = new WidgetFactory();

            game = new robots.Game(new SmallLabirint());
            game.addGameActionListener(new GameController());

            JPanel content = (JPanel) this.getContentPane();
            content.add(new FieldWidget(game.getGameField(), widgetFactory));

            widgetFactory.getWidget(game.activeRobot()).requestFocus();

            pack();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        class GameController implements GameActionListener {

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
                        JOptionPane.showMessageDialog(null, "Выйграл робот: " + game.winner());
                        break;
                    case GAME_FINISHED_AHEAD_OF_SCHEDULE:
                        JOptionPane.showMessageDialog(null, "Игра завершена досрочно");
                        break;
                    case ALL_ROBOTS_HAVE_LOW_BATTERIES:
                        JOptionPane.showMessageDialog(null, "Все роботы имеют нулевой заряд");
                        break;
                    case ALL_ROBOTS_OUT:
                        JOptionPane.showMessageDialog(null, "Все роботы вышли");
                        break;
                }
            }
        }
    }
}
