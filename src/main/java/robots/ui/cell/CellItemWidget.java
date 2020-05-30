package robots.ui.cell;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import robots.ui.cell.CellWidget.Layer;

/**
 * Виджет объекта для виджета ячейки.
 */
public abstract class CellItemWidget extends JPanel {

    /**
     * Конструтор.
     */
    public CellItemWidget() {
        setState(State.DEFAULT);
        setOpaque(false);
    }

    /**
     * Состояние виджета.
     */
    public enum State { // !!! Какой уровень доступа хотели?
                        // DONE: Сделал енам общедоступным, чтобы можно было использовать общедоступный getState
        /**
         * Обычный.
         */
        DEFAULT,

        /**
         * Маленький.
         */
        SMALL
    }

    /**
     * Состояние виджета.
     */
    protected State cellItemState = State.DEFAULT;

    /**
     * Установить состояние виджета {@link CellItemWidget#cellItemState}
     * @param state состояние виджета.
     */
    void setState(State state) { // !!! Какой уровень доступа хотели?
                                 // DONE: Уровень доступа отсаётся пакетным, так как изменения состояния может происходить только внутри пакета
        cellItemState = state;
        setPreferredSize(getDimension());
        repaint();
        revalidate();
    }

    /**
     * Получить состояние виджета {@link CellItemWidget#cellItemState}.
     * @return состояние виджета.
     */
    public State getState() {
        return cellItemState;
    }

    /**
     * Получить изображение виджета.
     * @return изображение виджета.
     */
    protected abstract BufferedImage getImage();

    /**
     * Получить слой на которм располагается виджет.
     * @return слой на котором располагается виджет.
     */
    public abstract Layer getLayer(); // !!! Какой уровень доступа хотели?
                                      // DONE: Заменил package-private на public.

    /**
     * Получить размеры виджета.
     * @return размеры виджета.
     */
    protected abstract Dimension getDimension();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }
}
