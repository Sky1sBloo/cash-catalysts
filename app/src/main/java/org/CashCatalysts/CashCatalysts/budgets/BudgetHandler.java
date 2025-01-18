package org.CashCatalysts.CashCatalysts.budgets;

import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.Date;

public class BudgetHandler {
    /**
     * Creates a new budget excluding the budget id
     */
    public static Budget createBudget(Date date, Currency amount) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount is null");
        }
        return new Budget(null, date, amount);
    }
}
