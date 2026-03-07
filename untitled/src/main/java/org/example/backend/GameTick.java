package org.example.backend;

import org.example.backend.Entity.*;
import org.example.backend.Interaction.Fight;
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
            MovementCodes code = MovementChecker.isMovementAllowed(player, map, playerMovement, player);
            if (code == MovementCodes.ALLOW) {
                player.move(playerMovement[0], playerMovement[1]);
                isPlayerCompletedMovement = true;
            } else if (code == MovementCodes.FIGHT){
                Entity enemy = Fight.fight(player, enemies, playerMovement);
                if (enemy != null && enemy.isDead()){
                    enemies.remove(enemy);
                }
            }
        }
        for (Entity e : enemies) {
            if (e instanceof Enemy a) {
                a.enemyWalking(map, player);
            }
        }
        //получение roomid для тумана войны
        int[] cords = player.getCordXY();
        System.out.println(map.getEntityRoomID(player));


        drawer.draw(map.getMap(), player, enemies);
        Thread.sleep(32); // ~60 FPS
        return GameTickExitCodes.OK;
    }
}

