package org.example.backend.MapGenerator;

import org.example.backend.Entity.Enemy;
import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;
import org.example.backend.Item.ExitItem;
import org.example.backend.Item.Item;
import org.example.backend.Tile;

public class GameMap extends MapGenerator{
    public GameMap(int width, int height){
        super(width, height);
        summonExitEntity();
    }

    public int[] getSize(){
        return new int[]{width, height};
    }

    public Tile[][] getMap() {
        return map;
    }


    public Room spawnPlayer(Player player) {
        if (rooms.isEmpty()) return null;
        Room topRightRoom = getTopLeftRoom();
        int[] center = topRightRoom.getCenter();
        player.setPosition(center);
        return topRightRoom;
    }

    private void summonExitEntity(){
        Room bottomRightRoom = getBottomRightRoom();
        int cordX = bottomRightRoom.getPosition()[0] + bottomRightRoom.getSize()[0] - 1;
        int cordY = bottomRightRoom.getPosition()[1] + bottomRightRoom.getSize()[1] - 1;
        bottomRightRoom.summonExitItem(new ExitItem(new int[]{cordX, cordY}));
        Item exitItem = bottomRightRoom.getExitItem();
        if(exitItem != null){
            this.addItemOnLevel(exitItem);
        }
    }

    public Room getTopLeftRoom(){
        int minX = this.height + 1, minY = this.width + 1;
        Room topLeftRoom = null;
        for (Room r : rooms){
            int cord[] = r.getPosition();
            if(cord[0] <= minX && cord[1] <= minY){
                minX = cord[0];
                minY = cord[1];
                topLeftRoom = r;
            }
        }
        return topLeftRoom;
    }

    public Room getBottomRightRoom(){
        int maxX = -1, maxY = -1;
        Room bottomRightRoom = null;
        for (Room r : rooms){
            int[] cord = r.getPosition();
            if(cord[0] >= maxX && cord[1] >= maxY){
                maxX = cord[0];
                maxY = cord[1];
                bottomRightRoom = r;
            }
        }
        return bottomRightRoom;
    }

    public int getEntityRoomID(Entity entity){
        int[] cords = entity.getCordXY();
        int x = cords[0], y = cords[1];
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
