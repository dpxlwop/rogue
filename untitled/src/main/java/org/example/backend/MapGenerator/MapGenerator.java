package org.example.backend.MapGenerator;
import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;
import java.util.ArrayList;
import org.example.backend.Tile;
import org.example.backend.MapGenerator.Room;
import java.util.Random;

public class MapGenerator {
    protected int width;
    protected int height;
    protected Tile[][] map;               //карта в виде двумерного массива тайлов
    protected ArrayList<Room> rooms;      //список комнат на карте
    private ArrayList<Entity> enemiesInRooms;
    private static final int ROOM_COUNT = 9;
    private static final int ROOM_PADDING = 2;

    public MapGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        init();
        generateAndCheckRooms();
        cutRooms();
        this.enemiesInRooms = new ArrayList<>();
        for(int i = 0; i < ROOM_COUNT; i++) {
            if (i > 0) {
                this.enemiesInRooms.add(rooms.get(i).getEnemyInRoom());
            }
        }
        this.map = CorridorGenerator.generateCorridors(this.map, this.rooms);
    }

    public ArrayList<Entity> getEnemiesInRooms(){
        return enemiesInRooms;
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
            if (roomHasConflict(new Room(x, y, w, h))) {        //проверка на пересечение с другими комнатами
                attempts++;
                i--;
                continue;
            }
            Room room = new Room(x, y, w, h);
            rooms.add(room);
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
