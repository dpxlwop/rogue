package org.example.Game;

import org.example.Config;
import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;
import org.example.backend.Item.Item;
import org.example.backend.MapGenerator.GameMap;
import org.example.backend.MapGenerator.Room;
import org.example.backend.MessageLog;
import org.example.ui.Drawer;
import org.example.ui.KeyHandler;
import org.example.ui.UiMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Game {
    private int level;
    private GameMap map;
    private Player player;
    private ArrayList<Entity> enemiesOnLevel;
    private ArrayList<Item> itemsOnLevel;
    @JsonIgnore
    private MessageLog messageLog;
    @JsonIgnore
    private UiMaster uiMaster;


    public Game() throws Exception {
        this.messageLog = new MessageLog();
        this.level = 1;
        this.player = new Player(new int[]{1, 1}, 50, 50, 50);
        this.map = new GameMap(1, this.player);
        Room playerRoom = this.map.spawnPlayer(player);
        this.enemiesOnLevel = map.getEnemiesInRooms();
        this.itemsOnLevel = map.getItemsOnLevel();
        removeItemAndEnemyFromFirstRoom(playerRoom);
    }

    @JsonCreator
    public Game(@JsonProperty("level") int jsonLevel,
                @JsonProperty("map") GameMap jsonMap,
                @JsonProperty("player") Player player,
                @JsonProperty("enemiesOnLevel") ArrayList<Entity> jsonEnemies,
                @JsonProperty("itemsOnLevel") ArrayList<Item> jsonItems
                ) throws Exception {
        this.messageLog = new MessageLog();
        this.level = jsonLevel;
        this.map = jsonMap;
        this.player = player;
        this.enemiesOnLevel = (jsonEnemies != null) ? jsonEnemies : new ArrayList<>();
        this.itemsOnLevel = (jsonItems != null) ? jsonItems : new ArrayList<>();
        this.enemiesOnLevel = map.getEnemiesInRooms();
        this.itemsOnLevel = map.getItemsOnLevel();
    }

    public void setUiMaster(UiMaster uiMaster) throws Exception {
        this.uiMaster = uiMaster;
        this.uiMaster.getDrawer().draw(this);
    }

    public void removeItemAndEnemyFromFirstRoom(Room room){
        ArrayList<Item> items = room.getItemsInRoom();
        Entity entity = room.getEnemyInRoom();
        room.cleanUpRoom();
        enemiesOnLevel.remove(entity);
        for (Item i: items) {
            itemsOnLevel.remove(i);
        }
    }

    public ArrayList<Entity> getEnemiesOnLevel(){
        return this.enemiesOnLevel;
    }

    public ArrayList<Item> getItemsOnLevel(){
        return this.itemsOnLevel;
    }


    public int getLevel(){
        return this.level;
    }

    @JsonIgnore
    public KeyHandler getKeyHandler(){
        return this.uiMaster.getKeyHandler();
    }

    public Player getPlayer(){
        return this.player;
    }

    public GameMap getMap(){
        return this.map;
    }

    @JsonIgnore
    public Drawer getDrawer(){
        return this.uiMaster.getDrawer();
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
        this.map = new GameMap(this.level, this.player);
        Room playerRoom = this.map.spawnPlayer(player);
        this.enemiesOnLevel = map.getEnemiesInRooms();
        this.itemsOnLevel = map.getItemsOnLevel();
        removeItemAndEnemyFromFirstRoom(playerRoom);
    }

    public void addItemToMap(Item item){
        if (item != null){
            itemsOnLevel.add(item);
        }
    }

    @JsonIgnore
    public MessageLog getMessageLog(){
        return this.messageLog;
    }

    @JsonIgnore
    public UiMaster getUiMaster(){
        return this.uiMaster;
    }

}


