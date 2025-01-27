package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.UserProfile.UsersHandler;
import org.CashCatalysts.CashCatalysts.game.UserGameStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserGameStatsTableTests {
    private UserGameStatsTable userGameStatsTable;
    private UsersTable usersTable;
    private int userId;
    @BeforeEach
    public void loadDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        usersTable = new UsersTable(connection);
        userId = usersTable.registerUser(UsersHandler.createUser("testUser"));
        userGameStatsTable = new UserGameStatsTable(connection, usersTable);
    }

    @Test
    public void testAddGameInventory() throws SQLException {
        userGameStatsTable.addGameInventory(userId);
        UserGameStats userGameStats = userGameStatsTable.getGameInventory(userId);
        Assertions.assertEquals(0, userGameStats.getGold().getAmount());
        Assertions.assertEquals(0, userGameStats.getStar().getAmount());
        Assertions.assertEquals(0, userGameStats.getWater().getAmount());
    }

    @Test
    public void testUpdateGameInventory() throws SQLException {
        userGameStatsTable.addGameInventory(1);
        UserGameStats userGameStats = new UserGameStats(1, 100, 200, 300);
        userGameStatsTable.updateGameInventory(userGameStats);
        UserGameStats updatedUserGameStats = userGameStatsTable.getGameInventory(userId);
        Assertions.assertEquals(100, updatedUserGameStats.getGold().getAmount());
        Assertions.assertEquals(200, updatedUserGameStats.getStar().getAmount());
        Assertions.assertEquals(300, updatedUserGameStats.getWater().getAmount());
    }

    @Test
    public void testNonExistentGameInventory() throws SQLException {
        UserGameStats userGameStats = userGameStatsTable.getGameInventory(2);
        Assertions.assertNull(userGameStats);
    }

    @Test
    public void testMultipleGameInventory() throws SQLException {
        int userId2 = usersTable.registerUser(UsersHandler.createUser("testUser2"));
        userGameStatsTable.addGameInventory(userId);
        userGameStatsTable.updateGameInventory(new UserGameStats(userId, 100, 200, 300));
        userGameStatsTable.addGameInventory(userId2);
        userGameStatsTable.updateGameInventory(new UserGameStats(userId2, 205, 300, 500));
        UserGameStats userGameStats = userGameStatsTable.getGameInventory(userId);
        Assertions.assertEquals(100, userGameStats.getGold().getAmount());
        Assertions.assertEquals(200, userGameStats.getStar().getAmount());
        Assertions.assertEquals(300, userGameStats.getWater().getAmount());

        UserGameStats userGameStats2 = userGameStatsTable.getGameInventory(userId2);
        Assertions.assertEquals(205, userGameStats2.getGold().getAmount());
        Assertions.assertEquals(300, userGameStats2.getStar().getAmount());
        Assertions.assertEquals(500, userGameStats2.getWater().getAmount());
    }
}
