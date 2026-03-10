package org.example.backend.Item;

import org.example.backend.Entity.Player;
import org.example.backend.Item.ItemsIcons;

//TODO добавить временность

public class Elix extends Item implements Usable{
    private BuffAttributes attributeToBuff;

    public Elix(BuffAttributes attributeToBuff, int buffValue, int[] itemPos){
        super(buffValue, itemPos);
        this.attributeToBuff = attributeToBuff;
    }

    public void use(Player player){
        switch (attributeToBuff){
            case AGILITY -> player.addAgility(this.getItemValue());
            case STRENGTH -> player.addStrength(this.getItemValue());
            case MAX_HEALTH -> player.addMaxHealth(this.getItemValue());
        }
    }

    @Override
    public char getSymbol(){
        return ItemsIcons.ELIX.getSymbol();
    }
}
