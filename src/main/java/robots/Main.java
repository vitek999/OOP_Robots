package robots;

import org.jetbrains.annotations.NotNull;
import robots.model.event.GameActionEvent;
import robots.model.event.GameActionListener;
import robots.model.Game;
import robots.model.GameStatus;
import robots.model.field.cell_objects.Robot;
import robots.model.labirints.SmallLabirint;
import robots.ui.FieldWidget;
import robots.ui.WidgetFactory;
import robots.ui.cell.RobotWidget;
import robots.ui.utils.GameWidgetsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame {

        private Game game;
        private WidgetFactory widgetFactory;

        public GamePanel() throws HeadlessException {
            setVisible(true);
            startGame();
            setResizable(false);

            JMenuBar menuBar = new JMenuBar();
            menuBar.add(createGameMenu());
            setJMenuBar(menuBar);

            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private JMenu createGameMenu() {
            JMenu gameMenu = new JMenu("Игра");
            JMenuItem newGameMenuItem = new JMenuItem(new NewGameAction());
            JMenuItem exitMenuItem = new JMenuItem(new ExitAction());
            gameMenu.add(newGameMenuItem);
            gameMenu.add(exitMenuItem);
            return gameMenu;
        }

        private void startGame() {
            widgetFactory = new WidgetFactory();
            game = new Game(new SmallLabirint());

            game.addGameActionListener(new GameController());

            JPanel content = (JPanel) this.getContentPane();
            content.removeAll();
            content.add(new FieldWidget(game.getGameField(), widgetFactory));
            widgetFactory.getWidget(game.getActiveRobot()).requestFocus();

            pack();
        }

        private class NewGameAction extends AbstractAction {

            public NewGameAction() {
                putValue(NAME, "Новая");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(GamePanel.this,
                        "Начать новую игру?", "Новая игра",JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) startGame();
            }
        }

        private static class ExitAction extends AbstractAction {

            public ExitAction() {
                putValue(NAME, "Выход");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
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
                if(status != GameStatus.GAME_IS_ON) {
                    String message = "";
                    switch (status) {
                        case WINNER_FOUND:
                            message = "Выйграл робот: " + GameWidgetsUtils.colorName(
                                    ((RobotWidget) widgetFactory.getWidget(game.getWinner())).getColor()
                            );
                            break;
                        case GAME_ABORTED:
                            message= "Игра завершена досрочно";
                            break;
                        case ALL_ROBOTS_HAVE_LOW_CHARGE:
                            message = "Все роботы имеют нулевой заряд";
                            break;
                        case ALL_ROBOTS_OUT:
                            message = "Все роботы вышли";
                            break;
                    }
                    String[] options = {"ok"};
                    int value = JOptionPane.showOptionDialog(GamePanel.this, message, "Игра окончена", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if(value == 0 || value == 1) {
                        startGame();
                        GamePanel.this.repaint();
                    }
                }
            }
        }
    }
}
