package org.CashCatalysts.CashCatalysts.Transactions;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.TransactionsTable;

import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.CashCatalysts.CashCatalysts.datatypes.DateFilterType;
import org.CashCatalysts.CashCatalysts.datatypes.DateFilterHandler;
import org.CashCatalysts.CashCatalysts.datatypes.DateRange;

import java.sql.SQLException;
import java.time.LocalDate;
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
    public static Transaction createTransaction(String name, String type, LocalDate date, Currency amount) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is missing");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
        return new Transaction(null, name, type, date, amount, null);
    }

    /**
     * Creates a new transaction with subscription id
     */
    public static Transaction createTransaction(String name, String type, LocalDate date, Currency amount, int subscriptionId) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is missing");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
        return new Transaction(null, name, type, date, amount, subscriptionId);
    }

    /**
     * Constructs an ExpensesHandler with the DatabaseHandler
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
     * @return the list of transactions within the specified date
     */
    public List<Transaction> getAllTransactionsBetween(DateRange dateRange) {
        try {
            return transactionsTable.getAllTransactionsBetween(dateRange.begin(), dateRange.end());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all transactions sorted by amount from highest to lowest
     *
     * @return the list of transactions sorted in descending order by amount
     */
    public List<Transaction> getAllTransactionsSortedByAmountDesc() {
        try {
            return transactionsTable.getAllTransactions().stream()
                    .sorted(Comparator.comparing(Transaction::amount).reversed())
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Transaction> getAllTransactionsOn(DateFilterType filter) {
        return getAllTransactionsBetween(DateFilterHandler.getDateRangeFromFilterType(filter));
    }

    /**
     * Updates the details of an existing transaction
     *
     * @param id          the id of the transaction to be updates
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
     */
    public void deleteTransaction(int id) {
        try {
            transactionsTable.deleteTransaction(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
