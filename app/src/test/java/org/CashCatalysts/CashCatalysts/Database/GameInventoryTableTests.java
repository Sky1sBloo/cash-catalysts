package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.UserProfile.UsersHandler;
import org.CashCatalysts.CashCatalysts.game.GameInventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GameInventoryTableTests {
    private GameInventoryTable gameInventoryTable;
    private UsersTable usersTable;
    private int userId;
    @BeforeEach
    public void loadDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        usersTable = new UsersTable(connection);
        userId = usersTable.registerUser(UsersHandler.createUser("testUser"));
        gameInventoryTable = new GameInventoryTable(connection, usersTable);
    }

    @Test
    public void testAddGameInventory() throws SQLException {
        gameInventoryTable.addGameInventory(userId);
        GameInventory gameInventory = gameInventoryTable.getGameInventory(userId);
        Assertions.assertEquals(0, gameInventory.getGold().getAmount());
        Assertions.assertEquals(0, gameInventory.getStar().getAmount());
        Assertions.assertEquals(0, gameInventory.getWater().getAmount());
    }

    @Test
    public void testUpdateGameInventory() throws SQLException {
        gameInventoryTable.addGameInventory(1);
        GameInventory gameInventory = new GameInventory(1, 100, 200, 300);
        gameInventoryTable.updateGameInventory(gameInventory);
        GameInventory updatedGameInventory = gameInventoryTable.getGameInventory(userId);
        Assertions.assertEquals(100, updatedGameInventory.getGold().getAmount());
        Assertions.assertEquals(200, updatedGameInventory.getStar().getAmount());
        Assertions.assertEquals(300, updatedGameInventory.getWater().getAmount());
    }

    @Test
    public void testNonExistentGameInventory() throws SQLException {
        GameInventory gameInventory = gameInventoryTable.getGameInventory(2);
        Assertions.assertNull(gameInventory);
    }

    @Test
    public void testMultipleGameInventory() throws SQLException {
        int userId2 = usersTable.registerUser(UsersHandler.createUser("testUser2"));
        gameInventoryTable.addGameInventory(userId);
        gameInventoryTable.updateGameInventory(new GameInventory(userId, 100, 200, 300));
        gameInventoryTable.addGameInventory(userId2);
        gameInventoryTable.updateGameInventory(new GameInventory(userId2, 205, 300, 500));
        GameInventory gameInventory = gameInventoryTable.getGameInventory(userId);
        Assertions.assertEquals(100, gameInventory.getGold().getAmount());
        Assertions.assertEquals(200, gameInventory.getStar().getAmount());
        Assertions.assertEquals(300, gameInventory.getWater().getAmount());

        GameInventory gameInventory2 = gameInventoryTable.getGameInventory(userId2);
        Assertions.assertEquals(205, gameInventory2.getGold().getAmount());
        Assertions.assertEquals(300, gameInventory2.getStar().getAmount());
        Assertions.assertEquals(500, gameInventory2.getWater().getAmount());
    }
}
