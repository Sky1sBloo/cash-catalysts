package org.CashCatalysts.CashCatalysts.game.plants;

public record UserPlantsInventory(int userId, int banana, int pineapple, int strawberry, int apple, int sampaguita, int orchids, int sunflower, int rose) {
    public UserPlantsInventory {
        if (banana < 0 || pineapple < 0 || strawberry < 0 || apple < 0 || sampaguita < 0 || orchids < 0 || sunflower < 0 || rose < 0) {
            throw new IllegalArgumentException("Plants amount cannot be negative");
        }
    }
}
