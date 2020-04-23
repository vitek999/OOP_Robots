package robots.ui;

import org.jetbrains.annotations.NotNull;
import robots.Cell;
import robots.Field;
import robots.Point;
import robots.ui.cell.CellWidget;

import javax.swing.*;
import java.awt.*;

public class FieldWidget extends JPanel {

    private final Field field;
    private final WidgetFactory widgetFactory;

    public FieldWidget(@NotNull Field field, @NotNull  WidgetFactory widgetFactory) {
        this.field = field;
        this.widgetFactory = widgetFactory;
        setLayout(new GridLayout(field.getHeight(), field.getWidth()));
        fillField();
    }

    private void fillField() {
        for (int i = 0; i < field.getHeight(); ++i) {
            for (int j = 0; j < field.getWidth(); ++j) {
                robots.Point point = new Point(i, j);
                Cell cell = field.getCell(point);
                CellWidget widget = widgetFactory.create(cell);
                add(widget);
            }
        }
    }
}
