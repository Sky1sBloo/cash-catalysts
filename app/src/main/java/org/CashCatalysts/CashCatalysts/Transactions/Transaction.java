package org.CashCatalysts.CashCatalysts.Transactions;


import java.math.BigDecimal;
import java.sql.Date;

public record Transaction(int transactionId, String name, String type, Date date, int amount, int amountCents) {

    @Override
    public String toString() {
        return "TransactionID: " + transactionId +
                ", Name: " + name + ", Type: " + type + ", Date: " + date + ", Amount: " + amount;
    }
}
