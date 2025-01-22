package org.CashCatalysts.CashCatalysts.Transactions;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.TransactionsTable;

import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
    public static Transaction createTransaction(String name, String type, Date date, Currency amount) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is missing");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
        return new Transaction(null, name, type, date, amount);
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

    public List<Transaction> getAllTransactionsOn(FilterType filter) {
        LocalDate begin;
        LocalDate end;
        switch (filter) {
            case FilterType.DAY:
                begin = LocalDate.from(LocalDate.now().atStartOfDay());
                end = LocalDate.from(LocalDate.now().atTime(23, 59, 59));
                return getAllTransactionsBetween(Date.valueOf(begin), Date.valueOf(end));
            case FilterType.WEEK:
                begin = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                end = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                return getAllTransactionsBetween(Date.valueOf(begin), Date.valueOf(end));
            case FilterType.MONTH:
                begin = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
                end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
                return getAllTransactionsBetween(Date.valueOf(begin), Date.valueOf(end));
            case FilterType.YEAR:
                begin = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
                end = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
                return getAllTransactionsBetween(Date.valueOf(begin), Date.valueOf(end));
        }
        return List.of();
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
     */
    public void deleteTransaction(int id) {
        try {
            transactionsTable.deleteTransaction(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
