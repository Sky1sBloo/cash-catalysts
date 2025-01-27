package org.CashCatalysts.CashCatalysts.Transactions;


import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.time.LocalDate;

public record Transaction(Integer transactionId, String name, String type, LocalDate date, Currency amount, Integer subscriptionId) {

    @Override
    public String toString() {
        return "TransactionID: " + transactionId +
                ", Name: " + name + ", Type: " + type + ", Date: " + date + ", Amount: " + amount.getAmount() + "." + amount.getCents() + " SubscriptionID: " + subscriptionId;
    }
}
