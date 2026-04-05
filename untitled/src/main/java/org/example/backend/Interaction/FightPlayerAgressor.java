package org.example.backend.Interaction;
import org.example.backend.Entity.Entity;
import org.example.backend.Entity.Player;
import org.example.backend.Entity.Vampire;
import org.example.game.Game;
import java.util.ArrayList;
import java.util.Random;

public class FightPlayerAgressor {
    static Random rand = new Random();

    public static Entity playerAttacs(Player player, ArrayList<Entity> enemies, int[] kickPos, Game game){
        Entity enemy = null;
        for (Entity e : enemies){
            int[] playerPos = player.getCordXY();
            int kickX = playerPos[0] + kickPos[0];
            int kickY = playerPos[1] + kickPos[1];
            if(e != null && e.getCordXY()[0] == kickX && e.getCordXY()[1] == kickY) {
                enemy = e;
                break;
            }
        }
        if(enemy != null && isSuccessKick(player, enemy)){
            int damage = getDamage(enemy, player);
            confirmDamage(enemy, damage);
            if (game != null) {
                game.getMessageLog().addMessage(String.format("Игрок атаковал %s, нанес %d урона", 
                    enemy.getClass().getSimpleName(), damage));
            }
            return enemy;
        }
        return null;
    }

    private static int getDamage(Entity enemy, Entity attacker){
        int str = attacker.getStrength();
        int agi = enemy.getAgility();
        double damage = (str * str) / (double)(str + agi);
        damage += Math.random() * 2;
        return Math.max(1, (int)damage);
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
