package org.CashCatalysts.CashCatalysts.game.challenges.daily;

import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.game.challenges.Challenge;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeReward;

import java.time.LocalDate;

public class DailyHarvester extends Challenge {
    public DailyHarvester(LocalDate startDate, UserStatsSystem userStatsSystem) {
        super("Daily Harvester", "Harvest at least 5 crops today and earn 10 xp",
                new ChallengeReward(0, 0, 5, 0, 0, 0), startDate, startDate.plusDays(1), userStatsSystem);
    }

    @Override
    public boolean conditionMet() {
        return false;
    }
}
