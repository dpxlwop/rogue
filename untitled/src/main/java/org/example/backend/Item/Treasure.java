package org.example.backend.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Treasure extends Item{

    @JsonCreator
    public Treasure(@JsonProperty("value") int value,
                    @JsonProperty("itemPos") int[] itemPos){
        super(value, itemPos);
    }

    @Override
    public char getSymbol(){
        return ItemsIcons.TREASURE.getSymbol();
    }
}
