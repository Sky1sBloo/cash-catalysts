package org.CashCatalysts.CashCatalysts.game.challenges.daily;

import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.CashCatalysts.CashCatalysts.datatypes.DateRange;
import org.CashCatalysts.CashCatalysts.game.challenges.Challenge;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeReward;

import java.time.LocalDate;

public class SaveAndEarn extends Challenge {
    public SaveAndEarn(LocalDate startDate, UserStatsSystem userStatsSystem) {
        super(2, "Save & Earn", "Save 50 today to earn 1 Ruby",
                new ChallengeReward(5, 0, 0, 0, 0, 0), startDate, startDate.plusDays(1), userStatsSystem);
    }

    @Override
    public boolean conditionMet() {
        Currency savings = userStatsSystem.getSavings(new DateRange(startDate, deadline));
        Currency expectedAmount = new Currency(50, 0);
        return savings.getAmountCents() > expectedAmount.getAmountCents();
    }
}
