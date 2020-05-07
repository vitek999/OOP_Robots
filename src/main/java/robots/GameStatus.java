package robots;

public enum GameStatus {
    WINNER_FOUND,
    GAME_IS_ON,
    ALL_ROBOTS_OUT,
    ALL_ROBOTS_HAVE_LOW_BATTERIES, // !!! Название сформулировано странно - "низкая батарейка" и почему батарейка?
    GAME_FINISHED_AHEAD_OF_SCHEDULE // !!! Название сформулировано странно - игра не завершилась, она прервалась
}
