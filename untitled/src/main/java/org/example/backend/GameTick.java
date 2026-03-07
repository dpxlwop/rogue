package org.example.backend;

import org.example.backend.Entity.Entity;
import org.example.backend.Entity.MovementChecker;
import org.example.backend.Entity.Player;
import org.example.backend.MapGenerator.GameMap;
import org.example.ui.Drawer;
import org.example.ui.KeyHandler;

import java.util.ArrayList;

public class GameTick {
    public static GameTickExitCodes gameTick(Drawer drawer, KeyHandler keyHandler, GameMap map, Player player, ArrayList<Entity> enemies) throws Exception{
        int[] playerMovement = keyHandler.handleInput(player);
        if (MovementChecker.isMovementAllowed(player, map, playerMovement)) {
            player.move(playerMovement[0], playerMovement[1]);
        }

        //получение roomid для тумана войны
        int[] cords = player.getCordXY();
        System.out.println(map.getPlayerRoomID(cords[0], cords[1]));


        drawer.draw(map.getMap(), player, enemies);
        Thread.sleep(32); // ~60 FPS
        return GameTickExitCodes.OK;
    }
}

