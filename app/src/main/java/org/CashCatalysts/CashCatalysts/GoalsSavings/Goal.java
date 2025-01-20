package org.CashCatalysts.CashCatalysts.GoalsSavings;

import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.Date;

public record Goal(Integer id, String name, Currency amount, Date deadline, GoalsType type) {

    @Override
    public String toString() {
        return type + "  -  " + name + "  -  " + amount.getAmount() + "." + amount.getAmountCents() + "  -  " + deadline;
    }
}