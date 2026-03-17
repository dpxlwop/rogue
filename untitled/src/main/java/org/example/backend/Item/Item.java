package org.example.backend.Item;

import org.example.backend.Entity.Entities;

public abstract class Item {
    private int itemValue;
    private int[] itemPos;

    public Item(int value, int[] pos){
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
