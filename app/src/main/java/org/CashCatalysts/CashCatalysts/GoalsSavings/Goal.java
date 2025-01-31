package org.CashCatalysts.CashCatalysts.GoalsSavings;

import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.time.LocalDate;

public record Goal(Integer id, String name, Currency amount, LocalDate deadline, GoalsType type) {

    @Override
    public String toString() {
        return type + "  -  " + name + "  -  " + amount.getAmount() + "." + amount.getCents() + "  -  " + deadline;
    }
}