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
    private Random rand;

    public Room(int x, int y, int width, int height, Player player, int level) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rand = new Random();
        generateRandomShitInRoom(player, level);
        exitItem = null;
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

        if (rand.nextDouble() < 0.9) {
            //TODO генерация нескольких предметов в комнате
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

    private double getChanceOfHardEnemy(int level, Player player){
        if (level >= 15){
            return 0.8;
        } else{
            return (level/100.0) + (player.getHealth() / 250.0) + (player.getAgility() / 100.0);
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
