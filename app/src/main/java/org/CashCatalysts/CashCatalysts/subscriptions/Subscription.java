package org.CashCatalysts.CashCatalysts.subscriptions;

import org.CashCatalysts.CashCatalysts.Transactions.TransactionType;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.time.LocalDate;

public record Subscription(Integer id, String name, TransactionType type, SubscriptionFrequency frequency, LocalDate startDate, LocalDate endDate, Currency amount) {
    @Override
    public String toString() {
        return "SubscriptionRecord{id=" + id + ", name='" + name + "', type='" + type + "', paymentTime='" + frequency+ "', startDate=" + startDate + ", endDate=" + endDate + ", " + amount.toString() + '}';
    }
}