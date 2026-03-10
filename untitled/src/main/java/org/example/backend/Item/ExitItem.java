package org.example.backend.Item;

public class ExitItem extends Item{

    public ExitItem(int[] cordXY){
        super(0, cordXY);
    }

    @Override
    public char getSymbol(){
        return ItemsIcons.EXIT.getSymbol();
    }
}
