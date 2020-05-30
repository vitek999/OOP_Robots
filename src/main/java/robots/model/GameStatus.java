package robots.model;

/**
 * Статус игры.
 */
public enum GameStatus {

    /**
     * Найден победитель.
     */
    WINNER_FOUND,

    /**
     * Игра идёт.
     */
    GAME_IS_ON,

    /**
     * Все роботы телепоортированы.
     */
    ALL_ROBOTS_OUT,

    /**
     * Все роботы имеют низкий заряд.
     */
    ALL_ROBOTS_HAVE_LOW_CHARGE, // !!! Название сформулировано странно - "низкая батарейка" и почему батарейка?
                                // DONE: переименовал ALL_ROBOTS_HAVE_LOW_BATTERIES -> ALL_ROBOTS_HAVE_LOW_CHARGE
    /**
     * Игра прервана.
     */
    GAME_ABORTED // !!! Название сформулировано странно - игра не завершилась, она прервалась
                 // DONE: переименовал GAME_FINISHED_AHEAD_OF_SCHEDULE -> GAME_ABORTED
}
