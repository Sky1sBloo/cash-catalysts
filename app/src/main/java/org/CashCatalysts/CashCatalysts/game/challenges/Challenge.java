package org.CashCatalysts.CashCatalysts.game.challenges;

import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;

import java.time.LocalDate;

public abstract class Challenge {
    protected final String name;
    protected final String description;
    protected final LocalDate startDate;
    protected final LocalDate deadline;
    protected final UserStatsSystem userStatsSystem;
    protected final ChallengeReward reward;

    public Challenge(String name, String description, ChallengeReward reward, LocalDate startDate, LocalDate deadline, UserStatsSystem userStatsSystem) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.startDate = startDate;
        this.deadline = deadline;
        this.userStatsSystem = userStatsSystem;
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

    /**
     * Checks if the condition is met within the deadline
     */
    public abstract boolean conditionMet();
}
