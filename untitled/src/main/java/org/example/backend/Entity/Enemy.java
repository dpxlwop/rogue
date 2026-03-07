package org.example.backend.Entity;

import org.example.backend.MapGenerator.GameMap;

public interface Enemy {
    public int[] enemyWalking(GameMap map, Player player);
    public int getEvilness();

    }
