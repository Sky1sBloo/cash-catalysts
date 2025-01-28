package org.CashCatalysts.CashCatalysts.game.challenges.weekly;

import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.game.challenges.Challenge;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeReward;

import java.time.LocalDate;

public class GoldenHarvest extends Challenge {
    public GoldenHarvest(LocalDate startDate, UserStatsSystem userStatsSystem) {
        super("Golden Harvest", "Grow and sell crops worth 200 gold coins within 3 days to earn 5 stars",
                new ChallengeReward(0, 5, 0), startDate, startDate.plusDays(3), userStatsSystem);
    }

    @Override
    public boolean conditionMet() {
        return false;
    }
}
