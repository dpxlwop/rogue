package org.example.backend.MapGenerator;

import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;
import org.example.backend.Tile;

public class GameMap extends MapGenerator{
    public GameMap(int width, int height){
        super(width, height);
    }

    public int[] getSize(){
        return new int[]{width, height};
    }

    public Tile[][] getMap() {
        return map;
    }


    public void spawnPlayer(Player player) {
        if (rooms.isEmpty()) return;
        Room firstRoom = rooms.get(0);
        int[] center = firstRoom.getCenter();
        player.setPosition(center);
    }

    public int getPlayerRoomID(int x, int y){
        int roomId = -1;
        for (int i = 0; i < rooms.size(); i++){
            int[] pos = rooms.get(i).getPosition();
            int roomX0 = pos[0];
            int roomY0 = pos[1];
            int[] roomSize = rooms.get(i).getSize();
            int roomX = roomX0 + roomSize[0];
            int roomY = roomY0 + roomSize[1];
            if (x >= roomX0 && x <= roomX && y >= roomY0 && y <= roomY){
                roomId = i;
            }
        }
        return roomId;
    }
}
