package org.example.backend.Item;

import org.example.backend.Entity.Player;
import org.example.backend.Item.ItemsIcons;

//TODO добавить временность

public class Elix extends Item implements Usable{
    private BuffAttributes attributeToBuff;
    private int durationTicks;
    private int ticksActive;

    public Elix(BuffAttributes attributeToBuff, int buffValue, int[] itemPos, int duration){
        super(buffValue, itemPos);
        this.attributeToBuff = attributeToBuff;
        this.durationTicks = duration;
        this.ticksActive = 0;
    }

    public void removeEffect(Player player){
        switch (attributeToBuff){
            case AGILITY -> player.addAgility(this.getItemValue() * -1);
            case STRENGTH -> player.addStrength(this.getItemValue() * -1);
            case MAX_HEALTH -> player.addMaxHealth(this.getItemValue() * -1);
        }
    }

    public void use(Player player){
        System.out.println(String.format("%s %d, %d", attributeToBuff, getItemValue(), this.durationTicks));
        switch (attributeToBuff){
            case AGILITY -> player.addAgility(this.getItemValue());
            case STRENGTH -> player.addStrength(this.getItemValue());
            case MAX_HEALTH -> player.addMaxHealth(this.getItemValue());
        }
    }

    public void reduceTick(){
        this.ticksActive++;
    }

    public boolean isExpired(){
        return durationTicks == ticksActive;
    }


    @Override
    public char getSymbol(){
        return ItemsIcons.ELIX.getSymbol();
    }
}
