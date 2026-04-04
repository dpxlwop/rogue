package org.example.backend.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.backend.Entity.Player;

public class Roll extends Item implements Usable{
    private BuffAttributes attributeToBuff;

    @JsonCreator
    public Roll(@JsonProperty("attributeToBuff") BuffAttributes attributeToBuff,
                @JsonProperty("buffValue") int buffValue,
                @JsonProperty("itemPos") int[] itemPos){
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
