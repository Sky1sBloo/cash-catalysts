package org.CashCatalysts.CashCatalysts.game.challenges;

import org.CashCatalysts.CashCatalysts.game.currency.GameCurrency;

public record ChallengeReward(GameCurrency gold, GameCurrency star, int xp) {
}
