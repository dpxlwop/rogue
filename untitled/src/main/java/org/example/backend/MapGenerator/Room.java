package org.example.backend.MapGenerator;

import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Zombie;

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
            this.enemyInRoom = new Zombie(this.getCenter(), 10, 10, 10);
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
