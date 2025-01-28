package org.CashCatalysts.CashCatalysts.game.gameaction;

import java.time.LocalDate;

public record GameAction(Integer id, int userId, GameActionType type, Integer actionId, LocalDate actionDate) {
}
