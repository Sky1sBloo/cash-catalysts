package org.CashCatalysts.CashCatalysts.UserProfile;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.UsersTable;
import org.CashCatalysts.CashCatalysts.game.GameInventory;

import java.sql.SQLException;

/**
 * Handles user-related operations: registration, retrieval, updating, and deletion of users
 */
public class UsersHandler {
    private final UsersTable usersTable;
    private GameInventory gameInventory;
    private User currentUser;

    /**
     * Constructs a UsersHandler with the DatabaseHandler
     *
     * @param dbHandler DatabaseHandler to interact with the database
     */
    public UsersHandler(DatabaseHandler dbHandler)
    {
        this.usersTable = dbHandler.getUsersTable();
        try {
            if (currentUser != null) {
                this.gameInventory = dbHandler.getGameInventoryTable().getGameInventory(currentUser.id());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create new user
     *
     * @param userID   identifier of user
     * @param username name of user
     * @param rank     rank of user; can't be < 1
     * @return a {@code User} object representing the created user
     */
    public static User createUser(int userID, String username, int rank) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Fill in username.");
        }
        if (rank < 1) {
            throw new IllegalArgumentException("Rank can't be negative.");
        }
        return new User(userID, username);
    }

    /**
     * Creates a new user with default rank
     * Note: Generally used for registering new users
     */
    public static User createUser(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Fill in username.");
        }
        return new User(null, username);
    }

    /**
     * Creates a new user with a specified username and rank.
     * Note: Typically used when updating a user's details.
     */
    public static User createUser(String username, int rank) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Fill in username.");
        }
        if (rank < 1) {
            throw new IllegalArgumentException("Rank can't be negative.");
        }
        return new User(null, username);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    public void login(User user) {
        this.currentUser = user;
    }

    public GameInventory getCurrentUserGameInventory() {
        return gameInventory;
    }
}
