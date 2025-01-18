package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.budgets.Budget;
import org.CashCatalysts.CashCatalysts.budgets.BudgetHandler;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class BudgetsTableTests {
    @Test
    public void newBudgetTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        BudgetsTable budgetsTable = new BudgetsTable(connection);

        Budget testBudget = BudgetHandler.createBudget(Date.valueOf(LocalDate.now()), new Currency(200, 0));
        int testBudgetId = budgetsTable.addBudget(testBudget);
        Budget expectedBudget = new Budget(
                testBudgetId,
                Date.valueOf(LocalDate.now()), new Currency(200, 0));

        Assertions.assertEquals(expectedBudget, budgetsTable.getBudget(testBudgetId));
        Assertions.assertThrows(SQLException.class, () -> {
            budgetsTable.addBudget(testBudget);
        });
    }

    @Test
    public void updateBudgetTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        BudgetsTable budgetsTable = new BudgetsTable(connection);

        Budget testBudget = BudgetHandler.createBudget(Date.valueOf(LocalDate.now()), new Currency(200, 0));
        int testBudgetId = budgetsTable.addBudget(testBudget);
        budgetsTable.updateBudget(testBudget.date(), new Currency(500, 20));
        Budget expectedBudget = new Budget(
                testBudgetId,
                Date.valueOf(LocalDate.now()), new Currency(500, 20));

        Assertions.assertEquals(budgetsTable.getBudget(testBudgetId), expectedBudget);
    }
}
