package org.CashCatalysts.CashCatalysts.game.challenges.weekly;

import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.game.challenges.Challenge;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeReward;

import java.time.LocalDate;

public class SavingsStreak extends Challenge {
    public SavingsStreak(LocalDate startDate, UserStatsSystem userStatsSystem) {
        super("Savings Streak", "Save atleast 500 across the week to unlock 3 star",
                new ChallengeReward(0, 3, 0, 0, 0, 0), startDate, startDate.plusWeeks(1), userStatsSystem);
    }

    @Override
    public boolean conditionMet() {
        return false;
    }
}
