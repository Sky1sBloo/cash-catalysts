package org.CashCatalysts.CashCatalysts.budgets;

import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BudgetTests {
    public static List<Budget> createTestBudgets() {
        List<Budget> testBudgets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Budget testBudget = BudgetHandler.createBudget(LocalDate.now().plusMonths(i), new Currency(200 + i * 100, 0));
            testBudgets.add(testBudget);
        }
        return testBudgets;
    }
}
