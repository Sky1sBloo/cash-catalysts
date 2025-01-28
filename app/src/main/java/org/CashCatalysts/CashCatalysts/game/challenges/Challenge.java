package org.CashCatalysts.CashCatalysts.game.challenges;

import org.CashCatalysts.CashCatalysts.game.currency.GameCurrency;

public abstract class Challenge {
    private final String name;
    private final String description;
    ChallengeReward reward;

    public Challenge(String name, String description, ChallengeReward reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract boolean conditionMet();
}
