package ru.poas.robots;

import org.jetbrains.annotations.NotNull;

public class Game {

    private GameStatus gameStatus;
    private Robot activeRobot;

    public void start() {
        // TODO: implement
    }

    public void finish() {
        // TODO: implement
    }

    public GameStatus gameStatus() {
        return gameStatus;
    }

    public Robot winner() {
        throw new UnsupportedOperationException();
    }

    public Robot activeRobot() {
        return activeRobot;
    }

    private void passMoveNexRobot() {

    }

    private void determineOutcomeGame() {

    }

    private void buildField() {

    }

    private void setWinner(@NotNull Robot robot) {

    }

    private void setActiveRobot(@NotNull Robot robot) {

    }

}
