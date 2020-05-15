package robots.ui.cell;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import robots.ui.cell.CellWidget.Layer;

public abstract class CellItemWidget extends JPanel {

    public CellItemWidget() {
        setState(State.DEFAULT);
        setOpaque(false);
    }

    public enum State { // !!! Какой уровень доступа хотели?
                        // DONE: Сделал енам общедоступным, чтобы можно было использовать общедоступный getState
        DEFAULT,
        SMALL
    }

    protected State cellItemState = State.DEFAULT;

    void setState(State state) { // !!! Какой уровень доступа хотели?
                                 // DONE: Уровень доступа отсаётся пакетным, так как изменения состояния может происходить только внутри пакета
        cellItemState = state;
        setPreferredSize(getDimension());
        repaint();
        revalidate();
    }

    public State getState() {
        return cellItemState;
    }

    protected abstract BufferedImage getImage();

    public abstract Layer getLayer(); // !!! Какой уровень доступа хотели?
                                      // DONE: Заменил package-private на public.

    protected abstract Dimension getDimension();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }
}
