package org.CashCatalysts.CashCatalysts.game.challenges;

public enum ChallengeType {
    DAILY,
    WEEKLY;

    public static ChallengeType fromString(String type) {
        return switch (type) {
            case "DAILY" -> DAILY;
            case "WEEKLY" -> WEEKLY;
            default -> throw new IllegalArgumentException("Invalid challenge type: " + type);
        };
    }

    public static String toString(ChallengeType type) {
        return switch (type) {
            case DAILY -> "DAILY";
            case WEEKLY -> "WEEKLY";
            default -> throw new IllegalArgumentException("Invalid challenge type: " + type);
        };
    }
}
