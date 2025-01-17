package org.CashCatalysts.CashCatalysts.Transactions;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.TransactionsTable;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles transaction-related operations: adding, retrieving, updating, and deleting transactions
 */
public class TransactionHandler {
    private final TransactionsTable transactionsTable;

    /**
     * Creates a new transaction excluding the transaction id
     * Note: Generally used for creating a new transaction
     */
    public static Transaction createTransaction(String name, String type, Date date, int amount, int amountCents) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is missing");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
        return new Transaction(null, name, type, date, amount, amountCents);
    }

    /**
     * Constructs a TransactionHandler with the DatabaseHandler
     *
     * @param dbHandler the DatabaseHandler to interact with the database
     */
    public TransactionHandler(DatabaseHandler dbHandler) {
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
     * @param id the id of the transaction to be updated
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

    /**
     * Sorts a list of transactions by amount in ascending order.
     *
     * @param transactions the list of transactions to sort
     * @return the sorted list of transactions
     */
    public List<Transaction> sortTransactionsByAmountAscending(List<Transaction> transactions) {
        return transactions.stream()
                .sorted(Comparator.comparingDouble(t -> t.amount() + (t.amountCents() / 100.0)))
                .collect(Collectors.toList());
    }

    /**
     * Sorts a list of transactions by amount in descending order.
     *
     * @param transactions the list of transactions to sort
     * @return the sorted list of transactions
     */
    public List<Transaction> sortTransactionsByAmountDescending(List<Transaction> transactions) {
        return transactions.stream()
                .sorted(Comparator.comparingDouble((Transaction t) -> t.amount() + (t.amountCents() / 100.0)).reversed())
                .collect(Collectors.toList());
    }
}
