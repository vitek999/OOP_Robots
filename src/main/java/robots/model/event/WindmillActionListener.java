package robots.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface WindmillActionListener extends EventListener {

    void windmillChargeIsUpdated(@NotNull WindmillActionEvent event);
}
