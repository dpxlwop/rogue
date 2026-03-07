package org.example.backend.Entity;

import org.example.backend.MapGenerator.GameMap;

public interface Enemy {
    public int[] entityRandomWalk(GameMap map);
}
