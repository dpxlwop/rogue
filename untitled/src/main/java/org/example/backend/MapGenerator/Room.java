package org.example.backend.MapGenerator;

import org.example.backend.Entity.*;
import org.example.backend.Item.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Room {
    private int x;
    private int y;
    private int width;
    private int height;
    private Entity enemyInRoom;
    private Item itemInRoom;
    private Item exitItem;

    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        generateRandomLoot();
        exitItem = null;
    }

    private static BuffAttributes selectRandomAttribute() {
        BuffAttributes[] levels = BuffAttributes.values();
        int randomIndex = ThreadLocalRandom.current().nextInt(0, levels.length);
        return levels[randomIndex];
    }

    private void generateRandomLoot() {
        Random rand = new Random();
        if (rand.nextDouble() < 0.9) {
            int type = rand.nextInt(5);
            switch (type) {
                case 0 -> this.enemyInRoom = new Zombie(this.getCenter());
                case 1 -> this.enemyInRoom = new Vampire(this.getCenter());
                case 2 -> this.enemyInRoom = new Ghost(this.getCenter());
                case 3 -> this.enemyInRoom = new Ogre(this.getCenter());
                case 4 -> this.enemyInRoom = new MagicSnake(this.getCenter());
            }
        }
        if (rand.nextDouble() < 0.9) {
            //TODO генерация предметов в комнате
            //все остальное вроде дописано, отрисовка рюкзака
            for (int i = 0; i < rand.nextInt(3); i++) {
                int type = rand.nextInt(4);
                int[] itempos = new int[]{this.getCenter()[0] + rand.nextInt(3), this.getCenter()[1] + rand.nextInt(3)};
                switch (type) {
                    case 0 -> this.itemInRoom = new Elix(selectRandomAttribute(), rand.nextInt(3)+1, itempos);
                    case 1 -> this.itemInRoom = new Food(rand.nextInt(4) +1 , itempos);
                    case 2 -> this.itemInRoom = new Roll(selectRandomAttribute(), rand.nextInt(3) + 1, itempos);
                    case 3 -> this.itemInRoom = new Weapon(rand.nextInt(4) + 1, itempos);
                }
            }
        }
    }

    public Item getItemInRoom(){
        return itemInRoom;
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
        itemInRoom = null;
        enemyInRoom = null;
    }

}
