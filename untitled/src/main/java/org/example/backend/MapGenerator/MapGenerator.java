package org.example.backend.MapGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.example.Config;
import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;
import java.util.ArrayList;

import org.example.backend.Item.ExitItem;
import org.example.backend.Item.Item;
import org.example.backend.Tile;

import java.util.Random;

@Getter @Setter
public class MapGenerator {
    private final int width = Config.WIDTH;
    private final int height = Config.MAP_HEIGHT;
    private Tile[][] map;               //карта в виде двумерного массива тайлов
    private ArrayList<Room> rooms;      //список комнат на карте
    private ArrayList<Entity> enemiesInRooms;
    private ArrayList<Item> itemsOnLevel;
    private static final int ROOM_COUNT = 9;
    private static final int ROOM_PADDING = 2;
    private int level;
    private Player player;

    public MapGenerator(int level, Player player) {
        this.player = player;
        this.level = level;
        init();
        boolean isRoomsGeneratedSuccessful = false;
        while (!isRoomsGeneratedSuccessful) {
            generateAndCheckRooms();
            if (rooms.size() == ROOM_COUNT) {
                isRoomsGeneratedSuccessful = true;
            } else {
                rooms.clear();
            }
        }
        cutRooms();
        this.enemiesInRooms = new ArrayList<>();
        this.itemsOnLevel = new ArrayList<>();
        for(int i = 0; i < rooms.size(); i++) {
            Entity enemy = rooms.get(i).getEnemyInRoom();
            if(enemy != null) enemiesInRooms.add(enemy);
            ArrayList<Item> items = rooms.get(i).getItemsInRoom();
            for(Item item : items){
                if(item != null) itemsOnLevel.add(item);
            }

        }
        this.map = CorridorGenerator.generateCorridors(this.map, this.rooms);
        summonExitItem();
    }

    public void addItemOnLevel(Item item){
        this.itemsOnLevel.add(item);
    }

    private void summonExitItem(){
        Room bottomRightRoom = getBottomRightRoom();
        int cordX = bottomRightRoom.getPosition()[0] + bottomRightRoom.getSize()[0] - 1;
        int cordY = bottomRightRoom.getPosition()[1] + bottomRightRoom.getSize()[1] - 1;
        bottomRightRoom.summonExitItem(new ExitItem(new int[]{cordX, cordY}));
        Item exitItem = bottomRightRoom.getExitItem();
        if(exitItem != null){
            this.addItemOnLevel(exitItem);
        }
    }

    private Room getBottomRightRoom(){
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

    private void init() {     //инициализация карты, делаем все стенами
        this.rooms = new ArrayList<>();
        this.map = new Tile[this.height][this.width];
        for (int y = 0; y < height; y++) {      //делаем все стенами
            for (int x = 0; x < width; x++) {
                this.map[y][x] = Tile.WALL;
            }
        }
    }

    private void generateAndCheckRooms(){
        Random rand = new Random();
        int w, h;
        int attempts = 0;
        for (int i = 0; i < ROOM_COUNT && attempts < 101; i++) {//генерируем 9 комнат с проверкой
            if (rand.nextDouble() < 0.1) {      //большие залы
                w = rand.nextInt(30) + 15;
                h = rand.nextInt(30) + 15;
            } else {
                w = rand.nextInt(20) + 5;
                h = rand.nextInt(15) + 5;
            }

            if (width - w - 1 <= 0 || height - h - 1 <= 0) {//помещается ли в карту
                attempts++;
                i--;
                continue;
            }
            int x = rand.nextInt(width - w - 1);
            int y = rand.nextInt(height - h - 1);
            Room generatedRoom = new Room(x, y, w, h, this.level, this.player);
            if (roomHasConflict(generatedRoom)) {        //проверка на пересечение с другими комнатами
                attempts++;
                i--;
                continue;
            }
            rooms.add(generatedRoom);
        }
    }


    private boolean roomHasConflict(Room newRoom) {     //проверка пересечения комнат, отступ 2
        int[] cords = newRoom.getPosition();
        int[] size = newRoom.getSize();
        int newLeft   = cords[0];
        int newRight  = newLeft + size[0];
        int newTop    = cords[1];
        int newBottom = newTop + size[1];
        for (Room room : rooms) {
            int[] roomCords = room.getPosition();
            int[] roomSize = room.getSize();
            int left   = roomCords[0];
            int right  = left + roomSize[0];
            int top    = roomCords[1];
            int bottom = top + roomSize[1];
            if (newLeft - ROOM_PADDING < right &&
                    newRight + ROOM_PADDING > left &&
                    newTop - ROOM_PADDING < bottom &&
                    newBottom + ROOM_PADDING > top) {
                return true;
            }
        }
        return false; //если нет
    }

    private void cutRooms(){
        for (Room r : rooms) {  //вырезаем комнаты
            int[] cords = r.getPosition();
            int[] size = r.getSize();
            for (int y = cords[1]; y < cords[1] + size[1]; y++) {
                for (int x = cords[0]; x < cords[0] + size[0]; x++) {
                    map[y][x] = Tile.FLOOR;
                }
            }
        }
    }
}
