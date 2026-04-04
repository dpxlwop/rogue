package org.example.backend.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.backend.Entity.Player;

public class Weapon extends Item implements Equipable {

    @JsonCreator
    public Weapon(@JsonProperty("strength") int strength,
                  @JsonProperty("itemPos") int[] itemPos){
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
