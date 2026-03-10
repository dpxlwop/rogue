package org.example.backend;

import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;
import org.example.backend.Item.Item;
import org.example.backend.MapGenerator.GameMap;
import org.example.backend.MapGenerator.Room;
import org.example.ui.Drawer;
import org.example.ui.KeyHandler;

import java.util.ArrayList;

public class Game {
    private final int WIDTH = 120;
    private final int MAP_HEIGHT = 38;
    private final int SCREEN_HEIGHT = 40;
    private Drawer drawer;
    private int level;
    private KeyHandler keyHandler;
    private GameMap map;
    private Player player;
    private ArrayList<Entity> enemiesOnLevel;
    private ArrayList<Item> itemsOnLevel;

    public Game() throws Exception {
        this.drawer = new Drawer(WIDTH, MAP_HEIGHT, SCREEN_HEIGHT);
        this.level = 1;
        this.keyHandler = new KeyHandler(drawer.getScreen());
        this.map = new GameMap(WIDTH, MAP_HEIGHT);
        this.player = new Player(new int[]{1, 1}, 100, 10, 10);
        Room playerRoom = this.map.spawnPlayer(player);
        this.enemiesOnLevel = map.getEnemiesInRooms();
        this.itemsOnLevel = map.getItemsOnLevel();
        removeItemAndEnemyFromFirstRoom(playerRoom);
        this.drawer.drawWelcomeScreen();
    }

    public void removeItemAndEnemyFromFirstRoom(Room room){
        Item item = room.getItemInRoom();
        Entity entity = room.getEnemyInRoom();
        room.cleanUpRoom();
        enemiesOnLevel.remove(entity);
        itemsOnLevel.remove(item);
    }

    public ArrayList<Entity> getEnemiesOnLevel(){
        return this.enemiesOnLevel;
    }

    public ArrayList<Item> getItemsOnLevel(){
        return this.itemsOnLevel;
    }

    public int getWIDTH(){
        return this.WIDTH;
    }

    public int getMAP_HEIGHT(){
        return this.MAP_HEIGHT;
    }

    public int getSCREEN_HEIGHT(){
        return this.SCREEN_HEIGHT;
    }

    public int getLevel(){
        return this.level;
    }

    public KeyHandler getKeyHandler(){
        return this.keyHandler;
    }

    public Player getPlayer(){
        return this.player;
    }

    public GameMap getMap(){
        return this.map;
    }

    public Drawer getDrawer(){
        return this.drawer;
    }

    public Item getItemByCords(int cordX, int cordY){
        if (itemsOnLevel.size() > 0){
            for (int i = 0; i < itemsOnLevel.size(); i++){
                Item item = itemsOnLevel.get(i);
                if (item == null) continue;
                int[] cords = item.getCordXY();
                if (cords[0] == cordX && cords[1] == cordY)
                    return item;
            }
        }
        return null;
    }

    public void removeItemFromGame(Item item){
        this.itemsOnLevel.remove(item);
    }

    public void removeEntityFromGame(Entity entity){
        this.enemiesOnLevel.remove(entity);
    }

    public void generateNextLevel(){
        this.level++;
        this.map = new GameMap(WIDTH, MAP_HEIGHT);
        Room playerRoom = this.map.spawnPlayer(player);
        this.enemiesOnLevel = map.getEnemiesInRooms();
        this.itemsOnLevel = map.getItemsOnLevel();
        removeItemAndEnemyFromFirstRoom(playerRoom);
    }

}


