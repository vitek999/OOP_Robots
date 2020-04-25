package robots;

import org.jetbrains.annotations.NotNull;
import robots.event.GameActionEvent;
import robots.event.GameActionListener;
import robots.labirints.SmallLabirint;
import robots.ui.FieldWidget;
import robots.ui.WidgetFactory;
import robots.ui.block.WallWidget;
import robots.ui.utils.GameUtils;

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
            game = new robots.Game(new SmallLabirint());

            game.addGameActionListener(new GameController());

            JPanel content = (JPanel) this.getContentPane();
            content.add(new FieldWidget(game.getGameField(), widgetFactory));

            widgetFactory.getWidget(game.activeRobot()).requestFocus();

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
                                GameUtils.colorName(widgetFactory.getWidget(game.winner()).getColor()));
                        break;
                    case GAME_FINISHED_AHEAD_OF_SCHEDULE:
                        JOptionPane.showMessageDialog(GamePanel.this, "Игра завершена досрочно");
                        break;
                    case ALL_ROBOTS_HAVE_LOW_BATTERIES:
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
