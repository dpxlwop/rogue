package org.example.backend.Entity;
import java.util.Random;
import com.googlecode.lanterna.TextColor;
import org.example.backend.Interaction.EnemyWalkingExitObj;
import org.example.backend.Interaction.MovementChecker;
import org.example.backend.MapGenerator.GameMap;
import org.example.backend.Interaction.MovementCodes;


public class Ghost extends Entity implements Enemy{

    public Ghost(){}

    public Ghost(int[] cordXY){
        super(cordXY, 2, 10, 2);
    }

    public int getEvilness(){
        return 2;
    }

    @Override
    public char getSymbol(){
        return Entities.GHOST.getSymbol();
    }

    @Override
    public EnemyWalkingExitObj enemyWalking(GameMap map, Player player){
        int entityRoomId = map.getEntityRoomID(this);
        int playerRoomId = map.getEntityRoomID(player);
        EnemyWalkingExitObj movement;
        if (entityRoomId == playerRoomId){
            movement = entityFollowsPlayer(map, player);
        } else{
            movement = entityRandomWalk(map);
        }
        return movement;
    }

    private EnemyWalkingExitObj entityFollowsPlayer(GameMap map, Player player){
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
        MovementCodes movementCode = MovementChecker.isMovementAllowed(this, map, new int[]{dx, dy}, player);
        if (movementCode == MovementCodes.ALLOW) {
            this.move(dx,dy);
        }
        return new EnemyWalkingExitObj(this.getCordXY(), movementCode, this);
    }

    private EnemyWalkingExitObj entityRandomWalk(GameMap map){
        Random rand = new Random();
        int dx, dy;
        if (rand.nextDouble() < (this.getAgility() / 10.0 - 0.2)) {        //получение вероятности хода
            while (true) {
                if (rand.nextDouble() < 0.5) {       //перемещение по x
                    if (rand.nextDouble() < 0.5) {
                        dx = rand.nextInt(4);
                    } else {
                        dx = -1 * rand.nextInt(4);
                    }
                    dy = 0;
                } else {
                    dx = 0;
                    if (rand.nextDouble() < 0.5) {
                        dy = rand.nextInt(4);
                    } else {
                        dy = -1 * rand.nextInt(4);
                    }
                }
                MovementCodes movementCode = MovementChecker.isMovementAllowed(this, map, new int[]{dx, dy}, null);
                if (movementCode == MovementCodes.ALLOW) {
                    this.move(dx, dy);
                    return new EnemyWalkingExitObj(this.getCordXY(), movementCode, this);
                }
            }
        }
        return new EnemyWalkingExitObj(this.getCordXY(), MovementCodes.DENY, this);
    }

    @Override
    public TextColor getColor(){
        return TextColor.ANSI.CYAN_BRIGHT;
    }

}
