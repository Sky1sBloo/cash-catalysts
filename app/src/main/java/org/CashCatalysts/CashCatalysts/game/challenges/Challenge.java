package org.CashCatalysts.CashCatalysts.game.challenges;

import java.time.LocalDate;

public record Challenge(Integer id,
                        ChallengeCondition condition,
                        String name,
                        String description,
                        LocalDate startDate,
                        LocalDate endDate,
                        ChallengeReward reward,
                        boolean isCompleted) {
}
