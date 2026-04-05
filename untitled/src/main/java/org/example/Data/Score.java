package org.example.Data;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Game.Game;
import org.example.backend.Entity.Player;
import java.util.UUID;

public class Score {
    private int score;
    private UUID id;
    private String playerName;

    @JsonCreator
    public Score(
            @JsonProperty("score") int score,
            @JsonProperty("id") UUID id,
            @JsonProperty("playerName") String playerName
    ) {
        this.score = score;
        this.id = id;
        this.playerName = playerName;
    }

    public Score(Player player, Game game, UUID id, String name) {
        int health = player.getHealth();
        int maxHealth = player.getMaxHealth();
        int strength = player.getStrength();
        int agility = player.getAgility();
        int treasure = player.getTreasure();
        int level = game.getLevel();
        double healthFactor = (double) health / maxHealth;
        healthFactor = 0.5 + healthFactor;
        double combatPower = Math.pow(strength, 1.2) + Math.pow(agility, 1.1);
        double treasureScore = Math.pow(treasure, 1.15) * 5;
        double levelMultiplier = 1 + (level * 0.1);
        int weaponBonus = player.isWeaponInUse() ? 20 : 0;
        double stunPenalty = player.isStunned() ? 0.7 : 1.0;
        double scoreDouble = (combatPower + treasureScore + weaponBonus)
                * healthFactor
                * levelMultiplier
                * stunPenalty;
        this.score = (int) scoreDouble;
        this.id = id;
        this.playerName = name;
    }


    public UUID getID(){
        return this.id;
    }

    public int getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void reCalculateScore(Player player,  Game game) {
        int health = player.getHealth();
        int maxHealth = player.getMaxHealth();
        int strength = player.getStrength();
        int agility = player.getAgility();
        int treasure = player.getTreasure();
        int level = game.getLevel();
        double healthFactor = (double) health / maxHealth;
        healthFactor = 0.5 + healthFactor;
        double combatPower = Math.pow(strength, 1.2) + Math.pow(agility, 1.1);
        double treasureScore = Math.pow(treasure, 1.15) * 5;
        double levelMultiplier = 1 + (level * 0.1);
        int weaponBonus = player.isWeaponInUse() ? 20 : 0;
        double stunPenalty = player.isStunned() ? 0.7 : 1.0;
        double scoreDouble = (combatPower + treasureScore + weaponBonus)
                * healthFactor
                * levelMultiplier
                * stunPenalty;
        this.score = (int) scoreDouble;
    }
}
