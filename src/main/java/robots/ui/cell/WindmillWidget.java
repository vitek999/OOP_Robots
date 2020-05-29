package robots.ui.cell;

import org.jetbrains.annotations.NotNull;
import robots.model.event.WindmillActionEvent;
import robots.model.event.WindmillActionListener;
import robots.model.field.cell_objects.power_supplies.Windmill;

import java.awt.*;
import java.io.File;

public class WindmillWidget extends PowerSupplyWidget implements WindmillActionListener {

    public WindmillWidget(Windmill windmill) {
        super(windmill);
        windmill.addWindmillActionListener(this);
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(120, 120);
    }

    @Override
    protected File getImageFile() {
        return new File("W.png");
    }

    @Override
    public void windmillChargeIsUpdated(@NotNull WindmillActionEvent event) {
        if(event.getWindmill() == powerSupply) repaint();
    }
}
