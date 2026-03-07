package org.example.backend;

import org.example.backend.Entity.*;
import org.example.backend.Interaction.FightEntityAgressor;
import org.example.backend.Interaction.FightPlayerAgressor;
import org.example.backend.Interaction.MovementChecker;
import org.example.backend.MapGenerator.GameMap;
import org.example.ui.Drawer;
import org.example.ui.KeyHandler;
import org.example.backend.Interaction.MovementCodes;

import java.util.ArrayList;

public class GameTick {
    public static GameTickExitCodes gameTick(Drawer drawer, KeyHandler keyHandler, GameMap map, Player player, ArrayList<Entity> enemies) throws Exception {
        boolean isPlayerCompletedMovement = false;
        while (!isPlayerCompletedMovement) {

            int[] playerMovement = keyHandler.handleInput(player);
            if (playerMovement[0] == -999)
                return GameTickExitCodes.GAME_OVER_BY_PLAYER;

            MovementCodes code = MovementChecker.isMovementAllowed(player, map, playerMovement, player);

            if (code == MovementCodes.ALLOW) {
                player.move(playerMovement[0], playerMovement[1]);
                isPlayerCompletedMovement = true;

            } else if (code == MovementCodes.FIGHT){
                Entity enemy = FightPlayerAgressor.playerAttacs(player, enemies, playerMovement);
                if (enemy != null){
                    if (enemy.isDead()){
                    enemies.remove(enemy);
                    }
                    boolean isPlayerBeenAttacked = FightEntityAgressor.EntityAttacs(player,enemy);
                    if (isPlayerBeenAttacked)
                        if (player.isDead())
                            return GameTickExitCodes.GAME_OVER_PLAYER_DIED;
                    if (enemies.isEmpty())
                        return GameTickExitCodes.GAME_OVER_WIN;

                }
            }
        }
        for (Entity e : enemies) {
            if (e instanceof Enemy a) {
                a.enemyWalking(map, player);
            }
        }
        //получение roomid для тумана войны
        int room_id = map.getEntityRoomID(player);
        System.out.println(room_id);


        drawer.draw(map, player, enemies);
        Thread.sleep(32); // ~60 FPS
        return GameTickExitCodes.OK;
    }
}

