package org.example.backend.Interaction;

import org.example.backend.Entity.Enemy;
import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;

import java.util.Random;

public class FightEntityAgressor {
    static Random rand = new Random();

    public static boolean EntityAttacs(Player player, Entity enemy){
        int[] playerPos = player.getCordXY();
        System.out.print("AAAAAATTACK ");
        int damage = 0;
        if(isSuccessKick(enemy)){
            damage = getDamage(player, enemy);
            confirmDamage(player, damage);
        }
        System.out.println(damage);
        return damage != 0;
    }

    private static int getDamage(Player player, Entity attacker){
        int attackerStrength = attacker.getStrength();
        int playerAgility = player.getAgility();
        int damage = 1 + (attackerStrength - playerAgility - 2);
        return damage <= 0 ? 1 : damage;
    }

    private static boolean isSuccessKick(Entity attacker){
        if (attacker instanceof Enemy) {
            double attackChance = attacker.getAgility() + ((Enemy) attacker).getEvilness()/ 10.0 + 0.2;
            if (rand.nextDouble() < attackChance) {
                return true;
            }
        }
        return false;
    }

    private static void confirmDamage(Entity entity, int damage){
        entity.setDamage(damage);
    }
}
