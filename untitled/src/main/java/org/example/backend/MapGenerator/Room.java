package org.example.backend.MapGenerator;

import org.example.backend.Entity.*;

import java.util.Random;

public class Room {
    private int x;
    private int y;
    private int width;
    private int height;
    private Entity enemyInRoom;

    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        generateRandomEntity();
    }

    private void generateRandomEntity(){
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

}
