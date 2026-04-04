package org.example.backend.MapGenerator;

import org.example.backend.Entity.*;
import org.example.backend.Item.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Room {
    private int x;
    private int y;
    private int width;
    private int height;
    private Entity enemyInRoom;
    private ArrayList<Item> itemsInRoom;
    private Item exitItem;
    @JsonIgnore
    private Random rand;

    public Room(int x, int y, int width, int height, int level, Player player) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        itemsInRoom = new ArrayList<>();
        rand = new Random();
        generateRandomShitInRoom(player, level);
        exitItem = null;
    }

    @JsonCreator
    public Room(@JsonProperty("x") int x,
                @JsonProperty("y") int y,
                @JsonProperty("width") int width,
                @JsonProperty("height") int height,
                @JsonProperty("enemyInRoom") Entity enemyInRoom,
                @JsonProperty("itemsInRoom") ArrayList<Item> itemsInRoom,
                @JsonProperty("exitItem") Item exitItem) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.enemyInRoom = enemyInRoom;
        this.itemsInRoom = (itemsInRoom != null) ? itemsInRoom : new ArrayList<>();
        this.exitItem = exitItem;
        this.rand = new Random();
    }

    private static BuffAttributes selectRandomAttribute() {
        BuffAttributes[] levels = BuffAttributes.values();
        int randomIndex = ThreadLocalRandom.current().nextInt(0, levels.length);
        return levels[randomIndex];
    }


    private void generateRandomEntity(double chance){
        double selectedEnemy = rand.nextDouble();
        if (selectedEnemy < chance){
            int type = rand.nextInt(3);
            switch (type) {
                case 0 -> this.enemyInRoom = new Vampire(this.getCenter());
                case 1 -> this.enemyInRoom = new Ogre(this.getCenter());
                case 2 -> this.enemyInRoom = new MagicSnake(this.getCenter());
            }
        } else{
            int type = rand.nextInt(2);
            switch (type) {
                case 0 -> this.enemyInRoom = new Zombie(this.getCenter());
                case 1 -> this.enemyInRoom = new Ghost(this.getCenter());
            }
        }
    }

    private void generateRandomShitInRoom(Player player, int level) {

        double chance = getChanceOfHardEnemy(level, player);

        generateRandomEntity(chance);

        if (rand.nextDouble() < 0.8 / level + 0.2) {
            int itemsCount;
            if (rand.nextDouble() > 0.8 / level + 0.2)
                itemsCount = 1;
            else
                itemsCount = rand.nextInt(4);
            for (int i = 0; i < itemsCount; i++) {
                int type = rand.nextInt(4);
                int[] itempos = new int[]{this.getCenter()[0] + rand.nextInt(3), this.getCenter()[1] + rand.nextInt(3)};
                switch (type) {
                    case 0 -> this.itemsInRoom.add(new Elix(selectRandomAttribute(), rand.nextInt(3)+1, itempos, rand.nextInt(256)+16));
                    case 1 -> this.itemsInRoom.add(new Food(rand.nextInt(4) +1 , itempos));
                    case 2 -> this.itemsInRoom.add(new Roll(selectRandomAttribute(), rand.nextInt(3) + 1, itempos));
                    case 3 -> this.itemsInRoom.add(new Weapon(rand.nextInt(4) + 1, itempos));
                }
            }
        }
    }

    private double getChanceOfHardEnemy(int level, Player player){
        if (level >= 15){
            return 0.8;
        } else{
            return (level/100.0) + (player.getHealth() / 250.0) + (player.getAgility() / 100.0);
        }
    }

    public ArrayList<Item> getItemsInRoom(){
        return itemsInRoom;
    }

    public Entity getEnemyInRoom(){
        return this.enemyInRoom;
    }

    public int[] getPosition() {
        return new int[]{x, y};
    }

    public int[] getSize() {
        return new int[]{width, height};
    }

    public int[] getCenter() {
        return new int[]{centerX(), centerY()};
    }

    public int centerX() {
        return x + width / 2;
    }

    public int centerY() {
        return y + height / 2;
    }

    public Item getExitItem(){
        return exitItem;
    }

    public void summonExitItem(Item exitItem){
        this.exitItem = exitItem;
    }

    public void cleanUpRoom() {
        itemsInRoom = null;
        enemyInRoom = null;
    }

}
