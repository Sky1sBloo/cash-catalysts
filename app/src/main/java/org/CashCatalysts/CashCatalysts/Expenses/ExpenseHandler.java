package org.CashCatalysts.CashCatalysts.Expenses;

import java.util.ArrayList;
import java.util.List;

public class ExpenseHandler {
    private List<Transaction> transactions = new ArrayList<>();
    private int currentId = 1; // To generate unique transaction IDs

    public void addTransaction(String expenseOrIncome, String name, String type, String date, double amount) {
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
