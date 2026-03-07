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
    public int[] enemyWalking(GameMap map, Player player){
        int entityRoomId = map.getEntityRoomID(this);
        int playerRoomId = map.getEntityRoomID(player);
        int[] movement = {0, 0};
        if (entityRoomId == playerRoomId){
            movement = entityFollowsPlayer(map, player);
        } else{
            movement = entityRandomWalk(map);
        }
        return movement;
    }

    @Override
    public int[] entityFollowsPlayer(GameMap map, Player player){
        int playerX = player.getCordXY()[0], playerY = player.getCordXY()[1];
        int enemyX = this.cordXY[0], enemyY = this.cordXY[1];
        int dx = 0, dy = 0;
        if (enemyY > playerY){
            //если мы выше игрока
            dx = 0;
            dy = -1;
        } else if(enemyY < playerY){
            //если мы ниже игрока
            dx = 0;
            dy = 1;
        } else if (enemyX > playerX) {
            //если мы левее игрока
            dx = -1;
            dy = 0;
        }  else if(enemyX < playerX) {
            //если мы правее игрока
            dx = 1;
            dy = 0;
        }
        if (MovementChecker.isMovementAllowed(this, map, new int[]{dx, dy})) {
            this.move(dx,dy);
        }
        return new int[]{dx, dy};
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
