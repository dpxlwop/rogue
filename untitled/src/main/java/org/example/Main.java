package org.example;

import org.example.data.DataClass;
import org.example.data.LeaderBoard;
import org.example.data.Score;
import org.example.game.Game;
import org.example.backend.GameTickExitCodes;
import org.example.backend.GameTick;
import org.example.ui.UiMaster;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws Exception {
        LeaderBoard leaderBoard = DataClass.loadLeaderBoard();
        while (true) {
            Game game = saveOrNewGame(new UiMaster(), leaderBoard);
            GameTick gameTick;
            game.setUiMaster(new UiMaster());
            gameTick = new GameTick(game);
            boolean isPlayerDead = false;
            while (!isPlayerDead) {
                GameTickExitCodes exitCode = gameTick.NextTick();
                if (exitCode == GameTickExitCodes.NEXT_LEVEL){
                    if (game.getLevel() == 21){
                        game.getDrawer().drawWinScreen();
                        gameOver(game, leaderBoard);
                        return;
                    } else{
                        game.generateNextLevel();
                        gameTick = new GameTick(game);
                        game.getDrawer().draw(game);
                        DataClass.saveGame(game);
                        saveScoreParams(game, leaderBoard);
                    }
                } else if (exitCode == GameTickExitCodes.GAME_OVER_BY_PLAYER) {
                    game.getDrawer().drawQuitScreen();
                    gameOver(game, leaderBoard);
                    DataClass.saveGame(game);
                    return;
                } else if (exitCode == GameTickExitCodes.GAME_OVER_PLAYER_DIED) {
                    game.getDrawer().drawDeadScreen();
                    isPlayerDead = true;
                    gameOver(game, leaderBoard);
                } else if (exitCode == GameTickExitCodes.GAME_OVER_WIN) {
                    game.getDrawer().drawWinScreen();
                    gameOver(game, leaderBoard);
                    return;
                }
            }
        }
    }

    public static void gameOver(Game game, LeaderBoard leaderBoard) throws Exception {
        DataClass.clearSave();
        saveScoreParams(game, leaderBoard);
        Thread.sleep(3000);
        game.getDrawer().stop();
    }

    public static Game saveOrNewGame(UiMaster ui, LeaderBoard leaderBoard) throws Exception {
        Game game;
        ui.getDrawer().drawWelcomeScreen(leaderBoard.getScoreList(), null);
        String name = ui.getKeyHandler().handleName(leaderBoard.getScoreList(), ui.getDrawer());
        ui.getDrawer().stop();
        if (name.isEmpty() && DataClass.isSaveExist()) {
            game = DataClass.loadGame();
            if (game == null) {
                game = new Game("Player");
            }
        } else {
            game = new Game(name.isEmpty() ? "Player" : name);
        }
        return game;
    }

    public static void saveScoreParams(Game game, LeaderBoard leaderBoard) throws IOException {
        Score score = new Score(game.getPlayer(), game, game.getID(), game.getName());
        leaderBoard.addToScoreList(score);
        DataClass.saveLeaderBoard(leaderBoard);
    }
}
