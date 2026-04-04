package org.example.backend.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExitItem extends Item{

    @JsonCreator
    public ExitItem(@JsonProperty("cordXY") int[] cordXY){
        super(0, cordXY);
    }

    @Override
    public char getSymbol(){
        return ItemsIcons.EXIT.getSymbol();
    }
}
