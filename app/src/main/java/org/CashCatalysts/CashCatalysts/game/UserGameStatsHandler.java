package org.CashCatalysts.CashCatalysts.game;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.UserGameStatsTable;

import java.sql.SQLException;

public class UserGameStatsHandler {
    private final UserGameStats userGameStats;
    private final UserGameStatsTable userGameStatsTable;

    public UserGameStatsHandler(int userId, DatabaseHandler databaseHandler) {
        this.userGameStatsTable = databaseHandler.getUserGameStatsTable();
        try {
            UserGameStats stats = userGameStatsTable.getGameInventory(userId);
            if (stats == null) {
                userGameStatsTable.addGameInventory(userId);
            }
            userGameStats = userGameStatsTable.getGameInventory(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserGameStats getUserGameStats() {
        return userGameStats;
    }

    public void updateUserGameStats() {
        try {
            userGameStatsTable.updateGameInventory(userGameStats);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
