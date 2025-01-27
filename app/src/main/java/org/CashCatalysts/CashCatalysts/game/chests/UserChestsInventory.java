package org.CashCatalysts.CashCatalysts.game.chests;

/**
 * Class used for getting the amount of chests the user is having
 */
public record UserChestsInventory(int userId, int normalChestsAmount, int rareChestsAmount, int epicChestsAmount) {
}
