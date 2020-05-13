package robots.ui.block;

import org.jetbrains.annotations.NotNull;
import robots.Orientation;

import javax.swing.*;
import java.awt.*;

public class BetweenCellsWidget extends JPanel { // !!! До конца предназначение виджета не понял

    private final Orientation orientation;

    public BetweenCellsWidget(@NotNull Orientation orientation) {
        super(new BorderLayout());
        this.orientation = orientation;
        setPreferredSize(getDimensionByOrientation());
        setBackground(Color.BLACK);
    }

    public void addItem(@NotNull BlockWidget blockWidget) { // !!! Плохое название - могу много элементов добавить?
        if(blockWidget.getOrientation() != orientation) throw new IllegalArgumentException();
        add(blockWidget);                                   // !!! Что будет, если ориентация BetweenCellsWidget и BlockWidget не совпадают??
                                                            // DONE: Добавил проверку на несовпадение ориентаций.
    }

    private Dimension getDimensionByOrientation() {
        return (orientation == Orientation.VERTICAL) ? new Dimension(5, 120) : new Dimension(125, 5);
    }
}
