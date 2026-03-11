package org.example.backend;

public enum GameTickExitCodes {
    //коды выхода для GameTick
    OK,
    GAME_OVER_PLAYER_DIED,
    GAME_OVER_WIN,
    GAME_OVER_BY_PLAYER,
    NEXT_LEVEL,         //+playerMovement
    //коды выхода для playerMovement
    CONTINUE;

}
