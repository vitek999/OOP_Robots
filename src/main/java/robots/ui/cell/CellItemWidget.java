package robots.ui.cell;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class CellItemWidget extends JPanel {

    public CellItemWidget() {
        setState(State.DEFAULT);
        setOpaque(false);
    }

    enum State { // !!! Какой уровень доступа хотели?
        DEFAULT,
        SMALL
    }

    protected State cellItemState = State.DEFAULT;

    void setState(State state) { // !!! Какой уровень доступа хотели?
        cellItemState = state;
        setPreferredSize(getDimension());
        repaint();
        revalidate();
    }

    protected abstract BufferedImage getImage();

    abstract CellWidget.Layer getLayer(); // !!! Какой уровень доступа хотели?

    protected abstract Dimension getDimension();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }
}
