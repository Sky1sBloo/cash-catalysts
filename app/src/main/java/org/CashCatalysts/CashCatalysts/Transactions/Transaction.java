package org.CashCatalysts.CashCatalysts.Transactions;

public record Transaction(int transactionId, String expenseOrIncome, String name, String type, String date, double amount) {

    @Override
    public String toString() {
        return "TransactionID: " + transactionId + ", Expense/Income: " + expenseOrIncome +
               ", Name: " + name + ", Type: " + type + ", Date: " + date + ", Amount: " + amount;
    }
}
