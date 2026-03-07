package org.example;


import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Zombie;
import org.example.backend.GameTickExitCodes;
import org.example.ui.Drawer;
import org.example.backend.MapGenerator.GameMap;
import org.example.ui.KeyHandler;
import org.example.backend.Entity.Player;
import org.example.backend.GameTick;

import java.util.ArrayList;

import static org.example.backend.GameTick.gameTick;


public class Main {
    public static final int WIDTH = 120;
    public static final int MAP_HEIGHT = 40;
    public static final int SCREEN_HEIGHT = 60;

    public static void main(String[] args) throws Exception {
        Drawer drawer = new Drawer(WIDTH, MAP_HEIGHT, SCREEN_HEIGHT);
        KeyHandler keyHandler = new KeyHandler(drawer.getScreen());
        GameMap map = new GameMap(WIDTH, MAP_HEIGHT);
        Player player = new Player(new int[]{1, 1}, 100, 10, 10);
        map.spawnPlayer(player);
        ArrayList<Entity> Enemies = map.getEnemiesInRooms();
        while (true) {
            GameTickExitCodes exitCode = gameTick(drawer, keyHandler, map, player, Enemies);
        }
        //screen.stopScreen();
    }
}
