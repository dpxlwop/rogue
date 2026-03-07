package org.example.backend.Entity;

import com.googlecode.lanterna.TextColor;

public abstract class Entity {
    protected int[] cordXY;
    protected int[] oldCordXY;
    private int health;
    private int agility;
    private int strength;

    public Entity(int[] cordXY, int health, int agility, int strength) {
        this.cordXY = cordXY;
        this.oldCordXY = new int[]{cordXY[0], cordXY[1]};
        this.health = health;
        this.agility = agility;
        this.strength = strength;
    }

    public int[] getCordXY() {
        return cordXY;
    }

    public int getHealth() {
        return health;
    }

    public void setDamage(int damage){
        this.health-=damage;
    }

    public int getAgility() {
        return agility;
    }

    public int getStrength() {
        return strength;
    }

    public int[] getOldPos(){
        return oldCordXY;
    }

    public void move(int dx, int dy) {
        oldCordXY[0] = cordXY[0];
        oldCordXY[1] = cordXY[1];

        cordXY[0] += dx;
        cordXY[1] += dy;
    }

    public void setPosition(int[] newCordXY) {
        this.cordXY = newCordXY;
    }

    public abstract char getSymbol();

    public boolean isDead(){
        return health <= 0;
    }

    public abstract TextColor getColor();
}
