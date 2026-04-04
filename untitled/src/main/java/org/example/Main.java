package org.example;

import org.example.Data.DataClass;
import org.example.Game.Game;
import org.example.backend.Entity.Player;
import org.example.backend.GameTickExitCodes;
import org.example.backend.GameTick;
import org.example.ui.UiMaster;


public class Main {

    public static void main(String[] args) throws Exception {
        while (true) {
            Game game;
            GameTick gameTick;
            UiMaster ui = new UiMaster();
            ui.getDrawer().drawWelcomeScreen();
            int[] command = ui.getKeyHandler().handleInput();
            ui.getDrawer().stop();
            if (command[0] == 200 && command[1] == 1 && DataClass.isSaveExist()) {
                game = DataClass.loadGame();
                System.out.println(DataClass.loadGame());
                if (game == null) {
                    game = new Game();
                }
            } else {
                game = new Game();
            }
            game.setUiMaster(new UiMaster());
            System.out.println("LEVEL: " + game.getLevel());
            gameTick = new GameTick(game);
            boolean isPlayerDead = false;
            while (!isPlayerDead) {
                GameTickExitCodes exitCode = gameTick.NextTick();
                if (exitCode == GameTickExitCodes.NEXT_LEVEL){
                    if (game.getLevel() == 21){
                        DataClass.clearSave();
                        game.getDrawer().drawWinScreen();
                        Thread.sleep(3000);
                        game.getDrawer().stop();
                        return;
                    } else{
                        game.generateNextLevel();
                        gameTick = new GameTick(game);
                        game.getDrawer().draw(game);
                        DataClass.saveGame(game);
                    }
                } else if (exitCode == GameTickExitCodes.GAME_OVER_BY_PLAYER) {
                    DataClass.saveGame(game);
                    game.getDrawer().drawQuitScreen();
                    Thread.sleep(3000);
                    game.getDrawer().stop();
                    return;
                } else if (exitCode == GameTickExitCodes.GAME_OVER_PLAYER_DIED) {
                    DataClass.clearSave();
                    game.getDrawer().drawDeadScreen();
                    isPlayerDead = true;
                    Thread.sleep(3000);
                } else if (exitCode == GameTickExitCodes.GAME_OVER_WIN) {
                    DataClass.clearSave();
                    game.getDrawer().drawWinScreen();
                    Thread.sleep(3000);
                    game.getDrawer().stop();
                    return;
                }
            }
        }
    }
}
