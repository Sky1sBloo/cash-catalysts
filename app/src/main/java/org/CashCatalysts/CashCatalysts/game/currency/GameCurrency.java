package org.CashCatalysts.CashCatalysts.game.currency;

public class GameCurrency {
    private int amount;

    public GameCurrency() {
        this.amount = 0;
    }

    public GameCurrency(int amount) {
        this.amount = amount;
    }

    public void add(int amount) {
        this.amount += amount;
    }

    // Subtracts the amount of currency
    public void exchange(int amount) {
        this.amount -= amount;
    }

    public int getAmount() {
        return amount;
    }
}
