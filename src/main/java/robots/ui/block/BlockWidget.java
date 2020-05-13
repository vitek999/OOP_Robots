package robots.ui.block;

import robots.Orientation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BlockWidget extends JPanel {

    protected final Orientation orientation;

    public Orientation getOrientation() {
        return orientation;
    }

    public BlockWidget(Orientation orientation) {
        this.orientation = orientation;
        setPreferredSize(getDimensionByOrientation());
    }

    protected abstract BufferedImage getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }

    protected Dimension getDimensionByOrientation() {
        return (orientation == Orientation.VERTICAL) ? new Dimension(5, 120) : new Dimension(125, 5);
    }
}
