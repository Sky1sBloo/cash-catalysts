package org.CashCatalysts.CashCatalysts.Transactions;


import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.Date;

public record Transaction(Integer transactionId, String name, String type, Date date, Currency amount) {

    @Override
    public String toString() {
        return "TransactionID: " + transactionId +
                ", Name: " + name + ", Type: " + type + ", Date: " + date + ", Amount: " + amount.getAmount() + "." + amount.getCents();
    }

    public int getAmount() {
        return amount;
    }

    public int getAmountCents() {
        return amountCents;
    }
}
