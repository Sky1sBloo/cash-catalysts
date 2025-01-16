package org.CashCatalysts.CashCatalysts.Frontend;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.UsersTable;
import org.CashCatalysts.CashCatalysts.UserProfile.User;

import java.sql.SQLException;

/**
 * Handles user-related operations: registration, retrieval, updating, and deletion of users
 */
public class UsersHandler {
    private final UsersTable usersTable;

    /**
     * Constructs a UsersHandler with the DatabaseHandler
     *
     * @param dbHandler DatabaseHandler to interact with the database
     */
    public UsersHandler(DatabaseHandler dbHandler) {
        this.usersTable = dbHandler.getUsersTable();
    }

    /**
     * Registers a new user
     *
     * @param user the user to be registered
     * @return the ID of the newly registered user
     */
    public int registerUser(User user) {
        try {
            return usersTable.registerUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves a user by id
     *
     * @param id the id of the user to be retrieved
     * @return the user of the id retrieved
     */
    public User getUser(int id) {
        try {
            return usersTable.getUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates the details of the user
     *
     * @param id the id of the user to be updated
     * @param user the updated user information
     */
    public void updateUser(int id, User user) {
        try {
            usersTable.updateUser(id, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a user by id
     *
     * @param id the id of the user to be deleted
     */
    public void deleteUser(int id) {
        try {
            usersTable.deleteUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
