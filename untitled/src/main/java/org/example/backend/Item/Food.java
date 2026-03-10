package org.example.backend.Item;

import org.example.backend.Entity.Player;

public class Food  extends Item implements Usable {

    public Food(int healingValue, int[] itemPos){
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
