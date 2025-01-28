package org.CashCatalysts.CashCatalysts.game;

import org.CashCatalysts.CashCatalysts.game.currency.GameCurrency;

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

    public UserGameStats(int userId) {
        this.userId = userId;
        gold = new GameCurrency();
        star = new GameCurrency();
        water = new GameCurrency();
        normalChests = new GameCurrency();
        rareChests = new GameCurrency();
        epicChests = new GameCurrency();
    }

    public UserGameStats(int userId, int gold, int star, int water, int normalChests, int rareChests, int epicChests) {
        this.userId = userId;
        this.gold = new GameCurrency(gold);
        this.star = new GameCurrency(star);
        this.water = new GameCurrency(water);
        this.normalChests = new GameCurrency(normalChests);
        this.rareChests = new GameCurrency(rareChests);
        this.epicChests = new GameCurrency(epicChests);
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
}
