package org.example.backend.Entity;
import com.googlecode.lanterna.TextColor;
import org.example.backend.Item.*;
import org.example.backend.MapGenerator.GameMap;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Player extends Entity{
    private int maxHealth;
    private int roomID;
    private int weaponValue;
    private Weapon equipedWeapon;
    private Backpack backpack;
    private int treasureCount;
    private boolean isStunned;
    private ArrayList<Elix> elixes;

    public Player(int[] cordXY, int health, int agility, int strength) {
        super(cordXY, health, agility, strength);
        this.maxHealth = health;
        this.backpack = new Backpack();
        this.treasureCount = 0;
        this.isStunned = false;
        this.elixes = new ArrayList<>();
    }

    @JsonCreator
    public Player(
            @JsonProperty("cordXY") int[] cordXY,
            @JsonProperty("oldCordXY") int[] oldCordXY,
            @JsonProperty("health") int health,
            @JsonProperty("agility") int agility,
            @JsonProperty("strength") int strength,
            @JsonProperty("maxHealth") int maxHealth,
            @JsonProperty("roomID") int roomID,
            @JsonProperty("weaponValue") int weaponValue,
            @JsonProperty("equipedWeapon") Weapon equipedWeapon,
            @JsonProperty("backpack") Backpack backpack,
            @JsonProperty("treasureCount") int treasureCount,
            @JsonProperty("isStunned") boolean isStunned,
            @JsonProperty("elixes") ArrayList<Elix> elixes){
        super(cordXY, health, agility, strength);
        this.oldCordXY = oldCordXY;
        this.maxHealth = maxHealth;
        this.roomID = roomID;
        this.weaponValue = weaponValue;
        this.equipedWeapon = equipedWeapon;
        this.backpack = (backpack != null) ? backpack : new Backpack();
        this.treasureCount = treasureCount;
        this.isStunned = isStunned;
        this.elixes = (elixes != null) ? elixes : new ArrayList<>();
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
        reduceTicksInActiveElixes();        //привязка движения к увеличению тиков в элексире
        oldCordXY[0] = cordXY[0];
        oldCordXY[1] = cordXY[1];

        cordXY[0] += dx;
        cordXY[1] += dy;
        checkElixDuration();                //проверяем истекшие и удаляем

    }
    public TextColor getColor(){
        return TextColor.ANSI.CYAN;
    }

    public int equipWeapon(Weapon weapon){
        this.addStrength(weapon.getItemValue());
        this.weaponValue = weapon.getItemValue();
        equipedWeapon = weapon;
        return this.getStrength();
    }

    public Weapon unEquipWeapon(){
        this.addStrength(-weaponValue);
        weaponValue = 0;
        Weapon oldWeapon = equipedWeapon;
        equipedWeapon = null;
        return oldWeapon;
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

    public String useItem(int slotNumber, GameMap map){
        if(slotNumber >= 0 && slotNumber < backpack.getItemsCounter()) {
            Item item = this.getBackpack().get(slotNumber);
            if (item instanceof Elix elix){
                addElix(elix);
                elix.use(this);
                this.backpack.removeItemFromBackpack(item);
                return String.format("Игрок использовал %s, увеличил %s на %d единиц", 
                    item.getClass().getSimpleName(), elix.getAttributeToBuff(), elix.getItemValue());
            } else if (item instanceof Usable usable) {
                usable.use(this);
                this.backpack.removeItemFromBackpack(item);
                return String.format("Игрок использовал %s", item.getClass().getSimpleName());
            } else if (item instanceof Equipable equipable) {
                if (isWeaponInUse()) {
                    Item weapon = getEquipedWeapon();
                    int[] playerPos = this.getCordXY();
                    int[] weaponCords = new int[]{playerPos[0] + 1, playerPos[1] + 1};
                    weapon.setItemPos(weaponCords);
                    map.addItemOnLevel(weapon);
                    unEquipWeapon();
                }
                equipable.equip(this);
                this.backpack.removeItemFromBackpack(item);
                return String.format("Игрок экипировал %s", item.getClass().getSimpleName());
            }
            this.backpack.removeItemFromBackpack(item);
        }
        return "";
    }



    public Item getEquipedWeapon(){
        return this.equipedWeapon;
    }

    public boolean isWeaponInUse(){
        return equipedWeapon != null;
    }

    public void damageMaxHealth(int damageMaxHealth){
        this.maxHealth-=damageMaxHealth;
    }


    public int getTreasure(){
        return treasureCount;
    }

    private void addElix(Elix elix){
        this.elixes.add(elix);
    }

    private void reduceTicksInActiveElixes(){
        for(Elix e : this.elixes){
                e.reduceTick();
        }
    }

    public void checkElixDuration(){
        for (int i = 0; i < elixes.size(); i++) {
            Elix e = elixes.get(i);
            if (e.isExpired()) {
                e.removeEffect(this);
                elixes.remove(i);
                i--;
            }
        }
        if(this.isDead()){
            this.health = 1;
        }
    }

    @Override
    public void addHealth(int health){
        this.health+=health;
        if (this.health > this.maxHealth){
            this.health = this.maxHealth;
        }
    }

}
