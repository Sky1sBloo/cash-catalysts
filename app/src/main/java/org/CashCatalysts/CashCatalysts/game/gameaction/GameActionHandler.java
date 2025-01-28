package org.CashCatalysts.CashCatalysts.game.gameaction;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.GameActionTable;

import java.time.LocalDate;
import java.util.List;

public class GameActionHandler {
    private final GameActionTable gameActionTable;
    private final int userId;

    public GameActionHandler(DatabaseHandler databaseHandler, int userId) {
        this.gameActionTable = databaseHandler.getGameActionTable();
        this.userId = userId;
    }

    public void addGameAction(GameAction action) {
        try {
            gameActionTable.addGameAction(action);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<GameAction> getGameActions() {
        try {
            return gameActionTable.getGameActions(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<GameAction> getGameActionsBetween(LocalDate startDate, LocalDate endDate) {
        try {
            return gameActionTable.getGameActionsBetween(userId, startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<GameAction> getGameActionsWithActionTypeBetween(GameActionType type, LocalDate startDate, LocalDate endDate) {
        try {
            return gameActionTable.getGameActionsWithActionTypeBetween(userId, type, startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
