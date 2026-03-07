package org.example.backend.Entity;
import java.util.Random;

import com.googlecode.lanterna.TextColor;
import org.example.backend.Interaction.MovementChecker;
import org.example.backend.MapGenerator.GameMap;
import org.example.backend.Interaction.MovementCodes;


public class MagicSnake extends Entity implements Enemy{
    private int evilness;
    public MagicSnake(int[] cordXY){
        super(cordXY, 6, 14, 6);
        this.evilness = 10;
    }

    public int getEvilness(){
        return this.evilness;
    }

    @Override
    public char getSymbol(){
        return Entities.MAGICSNAKE.getSymbol();
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
            if (enemyX > playerX)   //левее
                dx = -1;
            else                    //правее
                dx = 1;
            dy = -1;
        } else if(enemyY < playerY){
            if (enemyX > playerX)   //левее
                dx = -1;
            else                    //правее
                dx = 1;
            dy = 1;
        } else{
            dx = 1;
            dy = 1;
        }
        if (MovementChecker.isMovementAllowed(this, map, new int[]{dx, dy}, player) == MovementCodes.ALLOW) {
            this.move(dx,dy);
        }
        return new int[]{dx, dy};
    }

    private int[] entityRandomWalk(GameMap map){
        Random rand = new Random();
        int dx, dy;
        if (rand.nextDouble() < (this.getAgility() / 10.0 - 0.2)){        //получение вероятности хода
            while (true) {
                dx = rand.nextDouble() < 0.5 ? 1 : -1;
                dy = rand.nextDouble() < 0.5 ? 1 : -1;
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
        return TextColor.ANSI.MAGENTA;
    }
}
