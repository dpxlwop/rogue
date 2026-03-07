package org.example.backend.Entity;
import org.example.backend.Entity.Entities;

public class Player extends Entity{
    private int maxHealth;
    private int roomID;

    public Player(int[] cordXY, int health, int agility, int strength) {
        super(cordXY, health, agility, strength);
        this.maxHealth = health;
    }

    @Override
    public char getSymbol(){
        return Entities.PLAYER.getSymbol();
    }
    public int getMaxHealth(){
        return this.maxHealth;
    }

    @Override
    public void move(int dx, int dy) {
        oldCordXY[0] = cordXY[0];
        oldCordXY[1] = cordXY[1];

        cordXY[0] += dx;
        cordXY[1] += dy;

    }

}
