package org.example.backend.Entity;

import org.example.backend.Interaction.EnemyWalkingExitObj;
import org.example.backend.MapGenerator.GameMap;

public interface Enemy {
    public EnemyWalkingExitObj enemyWalking(GameMap map, Player player);
    public int getEvilness();

    }
