package robots;

public enum GameStatus {
    WINNER_FOUND,
    GAME_IS_ON,
    ALL_ROBOTS_OUT,
    ALL_ROBOTS_HAVE_LOW_CHARGE, // !!! Название сформулировано странно - "низкая батарейка" и почему батарейка?
                                // DONE: переименовал ALL_ROBOTS_HAVE_LOW_BATTERIES -> ALL_ROBOTS_HAVE_LOW_CHARGE
    GAME_ABORTED // !!! Название сформулировано странно - игра не завершилась, она прервалась
                 // DONE: переименовал GAME_FINISHED_AHEAD_OF_SCHEDULE -> GAME_ABORTED
}
