package org.example.backend.Interaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.backend.Entity.Entity;


public class EnemyWalkingExitObj {
    private int[] cords;
    private MovementCodes exitCode;
    private Entity entity;

    @JsonIgnore
    public EnemyWalkingExitObj(int[] cords, MovementCodes exitCode, Entity entity){
        this.cords = cords;
        this.exitCode = exitCode;
        this.entity = entity;
    }

    public int[] getCords(){
        return cords;
    }

    public MovementCodes getExitCode(){
        return exitCode;
    }

    public Entity getEntity(){
        return entity;
    }

}
