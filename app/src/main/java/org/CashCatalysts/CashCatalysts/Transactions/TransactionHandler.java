package org.CashCatalysts.CashCatalysts.Transactions;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.TransactionsTable;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class TransactionHandler {
    private final TransactionsTable transactionsTable;

    public TransactionHandler(DatabaseHandler dbHandler) {
        this.transactionsTable = dbHandler.getTransactionsTable();
    }

    public static Transaction createTransaction(String name, String type, Date date, int amount, int amountCents) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is missing");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
        return new Transaction(null, name, type, date, amount, amountCents);
    }

    public int addTransaction(Transaction transaction) {
        try {
            return transactionsTable.addTransaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Transaction> getAllTransactions() {
        try {
            return transactionsTable.getAllTransactions();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Transaction> sortTransactionsByAmount(List<Transaction> transactions) {
        transactions.sort(Comparator.comparingDouble(t -> t.amount() + (t.amountCents() / 100.0)));
        return transactions;
    }

    public List<Transaction> sortTransactionsByAmountDescending(List<Transaction> transactions) {
        transactions.sort((t1, t2) -> Double.compare(
                t2.amount() + (t2.amountCents() / 100.0),
                t1.amount() + (t1.amountCents() / 100.0)));
        return transactions;
    }
}