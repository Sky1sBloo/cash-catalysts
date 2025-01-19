package org.CashCatalysts.CashCatalysts.GoalsSavings;

import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.Date;

public record Goal(Integer id, String name, Currency amount, Date deadline, String type) {

    @Override
    public String toString() {
        return "Goals[" +
                "ID: " + id + ", " +
                "Name: " + name + ", " +
                "TargetAmount: " + amount.getAmount() + ", " +
                "TargetAmountCents: " + amount.getAmountCents() + ", " +
                "Deadline: " + deadline + ", " +
                "Type: " + type + ']';
    }
}