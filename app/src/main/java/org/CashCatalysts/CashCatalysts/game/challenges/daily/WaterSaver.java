package org.CashCatalysts.CashCatalysts.game.challenges.daily;

import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.CashCatalysts.CashCatalysts.datatypes.DateRange;
import org.CashCatalysts.CashCatalysts.game.challenges.Challenge;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeReward;

import java.time.LocalDate;

public class WaterSaver extends Challenge {
    public WaterSaver(LocalDate startDate, UserStatsSystem userStatsSystem) {
        super(3, "Water Saver", "Use exactly 12 drops of water without exceeding the limit to earn 3 stars",
                new ChallengeReward(0, 3, 0, 0, 0, 0), startDate, startDate.plusDays(1), userStatsSystem);
    }

    @Override
    public boolean conditionMet() {
        Currency savings = userStatsSystem.getSavings(new DateRange(startDate, deadline));
        Currency expectedAmount = new Currency(50, 0);
        return savings.getAmountCents() > expectedAmount.getAmountCents();
    }
}
