package org.CashCatalysts.CashCatalysts.Transactions;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TransactionHandler {
    private List<Transaction> transactions = new ArrayList<>();
    private int currentId = 1; // To generate unique transaction IDs

    public void addTransaction(String name, String type, Date date, int amount, int amountCents) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is missing");
        }
        Transaction transaction = new Transaction(currentId++, name, type, date, amount, amountCents);
        transactions.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public Transaction getTransactionById(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.transactionId() == id) {
                return transaction;
            }
        }
        return null;
    }
}
