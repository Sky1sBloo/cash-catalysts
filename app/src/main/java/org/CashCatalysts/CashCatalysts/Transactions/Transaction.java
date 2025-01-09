package org.CashCatalysts.CashCatalysts.Transactions;


import java.sql.Date;

public record Transaction(int transactionId, String expenseOrIncome, String name, String type, Date date, double amount) {

    @Override
    public String toString() {
        return "TransactionID: " + transactionId + ", Expense/Income: " + expenseOrIncome +
               ", Name: " + name + ", Type: " + type + ", Date: " + date + ", Amount: " + amount;
    }
}
