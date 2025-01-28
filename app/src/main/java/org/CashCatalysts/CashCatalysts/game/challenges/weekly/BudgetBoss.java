package org.CashCatalysts.CashCatalysts.game.challenges.weekly;

import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.game.challenges.Challenge;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeReward;

import java.time.LocalDate;

public class BudgetBoss extends Challenge {
    public BudgetBoss(LocalDate startDate, UserStatsSystem userStatsSystem) {
        super(4, "Budget boss", "Stay within your budget for 1 week to receive 1 rare chest",
                new ChallengeReward(0, 0, 0, 0, 1, 0), startDate, startDate.plusWeeks(3), userStatsSystem);
    }

    @Override
    public boolean conditionMet() {
        return false;
    }
}
