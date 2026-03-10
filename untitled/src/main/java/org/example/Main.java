package org.example;

import org.example.backend.Game;
import org.example.backend.GameTickExitCodes;
import org.example.backend.GameTick;
import org.example.backend.GameTick;


public class Main {
    private Game game;
    private GameTick gameTick;

    public static void main(String[] args) throws Exception {
        while (true) {
            Game game = new Game();
            GameTick gameTick = new GameTick(game);
            boolean isPlayerDead = false;
            while (!isPlayerDead) {
                GameTickExitCodes exitCode = gameTick.NextTick();
                if (exitCode == GameTickExitCodes.NEXT_LEVEL){
                    if (game.getLevel() == 21){
                        game.getDrawer().drawWinScreen();
                        Thread.sleep(5000);
                        game.getDrawer().stop();
                        return;
                    } else{
                        game.generateNextLevel();
                        gameTick = new GameTick(game);
                        game.getDrawer().draw(game);
                    }

                }else if (exitCode == GameTickExitCodes.GAME_OVER_BY_PLAYER) {
                    game.getDrawer().drawQuitScreen();
                    Thread.sleep(5000);
                    game.getDrawer().stop();
                    return;
                } else if (exitCode == GameTickExitCodes.GAME_OVER_PLAYER_DIED) {
                    game.getDrawer().drawDeadScreen();
                    isPlayerDead = true;
                    Thread.sleep(5000);
                } else if (exitCode == GameTickExitCodes.GAME_OVER_WIN) {
                    game.getDrawer().drawWinScreen();
                    Thread.sleep(5000);
                    game.getDrawer().stop();
                    return;
                }
            }
        }
    }
}
