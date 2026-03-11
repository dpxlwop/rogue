package org.example.backend.Interaction;

import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;
import org.example.backend.Entity.Vampire;

import java.util.ArrayList;
import java.util.Random;

public class FightPlayerAgressor {
    static Random rand = new Random();
    public static Entity playerAttacs(Player player, ArrayList<Entity> enemies, int[] kickPos){
        Entity enemy = null;
        for (Entity e : enemies){
            int[] playerPos = player.getCordXY();
            int kickX = playerPos[0] + kickPos[0];
            int kickY = playerPos[1] + kickPos[1];
            if(e != null && e.getCordXY()[0] == kickX && e.getCordXY()[1] == kickY) {
                enemy = e;
                System.out.println(String.format("enemy: %s, hp: %d", enemy.getClass().getSimpleName(), enemy.getHealth()));
                break;
            }
        }
        if(enemy != null && isSuccessKick(player, enemy)){
            System.out.println("successful attack");
            int damage = getDamage(enemy, player);
            confirmDamage(enemy, damage);
            return enemy;
        }
        return null;
    }

    private static int getDamage(Entity enemy, Entity attacker){
        int attackerStrength = attacker.getStrength();
        int enemyAgility = enemy.getAgility();
        int damage = 1 + (attackerStrength - enemyAgility - 2);
        return damage <= 0 ? 1 : damage;
    }

    private static boolean isSuccessKick(Entity attacker, Entity defender){
        if (defender instanceof Vampire vamp){
            if(vamp.isFirstKick()) {
                vamp.addKick();
                return false;
            }
        }
        double attackerAgility = attacker.getAgility() / 10.0;
        if (rand.nextDouble() < 0.5 + attackerAgility){
            return true;
        }
        return false;
    }

    private static void confirmDamage(Entity entity, int damage){
        entity.setDamage(damage);
    }
}
