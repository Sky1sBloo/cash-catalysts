package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.UserProfile.User;
import org.CashCatalysts.CashCatalysts.UserProfile.UserProfileHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UsersTableTests {
    @Test
    public void registerUserTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        UsersTable usersTable = new UsersTable(connection);

        User testUser = UserProfileHandler.createUser("testUser");
        User expectedUser = new User(1, "testUser", 1);

        int userId = usersTable.registerUser(testUser);
        User actualUser = usersTable.getUser(userId);
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void updateUserTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        UsersTable usersTable = new UsersTable(connection);

        User testUser = UserProfileHandler.createUser("testUser");
        User newUser = new User(1, "testUser", 4);

        int userId = usersTable.registerUser(testUser);
        usersTable.updateUser(userId, newUser);

        Assertions.assertEquals(newUser, usersTable.getUser(userId));
    }

    @Test
    public void deleteUserTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        UsersTable usersTable = new UsersTable(connection);

        User testUser = UserProfileHandler.createUser("testUser");
        int userId = usersTable.registerUser(testUser);

        usersTable.deleteUser(userId);
        Assertions.assertNull(usersTable.getUser(userId));
    }
}
