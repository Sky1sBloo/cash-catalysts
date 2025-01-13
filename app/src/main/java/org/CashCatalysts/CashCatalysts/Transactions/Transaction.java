package org.CashCatalysts.CashCatalysts.Transactions;


import java.sql.Date;

public record Transaction(Integer transactionId, String name, String type, Date date, int amount, int amountCents) {

    @Override
    public String toString() {
        return "TransactionID: " + transactionId +
                ", Name: " + name + ", Type: " + type + ", Date: " + date + ", Amount: " + amount + "." + amountCents;
    }
}
