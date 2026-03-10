package org.example.backend.Item;

import org.example.backend.Entity.Player;

public class Roll extends Item implements Usable{
    private BuffAttributes attributeToBuff;

    public Roll(BuffAttributes attributeToBuff, int buffValue, int[] itemPos){
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
        return ItemsIcons.ROLL.getSymbol();
    }
}
