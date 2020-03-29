package robots.event;

import java.util.EventListener;

public interface FieldActionListener extends EventListener {
    void robotIsTeleported(FieldActionEvent event);
}
