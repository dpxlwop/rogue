package org.example.backend.Interaction;

import org.example.Game.Game;
import org.example.backend.Entity.*;

import java.util.Random;

public class FightEntityAgressor {
    static Random rand = new Random();

    public static boolean EntityAttacs(Player player, Entity enemy, Game game) {
        int[] playerPos = player.getCordXY();
        int damage = 0;
        if(isSuccessKick(enemy)){
            damage = getDamage(player, enemy);
            confirmDamage(player, damage);
            if (enemy instanceof Vampire vamp){
                confirmMaxHealthDamage(player, rand.nextInt(2));
            } else if( enemy instanceof MagicSnake mag){
                if (rand.nextDouble() < 0.2 && !player.isStunned()){
                    player.swapIsStunned();
                }
            }
            if (game != null) {
                game.getMessageLog().addMessage(String.format("%s атаковал игрока! Нанес %d урона",
                        enemy.getClass().getSimpleName(), damage));
            }
        }
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
            double attackChance = attacker.getAgility() / 10.0 + ((Enemy) attacker).getEvilness()/ 10.0 + 0.2;
            if (rand.nextDouble() < Math.min(attackChance, 1.0)) {
                if (attacker instanceof Ogre ogre){
                   if(ogre.getIsStunned()) {
                       ogre.swapIsStunned();
                       return false;
                   } else {
                       ogre.swapIsStunned();
                   }
                }
                return true;
            }
        }
        return false;
    }

    private static void confirmDamage(Entity entity, int damage){
        entity.setDamage(damage);
    }

    private static void confirmMaxHealthDamage(Entity entity, int damage){
        if (entity instanceof Player player) {
            player.damageMaxHealth(damage);
        }
    }
}
