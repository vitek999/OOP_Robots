package robots.ui.cell;

import robots.ui.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CellWidget extends JPanel {

    enum Layer { // !!! Какой уровень доступа хотели?
        TOP,
        BOTTOM
    }

    private Map<Layer, CellItemWidget> items = new HashMap();

    private static final int CELL_SIZE = 120;

    public CellWidget() {
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        setBackground(ImageUtils.BACKGROUND_COLOR);
    }

    public void addItem(CellItemWidget item) { // !!! Какой-то контроль за количеством элементов должен вестись??
                                               // DONE: Добавлен контроль за кол-вом элеменов
        if(items.size() > 2) throw new IllegalArgumentException();
        int index = -1;

        if (items.containsKey(Layer.BOTTOM)) {
            item.setState(CellItemWidget.State.SMALL);
        } else {
            item.setState(CellItemWidget.State.DEFAULT);
        }

        if (items.containsKey(Layer.TOP)) {
            item.setState(CellItemWidget.State.DEFAULT);
            items.get(Layer.TOP).setState(CellItemWidget.State.SMALL);
            index = 0;
        }

        items.put(item.getLayer(), item);
        add(item, index);
        // !!! В Swing добавление элемента приводит к перерисовке?
        // DONE: Убрал лишний repaint.
    }

    public void removeItem(CellItemWidget item) {
        if (items.containsValue(item)) {
            int index = 0;

            if(item.getLayer() == Layer.BOTTOM) {
                if (items.containsKey(Layer.TOP)) {
                    items.get(Layer.TOP).setState(CellItemWidget.State.DEFAULT);
                }
            }

            if(item.getLayer() == Layer.TOP) {
                if(items.containsKey(Layer.BOTTOM)) {
                    index = 1;
                    items.get(Layer.BOTTOM).setState(CellItemWidget.State.DEFAULT);
                }
            }

            remove(index);
            items.remove(item.getLayer());
            repaint();
        }
    }
}
