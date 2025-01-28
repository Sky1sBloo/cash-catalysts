package org.CashCatalysts.CashCatalysts.game.challenges;

import org.CashCatalysts.CashCatalysts.game.currency.GameCurrency;

import java.time.LocalDate;

public abstract class Challenge {
    private final String name;
    private final String description;
    ChallengeReward reward;
    private final LocalDate deadline;

    public Challenge(String name, String description, ChallengeReward reward, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ChallengeReward getReward() {
        return reward;
    }

    public abstract boolean conditionMet();
}
