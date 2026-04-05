package org.example.backend.Item;
import org.example.backend.Entity.Player;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


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

    @JsonCreator
    public Elix(@JsonProperty("attributeToBuff") BuffAttributes attributeToBuff,
                @JsonProperty("itemValue") int buffValue,
                @JsonProperty("itemPos") int[] itemPos,
                @JsonProperty("durationTicks") int duration,
                @JsonProperty("ticksActive") int ticksActive){
        super(buffValue, itemPos);
        this.attributeToBuff = attributeToBuff;
        this.durationTicks = duration;
        this.ticksActive = ticksActive;
    }

    public void removeEffect(Player player){
        switch (attributeToBuff){
            case AGILITY -> player.addAgility(this.getItemValue() * -1);
            case STRENGTH -> player.addStrength(this.getItemValue() * -1);
            case MAX_HEALTH -> player.addMaxHealth(this.getItemValue() * -1);
        }
    }

    public void use(Player player){
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

    public BuffAttributes getAttributeToBuff(){
        return this.attributeToBuff;
    }

    @Override
    public char getSymbol(){
        return ItemsIcons.ELIX.getSymbol();
    }
}
