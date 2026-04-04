package org.example.backend.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Backpack {
    private ArrayList<Item> backpack;

    public Backpack(){
        this.backpack = new ArrayList<>();
    }

    @JsonCreator
    public Backpack(@JsonProperty ArrayList<Item> backpack){
        this.backpack = backpack;
    }

    public boolean addItemInBackpack(Item item){
        if(backpack.size() >= 9)
            return false;
        backpack.add(item);
        return true;
    }

    public ArrayList<Item> getBackpack(){
        return this.backpack;
    }

    public void removeItemFromBackpack(Item item){
        backpack.remove(item);
    }

    public int getItemsCounter(){
        return this.backpack.size();
    }
}
