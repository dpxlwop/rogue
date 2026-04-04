package org.example;

import org.example.Data.DataClass;
import org.example.Data.LeaderBoard;
import org.example.Data.Score;
import org.example.Game.Game;
import org.example.backend.Entity.Player;
import org.example.backend.GameTickExitCodes;
import org.example.backend.GameTick;
import org.example.ui.UiMaster;

import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws Exception {
        LeaderBoard leaderBoard = DataClass.loadLeaderBoard();
        while (true) {
            Game game;
            GameTick gameTick;
            UiMaster ui = new UiMaster();
            ui.getDrawer().drawWelcomeScreen(leaderBoard.getScoreList());
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
                        saveScoreParams(game, leaderBoard);
                    }
                } else if (exitCode == GameTickExitCodes.GAME_OVER_BY_PLAYER) {
                    DataClass.saveGame(game);
                    saveScoreParams(game, leaderBoard);
                    game.getDrawer().drawQuitScreen();
                    Thread.sleep(3000);
                    game.getDrawer().stop();
                    return;
                } else if (exitCode == GameTickExitCodes.GAME_OVER_PLAYER_DIED) {
                    DataClass.clearSave();
                    saveScoreParams(game, leaderBoard);
                    game.getDrawer().drawDeadScreen();
                    isPlayerDead = true;
                    Thread.sleep(3000);
                } else if (exitCode == GameTickExitCodes.GAME_OVER_WIN) {
                    DataClass.clearSave();
                    saveScoreParams(game, leaderBoard);
                    game.getDrawer().drawWinScreen();
                    Thread.sleep(3000);
                    game.getDrawer().stop();
                    return;
                }
            }
        }
    }

    public static void saveScoreParams(Game game, LeaderBoard leaderBoard) throws IOException {
        Score score = new Score(game.getPlayer(), game, game.getID());
        leaderBoard.addToScoreList(score);
        DataClass.saveLeaderBoard(leaderBoard);
    }
}
