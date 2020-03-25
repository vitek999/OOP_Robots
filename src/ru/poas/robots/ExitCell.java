package ru.poas.robots;

import java.util.ArrayList;
import java.util.List;

public class ExitCell extends Cell {

    private List<Robot> teleportedRobots = new ArrayList<>();

    public List<Robot> getTeleportedRobots() {
        return new ArrayList<>(teleportedRobots);
    }

    private void teleportRobot() {
        // TODO: implement
    }
}
