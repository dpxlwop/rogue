package org.example.backend.MapGenerator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Config;
import org.example.backend.Entity.*;
import org.example.backend.Item.Item;
import org.example.backend.Item.Treasure;
import org.example.backend.Tile;
import java.util.ArrayList;
import java.util.Random;

public class GameMap{
    private final int width = Config.WIDTH;
    private final int height = Config.MAP_HEIGHT;
    @JsonIgnore
    private Random rand;
    private int level;
    protected Tile[][] map;
    protected ArrayList<Room> rooms;
    private ArrayList<Entity> enemiesInRooms;
    private ArrayList<Item> itemsOnLevel;


    public GameMap(int level, Player player){
        this.level = level;
        MapGenerator generator = new MapGenerator(this.level, player);
        this.map = generator.getMap();
        rooms = generator.getRooms();
        this.enemiesInRooms = generator.getEnemiesInRooms();
        this.itemsOnLevel = generator.getItemsOnLevel();
        this.rand = new Random();
    }

    @JsonCreator
    public GameMap(@JsonProperty("level") int levelS,
            @JsonProperty("map") Tile[][] mapS,
            @JsonProperty("rooms") ArrayList<Room> roomsS,
            @JsonProperty("enemiesInRooms") ArrayList<Entity> enemies,
            @JsonProperty("itemsOnLevel") ArrayList<Item> items){
        this.level = levelS;
        this.map = mapS;
        this.rooms = roomsS;
        this.enemiesInRooms = enemies;
        this.itemsOnLevel = items;
        this.rand = new Random();
    }

    public void addItemOnLevel(Item item){
        this.itemsOnLevel.add(item);
    }

    public ArrayList<Entity> getEnemiesInRooms(){
        return enemiesInRooms;
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

    public ArrayList<Item> getItemsOnLevel(){
        return itemsOnLevel;
    }

    public Treasure summonTreasure(Entity deadEnemy, int cordX, int cordY){
        int treasureValue = 0;

        if (deadEnemy instanceof Ghost){
            treasureValue = 5 * this.level / 2 + rand.nextInt(5);
        }else if (deadEnemy instanceof MagicSnake){
            treasureValue = 15 * this.level / 2 + rand.nextInt(5);
        } else if (deadEnemy instanceof Ogre){
            treasureValue = 10 * this.level / 2 + rand.nextInt(5);
        } else if (deadEnemy instanceof Vampire){
            treasureValue = 15 * this.level / 2 + rand.nextInt(5);
        } else if (deadEnemy instanceof Zombie){
            treasureValue = 5 * this.level / 2 + rand.nextInt(5);
        }

        Treasure treasure = new Treasure(treasureValue, new int[]{cordX, cordY});
        return treasure;
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
