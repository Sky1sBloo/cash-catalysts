package org.CashCatalysts.CashCatalysts.game.chests;

/**
 * Class used for getting the amount of chests the user is having
 */
public record UserChestsInventory(int normalChestsAmount, int rareChestsAmount, int epicChestsAmount) {
    public UserChestsInventory(int normalChestsAmount, int rareChestsAmount, int epicChestsAmount) {
        this.normalChestsAmount = normalChestsAmount;
        this.rareChestsAmount = rareChestsAmount;
        this.epicChestsAmount = epicChestsAmount;
    }
}
