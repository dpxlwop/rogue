package org.example.backend.Item;

import org.example.backend.Entity.Player;

public class Weapon extends Item implements Equipable {

    public Weapon(int strength, int[] itemPos){
        super(strength, itemPos);
    }

    public void equip(Player player){
        player.equipWeapon(this);
    }

    public void unEquip(Player player){
        player.unEquipWeapon();
    }

    @Override
    public char getSymbol(){
        return ItemsIcons.WEAPON.getSymbol();
    }
}
