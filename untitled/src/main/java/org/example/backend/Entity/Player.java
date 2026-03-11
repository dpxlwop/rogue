package org.example.backend.Entity;
import com.googlecode.lanterna.TextColor;
import org.example.backend.Entity.Entities;
import org.example.backend.Item.*;

import java.util.ArrayList;

public class Player extends Entity{
    private int maxHealth;
    private int roomID;
    private int weaponValue;
    private Weapon equipedWeapon;
    private Backpack backpack;
    private int treasureCount;
    private boolean isStunned;

    public Player(int[] cordXY, int health, int agility, int strength) {
        super(cordXY, health, agility, strength);
        this.maxHealth = health;
        this.backpack = new Backpack();
        this.treasureCount = 0;
        this.isStunned = false;

    }

    public boolean isStunned(){
        return this.isStunned;
    }

    public void swapIsStunned(){
        this.isStunned = !isStunned;
    }

    @Override
    public char getSymbol(){
        return Entities.PLAYER.getSymbol();
    }
    public int getMaxHealth(){
        return this.maxHealth;
    }

    public int addMaxHealth(int health){
        this.maxHealth+=health;
        this.addHealth(health);
        return this.getHealth();
    }

    @Override
    public void move(int dx, int dy) {
        oldCordXY[0] = cordXY[0];
        oldCordXY[1] = cordXY[1];

        cordXY[0] += dx;
        cordXY[1] += dy;

    }
    public TextColor getColor(){
        return TextColor.ANSI.CYAN;
    }

    public int equipWeapon(Weapon weapon){
        this.addStrength(weapon.getItemValue());
        this.weaponValue = weapon.getItemValue();
        return this.getStrength();
    }

    public Weapon unEquipWeapon(){
        this.addStrength(-1 * weaponValue);
        weaponValue = 0;
        return equipedWeapon;
    }

    public ArrayList<Item> getBackpack(){
        return this.backpack.getBackpack();
    }

    public void pickUpItem(Item item){
        if (item instanceof Treasure treasure){
            this.treasureCount+=item.getItemValue();
        } else{
            this.backpack.addItemInBackpack(item);
        }
    }

    public void useItem(int slotNumber){
        if(slotNumber >= 0 && slotNumber < backpack.getItemsCounter()) {
            Item item = this.getBackpack().get(slotNumber);
            if (item instanceof Usable usable) {
                usable.use(this);
            } else if (item instanceof Equipable equipable)
                equipable.equip(this);
            this.backpack.removeItemFromBackpack(item);
        }
    }

    public void damageMaxHealth(int damageMaxHealth){
        this.maxHealth-=damageMaxHealth;
    }


    public int getTreasure(){
        return treasureCount;
    }
}
