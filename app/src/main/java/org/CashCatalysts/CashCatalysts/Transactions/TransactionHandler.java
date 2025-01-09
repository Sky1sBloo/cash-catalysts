package org.CashCatalysts.CashCatalysts.Transactions;

import java.sql.Date;

public class TransactionHandler {
    public Transaction createTransaction(String name, String type, Date date, int amount, int amountCents) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is missing");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
        return new Transaction(null, name, type, date, amount, amountCents);
    }
}
