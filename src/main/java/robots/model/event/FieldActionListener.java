package robots.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface FieldActionListener extends EventListener {
    void robotIsTeleported(@NotNull FieldActionEvent event);
}
