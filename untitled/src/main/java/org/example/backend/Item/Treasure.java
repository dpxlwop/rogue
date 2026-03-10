package org.example.backend.Item;

public class Treasure extends Item{

    public Treasure(int value, int[] itemPos){
        super(value, itemPos);
    }

    @Override
    public char getSymbol(){
        return ItemsIcons.TREASURE.getSymbol();
    }
}
