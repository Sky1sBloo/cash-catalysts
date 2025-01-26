package org.CashCatalysts.CashCatalysts.game;

import org.CashCatalysts.CashCatalysts.game.currency.GameCurrency;

public class GameInventory {
    private final int userId;
    private final GameCurrency gold;
    private final GameCurrency star;
    private final GameCurrency water;

    public GameInventory(int userId) {
        this.userId = userId;
        gold = new GameCurrency();
        star = new GameCurrency();
        water = new GameCurrency();
    }

    public GameInventory(int userId, int gold, int star, int water) {
        this.userId = userId;
        this.gold = new GameCurrency(gold);
        this.star = new GameCurrency(star);
        this.water = new GameCurrency(water);
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
}
