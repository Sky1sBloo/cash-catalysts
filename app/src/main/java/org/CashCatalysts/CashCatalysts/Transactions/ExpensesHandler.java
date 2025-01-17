package org.CashCatalysts.CashCatalysts.Transactions;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.TransactionsTable;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Handles transaction-related operations: adding, retrieving, updating, and deleting transactions
 */
public class ExpensesHandler {
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
