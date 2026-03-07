package org.example.backend.Entity;
import java.util.Random;

import com.googlecode.lanterna.TextColor;
import org.example.backend.Interaction.MovementChecker;
import org.example.backend.MapGenerator.GameMap;
import org.example.backend.Interaction.MovementCodes;


public class Ogre extends Entity implements Enemy{
    private int evilness;
    public Ogre(int[] cordXY){
        super(cordXY, 14, 2, 14);
        this.evilness = 6;
    }

    public int getEvilness(){
        return this.evilness;
    }

    @Override
    public char getSymbol(){
        return Entities.OGRE.getSymbol();
    }

    @Override
    public int[] enemyWalking(GameMap map, Player player){
        int entityRoomId = map.getEntityRoomID(this);
        int playerRoomId = map.getEntityRoomID(player);
        int[] movement;
        if (entityRoomId == playerRoomId){
            movement = entityFollowsPlayer(map, player);
        } else{
            movement = entityRandomWalk(map);
        }
        return movement;
    }

    private int[] entityFollowsPlayer(GameMap map, Player player){
        int playerX = player.getCordXY()[0], playerY = player.getCordXY()[1];
        int enemyX = this.cordXY[0], enemyY = this.cordXY[1];
        int dx = 0, dy = 0;
        if (enemyY > playerY){
            //если мы выше игрока
            dx = 0;
            dy = -2;
        } else if(enemyY < playerY){
            //если мы ниже игрока
            dx = 0;
            dy = 2;
        } else if (enemyX > playerX) {
            //если мы левее игрока
            dx = -2;
            dy = 0;
        }  else if(enemyX < playerX) {
            //если мы правее игрока
            dx = 2;
            dy = 0;
        }
        if (MovementChecker.isMovementAllowed(this, map, new int[]{dx, dy}, player) == MovementCodes.ALLOW) {
            this.move(dx,dy);
        }
        return new int[]{dx, dy};
    }

    private int[] entityRandomWalk(GameMap map){
        Random rand = new Random();
        int dx, dy;
        if (rand.nextDouble() < (this.getAgility() / 10.0)){        //получение вероятности хода
            while (true) {
                dx = 0;
                dy = 0;
                if (rand.nextDouble() < 0.5) {       //перемещение по x
                    dx = rand.nextDouble() < 0.5 ? 2 : -2;
                    dy = 0;
                } else {
                    dx = 0;
                    dy = rand.nextDouble() < 0.5 ? 2 : -2;
                }
                if (MovementChecker.isMovementAllowed(this, map, new int[]{dx, dy}, null) == MovementCodes.ALLOW) {
                    this.move(dx, dy);
                    return new int[]{dx, dy};
                }
            }
        }
        return new int[]{0, 0};
    }
    @Override
    public TextColor getColor(){
        return TextColor.ANSI.YELLOW;
    }
}
