package org.example.backend.Entity;
import java.util.Random;
import org.example.backend.Entity.Entities;
import org.example.backend.MapGenerator.GameMap;

public class Zombie extends Entity implements Enemy{
    public Zombie(int[] cordXY, int health, int agility, int strength){
        super(cordXY, health, agility, strength);
    }

    @Override
    public char getSymbol(){
        return Entities.ZOMBIE.getSymbol();
    }

    @Override
    public int[] entityRandomWalk(GameMap map){
        Random rand = new Random();
        boolean isMovementCompleted;
        int dx, dy;
        if (rand.nextDouble() < (this.getAgility() / 10.0 - 0.2)){        //получение вероятности хода
            isMovementCompleted = false;
            while (!isMovementCompleted) {
                dx = 0;
                dy = 0;
                if (rand.nextDouble() < 0.5) {       //перемещение по x
                    dx = rand.nextDouble() < 0.5 ? 1 : -1;
                    dy = 0;
                } else {
                    dx = 0;
                    dy = rand.nextDouble() < 0.5 ? 1 : -1;
                }
                if (MovementChecker.isMovementAllowed(this, map, new int[]{dx, dy})) {
                    this.move(dx, dy);
                    isMovementCompleted = true;
                    System.out.println(String.format("ent moved to %d %d", dx, dy));
                    return new int[]{dx, dy};
                }
            }
        }
        return new int[]{0, 0};
    }
}
