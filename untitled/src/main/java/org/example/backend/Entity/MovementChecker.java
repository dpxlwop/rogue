package org.example.backend.Entity;

import org.example.backend.MapGenerator.GameMap;
import org.example.backend.Tile;

public class MovementChecker {
    public static boolean isMovementAllowed(Entity entity, GameMap mapGen, int[] movement) {

        Tile[][] map = mapGen.getMap();

        int[] pos = entity.getCordXY();

        int newX = pos[0] + movement[0];
        int newY = pos[1] + movement[1];

        // проверка границ
        if (newX < 0 || newY < 0 || newY >= map.length || newX >= map[0].length) {
            return false;
        }

        // проверка стены
        if (map[newY][newX] == Tile.WALL) {
            return false;
        }

        return true;
    }
}
