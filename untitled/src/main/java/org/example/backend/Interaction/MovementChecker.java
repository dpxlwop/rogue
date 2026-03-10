package org.example.backend.Interaction;

import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;
import org.example.backend.Item.ExitItem;
import org.example.backend.Item.Item;
import org.example.backend.MapGenerator.GameMap;
import org.example.backend.Tile;

import java.util.ArrayList;

public class MovementChecker {

    public static MovementCodes isMovementAllowed(Entity entity, GameMap mapGen, int[] movement, Player player) {

        Tile[][] map = mapGen.getMap();
        ArrayList<Item> items = mapGen.getItemsOnLevel();
        int[] pos = entity.getCordXY();

        int newX = pos[0] + movement[0];
        int newY = pos[1] + movement[1];

        int[] newPos = new int[]{newX, newY};

        if (newX < 0 || newY < 0 || newY >= map.length || newX >= map[0].length) {      //рганицы
            return MovementCodes.DENY;
        }

        if (map[newY][newX] == Tile.WALL) {         //стены
            return MovementCodes.DENY;
        }

        Item item = getItemAt(newX, newY, items);

        if (player != null) {
            if (isConflictCords(player.getCordXY(), newPos, mapGen)) {      //бой
                return MovementCodes.FIGHT;
            }

            if (item != null) {
                if (item instanceof ExitItem)
                    return MovementCodes.NEXT_LEVEL;
                else
                    return MovementCodes.PICK_UP_ITEM;
            }

        } else {
            if (item != null) {
                return MovementCodes.DENY;
            }
        }

        return MovementCodes.ALLOW;
    }

    private static Item getItemAt(int x, int y, ArrayList<Item> items) {
        for (Item item : items) {
            if (item == null) continue;
            int[] cords = item.getCordXY();
            if (x == cords[0] && y == cords[1]) {
                return item;
            }
        }
        return null;
    }

    private static boolean isConflictCords(int[] playerCords, int[] newCords, GameMap map) {
        if (newCords[0] == playerCords[0] && newCords[1] == playerCords[1]) {
            return true;
        }
        ArrayList<Entity> enemies = map.getEnemiesInRooms();
        for (Entity e : enemies) {
            if (e == null) continue;
            int[] cords = e.getCordXY();
            if (newCords[0] == cords[0] && newCords[1] == cords[1]) {
                return true;
            }
        }
        return false;
    }
}