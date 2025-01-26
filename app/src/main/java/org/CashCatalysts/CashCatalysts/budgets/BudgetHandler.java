package org.CashCatalysts.CashCatalysts.budgets;

import org.CashCatalysts.CashCatalysts.Database.BudgetsTable;
import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.datatypes.DateFilterType;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.CashCatalysts.CashCatalysts.datatypes.DateFilterHandler;
import org.CashCatalysts.CashCatalysts.datatypes.DateRange;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BudgetHandler {
    private final BudgetsTable budgetsTable;

    public BudgetHandler(DatabaseHandler databaseHandler) {
        this.budgetsTable = databaseHandler.getBudgetsTable();
    }

    /**
     * Creates a new budget excluding the budget id
     */
    public static Budget createBudget(LocalDate date, Currency amount) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount is null");
        }
        return new Budget(null, date, amount);
    }

    public int addBudget(Budget newBudget) {
        try {
            return budgetsTable.addBudget(newBudget);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Budget getBudget(int id) {
        try {
            return budgetsTable.getBudget(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Budget getBudget(LocalDate date) {
        try {
            return budgetsTable.getBudget(date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Budget> getAllBudgetsBetween(DateRange dateRange) {
        try {
            return budgetsTable.getAllTransactionsBetween(dateRange.begin(), dateRange.end());
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Budget> getAllBudgets() {
        try {
            return budgetsTable.getAllBudgets();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Budget> getAllBudgetsOn(DateFilterType filter) {
        return getAllBudgetsBetween(DateFilterHandler.getDateRangeFromFilterType(filter));
    }

    public void updateBudget(LocalDate date, Currency amount) {
        try {
            budgetsTable.updateBudget(date, amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
