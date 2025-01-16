package org.CashCatalysts.CashCatalysts.Frontend;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.UsersTable;
import org.CashCatalysts.CashCatalysts.Database.TransactionsTable;
import org.CashCatalysts.CashCatalysts.UserProfile.User;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Handles user-related operations: registration, retrieval, updating, and deletion of users
 */
class UsersHandler {
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

/**
 * Handles transaction-related operations: adding, retrieving, updating, and deleting transactions
 */
class ExpensesHandler {
    private final TransactionsTable transactionsTable;

    /**
     * Constructs an ExpensesHandler with the DatabaseHandler
     *
     * @param dbHandler the DatabaseHandler to interact with the database
     */
    public ExpensesHandler(DatabaseHandler dbHandler) {
        this.transactionsTable = dbHandler.getTransactionsTable();
    }

    /**
     * Adds a new transaction
     *
     * @param transaction the transaction to add
     * @return the id of the newly added transaction
     */
    public int addTransaction(Transaction transaction) {
        try {
            return transactionsTable.addTransaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves a transaction by id
     *
     * @param id the id of the transaction to be retrieved
     * @return the transaction with the given id
     */
    public Transaction getTransaction(int id) {
        try {
            return transactionsTable.getTransaction(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all transactions between the specified dates
     *
     * @param start start date of the transaction range
     * @param end end date of the transaction range
     * @return the list of transactions within the specified date range
     */
    public List<Transaction> getAllTransactionsBetween(Date start, Date end) {
        try {
            return transactionsTable.getAllTransactionsBetween(start, end);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates the details of an existing transaction
     *
     * @param id the id of the transaction to be updates
     * @param transaction the updated transaction information
     */
    public void updateTransaction(int id, Transaction transaction) {
        try {
            transactionsTable.updateTransaction(id, transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a transaction by id
     *
     * @param id the id of the transaction to be deleted
     */
    public void deleteTransaction(int id) {
        try {
            transactionsTable.deleteTransaction(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
