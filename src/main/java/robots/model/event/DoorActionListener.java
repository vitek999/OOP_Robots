package robots.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface DoorActionListener extends EventListener {
    void doorIsOpenChanged(@NotNull DoorActionEvent doorActionEvent);
}
