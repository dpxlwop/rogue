package org.example.backend.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.backend.Entity.Player;

public class Food  extends Item implements Usable {

    @JsonCreator
    public Food(@JsonProperty("healingValue") int healingValue,
                @JsonProperty("itemPos") int[] itemPos){
        super(healingValue, itemPos);

    }

    @Override
    public void use(Player player) {
        player.addHealth(this.getItemValue());
        this.setItemValue(0);
    }

    @Override
    public char getSymbol(){
        return ItemsIcons.FOOD.getSymbol();
    }
}
