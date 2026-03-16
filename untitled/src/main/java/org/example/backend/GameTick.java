package org.example.backend;

import org.example.Game.Game;
import org.example.backend.Entity.Enemy;
import org.example.backend.Entity.Entity;
import org.example.backend.Interaction.*;
import org.example.backend.Item.Item;
import org.example.backend.MapGenerator.Room;
import org.example.ui.KeyHandler;

public class GameTick {
    private KeyHandler keyHandler;
    private Game game;

    public GameTick(Game game) {
        this.keyHandler = game.getKeyHandler();
        this.game = game;
    }

    public GameTickExitCodes NextTick() throws Exception {
        boolean isPlayerCompletedMovement = false;
        int[] playerCommand = this.keyHandler.handleInput(game.getPlayer());



        if (playerCommand[0] == -999)
            return GameTickExitCodes.GAME_OVER_BY_PLAYER;

        else if (playerCommand[0] == 100) {
            game.getPlayer().useItem(playerCommand[1] - 1);
            return drawAndExit();
        }

        if(game.getPlayer().isStunned()){
            game.getPlayer().swapIsStunned();
            return drawAndExit();
        }

        GameTickExitCodes code = playerMovement(playerCommand);

        if (!isGameGoOn(code))
            return code;
        code = moveEnemiesAndAttackPlayer();

        if (!isGameGoOn(code))
            return code;
        return drawAndExit();
    }

    private boolean isGameGoOn(GameTickExitCodes code){
        return !(code == GameTickExitCodes.GAME_OVER_BY_PLAYER ||
                code == GameTickExitCodes.GAME_OVER_WIN ||
                code == GameTickExitCodes.GAME_OVER_PLAYER_DIED ||
                code == GameTickExitCodes.NEXT_LEVEL);
    }

    private GameTickExitCodes drawAndExit() throws Exception{
        this.game.getDrawer().draw(game);
        return GameTickExitCodes.OK;
    }

    private GameTickExitCodes moveEnemiesAndAttackPlayer(){
        for (Entity entity : game.getEnemiesOnLevel()) {
            if (entity instanceof Enemy a) {
                EnemyWalkingExitObj exitObj = a.enemyWalking(game.getMap(), game.getPlayer());
                if (exitObj.getExitCode() == MovementCodes.FIGHT) {
                    boolean isPlayerBeenAttacked = FightEntityAgressor.EntityAttacs(game.getPlayer(), entity);
                    System.out.println(String.format("EntityAttacs %d, player new hp: %d", entity.getHealth(), game.getPlayer().getHealth()));
                    if (isPlayerBeenAttacked)
                        if (game.getPlayer().isDead())
                            return GameTickExitCodes.GAME_OVER_PLAYER_DIED;
                }
            }
        }
        return GameTickExitCodes.OK;
    }

    private GameTickExitCodes playerMovement(int[] playerMovement) throws Exception{
        MovementCodes code = MovementChecker.isMovementAllowed(
                game.getPlayer(),
                game.getMap(),
                playerMovement,
                game.getPlayer());

        if (code == MovementCodes.ALLOW) {
            game.getPlayer().move(playerMovement[0], playerMovement[1]);
            return GameTickExitCodes.CONTINUE;

        } else if (code == MovementCodes.NEXT_LEVEL){
            return GameTickExitCodes.NEXT_LEVEL;

        } else if (code == MovementCodes.PICK_UP_ITEM) {
            int newX = game.getPlayer().getCordXY()[0] + playerMovement[0];
            int newY = game.getPlayer().getCordXY()[1] + playerMovement[1];
            Item item = game.getItemByCords(newX, newY);
            if (item != null) {
                game.getPlayer().pickUpItem(item);
                System.out.println(item.getClass().getSimpleName());
                game.removeItemFromGame(item);
            }
            game.getPlayer().move(playerMovement[0], playerMovement[1]);
            return GameTickExitCodes.CONTINUE;

        } else if (code == MovementCodes.FIGHT) {
            Entity enemy = FightPlayerAgressor.playerAttacs(game.getPlayer(), game.getEnemiesOnLevel(), playerMovement);
            if (enemy != null) {
                if (enemy.isDead()) {
                    int roomId = game.getMap().getEntityRoomID(enemy);
                    int[] cords = enemy.getCordXY();
                    game.addItemToMap(game.getMap().summonTreasure(enemy, cords[0], cords[1]));
                    game.removeEntityFromGame(enemy);
                }
                boolean isPlayerBeenAttacked = FightEntityAgressor.EntityAttacs(game.getPlayer(), enemy);
                if (isPlayerBeenAttacked)
                    if (game.getPlayer().isDead())
                        return GameTickExitCodes.GAME_OVER_PLAYER_DIED;
                return drawAndExit();
            }
        }
        return GameTickExitCodes.CONTINUE;
    }
}

