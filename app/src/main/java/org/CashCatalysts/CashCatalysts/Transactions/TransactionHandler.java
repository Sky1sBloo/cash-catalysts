package org.CashCatalysts.CashCatalysts.Transactions;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TransactionHandler {
    private List<Transaction> transactions = new ArrayList<>();
    private int currentId = 1; // To generate unique transaction IDs

    public void addTransaction(String expenseOrIncome, String name, String type, Date date, BigDecimal amount) {
        Transaction transaction = new Transaction(currentId++, expenseOrIncome, name, type, date, amount);
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
