package org.example.backend.Interaction;

import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;
import org.example.backend.MapGenerator.GameMap;
import org.example.backend.Tile;

import java.util.ArrayList;

public class MovementChecker {
    public static MovementCodes isMovementAllowed(Entity entity, GameMap mapGen, int[] movement, Player player) {

        Tile[][] map = mapGen.getMap();

        int[] pos = entity.getCordXY();

        int newX = pos[0] + movement[0];
        int newY = pos[1] + movement[1];
        if(player != null){
            if(isConflictCords(player.getCordXY(), new int[]{newX, newY}, mapGen)){
                return MovementCodes.FIGHT;
            }
        }
            // проверка границ
        if (newX < 0 || newY < 0 || newY >= map.length || newX >= map[0].length) {
            return MovementCodes.DENY;
        }

        // проверка стены
        if (map[newY][newX] == Tile.WALL) {
            return MovementCodes.DENY;
        }

        return MovementCodes.ALLOW;
    }

    private static boolean isConflictCords(int[] playerCords, int[] newCords, GameMap map){
        ArrayList<Entity> enemies = map.getEnemiesInRooms();
        for (Entity e : enemies){
            if (e == null) continue;
            if (newCords[0] == e.getCordXY()[0] && newCords[1] == e.getCordXY()[1])
                return true;
            if(newCords[0] == playerCords[0] && newCords[1] == playerCords[1])
                return true;
        }
        return false;
    }
}
