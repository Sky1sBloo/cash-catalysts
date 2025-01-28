package org.CashCatalysts.CashCatalysts.game.challenges;

public enum ChallengeCondition {
    SAVE_AND_EARN,
    DAILY_HARVESTER,
    WATER_SAVER,
    CROP_SELLER,
    BUDGET_BOSS,
    SAVINGS_STREAK,
    GOLDEN_HARVEST;

    public int toInt() {
        return switch (this) {
            case SAVE_AND_EARN -> 0;
            case DAILY_HARVESTER -> 1;
            case WATER_SAVER -> 2;
            case CROP_SELLER -> 3;
            case BUDGET_BOSS -> 4;
            case SAVINGS_STREAK -> 5;
            case GOLDEN_HARVEST -> 6;
        };
    }

    public static ChallengeCondition fromInt(int i) {
        return switch (i) {
            case 0 -> SAVE_AND_EARN;
            case 1 -> DAILY_HARVESTER;
            case 2 -> WATER_SAVER;
            case 3 -> CROP_SELLER;
            case 4 -> BUDGET_BOSS;
            case 5 -> SAVINGS_STREAK;
            case 6 -> GOLDEN_HARVEST;
            default -> throw new IllegalArgumentException("Invalid challenge type");
        };
    }
}
