package org.CashCatalysts.CashCatalysts.game.challenges.daily;

import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.game.challenges.Challenge;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeReward;

import java.time.LocalDate;

public class CropSeller extends Challenge {
    public CropSeller(LocalDate startDate, UserStatsSystem userStatsSystem) {
        super("Crop Seller", "Sell Crops worth 50 gold coins in the market to earn 1 star",
                new ChallengeReward(0, 1, 0, 0, 0, 0), startDate, startDate.plusDays(1), userStatsSystem);
    }

    @Override
    public boolean conditionMet() {
        return false;
    }
}
