package org.CashCatalysts.CashCatalysts.game.challenges;

import org.CashCatalysts.CashCatalysts.game.currency.GameCurrency;

import java.time.LocalDate;

public abstract class Challenge {
    protected final String name;
    protected final String description;
    protected ChallengeReward reward;
    protected final LocalDate startDate;
    protected final LocalDate deadline;

    public Challenge(String name, String description, ChallengeReward reward, LocalDate startDate, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.startDate = startDate;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public abstract boolean conditionMet();
}
