package org.CashCatalysts.CashCatalysts.game;

import org.CashCatalysts.CashCatalysts.game.currency.GameCurrency;

import java.time.LocalDateTime;

/**
 * Class that represents the inventory of a user in the game.
 * Doesn't include chests
 */
public class UserGameStats {
    private final int userId;
    private final GameCurrency gold;
    private final GameCurrency star;
    private final GameCurrency water;
    private final GameCurrency normalChests;
    private final GameCurrency rareChests;
    private final GameCurrency epicChests;
    private final GameCurrency pots;
    private Integer waterCooldownId;

    public UserGameStats(int userId) {
        this.userId = userId;
        gold = new GameCurrency();
        star = new GameCurrency();
        water = new GameCurrency();
        normalChests = new GameCurrency();
        rareChests = new GameCurrency();
        epicChests = new GameCurrency();
        pots = new GameCurrency();
    }

    public UserGameStats(int userId, int gold, int star, int water, int normalChests, int rareChests, int epicChests, int pots, Integer waterCooldownId) {
        this.userId = userId;
        this.gold = new GameCurrency(gold);
        this.star = new GameCurrency(star);
        this.water = new GameCurrency(water);
        this.normalChests = new GameCurrency(normalChests);
        this.rareChests = new GameCurrency(rareChests);
        this.epicChests = new GameCurrency(epicChests);
        this.pots = new GameCurrency(pots);
        this.waterCooldownId = waterCooldownId;
    }

    public int getUserId() {
        return userId;
    }

    public GameCurrency getGold() {
        return gold;
    }

    public GameCurrency getStar() {
        return star;
    }

    public GameCurrency getWater() {
        return water;
    }

    public GameCurrency getNormalChests() {
        return normalChests;
    }

    public GameCurrency getRareChests() {
        return rareChests;
    }

    public GameCurrency getEpicChests() {
        return epicChests;
    }

    public GameCurrency getPots() {
        return pots;
    }

    public Integer getWaterCooldownId() {
        return waterCooldownId;
    }

    public void setWaterCooldownId(int waterCooldownId) {
        this.waterCooldownId = waterCooldownId;
    }
}
