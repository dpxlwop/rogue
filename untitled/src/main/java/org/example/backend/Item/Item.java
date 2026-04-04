package org.example.backend.Item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.backend.Entity.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Elix.class, name = "elix"),
        @JsonSubTypes.Type(value = ExitItem.class, name = "exit"),
        @JsonSubTypes.Type(value = Food.class, name = "food"),
        @JsonSubTypes.Type(value = Roll.class, name = "roll"),
        @JsonSubTypes.Type(value = Treasure.class, name = "treasure"),
        @JsonSubTypes.Type(value = Weapon.class, name = "weapon")
})
public abstract class Item {
    private int itemValue;
    private int[] itemPos;

    @JsonCreator
    public Item(@JsonProperty("value") int value,
                @JsonProperty("pos") int[] pos){
        this.itemValue = value;
        this.itemPos = pos;
    }

    public void setItemPos(int[] pos){
        this.itemPos = pos;
    }

    public int[] getCordXY() {
        return itemPos;
    }

    public int getItemValue(){
        return this.itemValue;
    }

    public void setItemValue(int value){
        this.itemValue = value;
    }

    public abstract char getSymbol();
}
