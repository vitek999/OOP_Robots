package robots.ui.cell;

import robots.ui.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class CellItemWidget extends JPanel {

    public CellItemWidget() {
        setState(State.DEFAULT);
        setBackground(ImageUtils.BACKGROUND_COLOR);
    }

    enum State {
        DEFAULT,
        SMALL
    }

    protected State cellItemState = State.DEFAULT;

    void setState(State state) {
        cellItemState = state;
        setPreferredSize(getDimension());
        repaint();
        revalidate();
    }

    protected abstract BufferedImage getImage();

    abstract CellWidget.Layer getLayer();

    protected abstract Dimension getDimension();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }
}
