package org.CashCatalysts.CashCatalysts.budgets;

import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.time.LocalDate;

public record Budget(Integer id, LocalDate date, Currency amount) {
    public Budget {
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount is null");
        }
    }

    @Override
    public String toString() {
        return String.format("%s  |  %s", date, amount);
    }
}
