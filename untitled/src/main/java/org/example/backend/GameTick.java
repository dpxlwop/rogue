package org.example.backend;

import org.example.backend.Entity.Enemy;
import org.example.backend.Entity.Entity;
import org.example.backend.Entity.MovementChecker;
import org.example.backend.Entity.Player;
import org.example.backend.MapGenerator.GameMap;
import org.example.ui.Drawer;
import org.example.ui.KeyHandler;

import java.util.ArrayList;

public class GameTick {
    public static GameTickExitCodes gameTick(Drawer drawer, KeyHandler keyHandler, GameMap map, Player player, ArrayList<Entity> enemies) throws Exception {
        boolean isPlayerCompletedMovement = false;
        while (!isPlayerCompletedMovement) {
            int[] playerMovement = keyHandler.handleInput(player);
            if (MovementChecker.isMovementAllowed(player, map, playerMovement, player)) {
                player.move(playerMovement[0], playerMovement[1]);
                isPlayerCompletedMovement = true;
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

