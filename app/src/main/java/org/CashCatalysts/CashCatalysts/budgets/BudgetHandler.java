package org.CashCatalysts.CashCatalysts.budgets;

import org.CashCatalysts.CashCatalysts.Database.BudgetsTable;
import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Transactions.FilterType;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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

    public List<Budget> getAllBudgetsBetween(LocalDate start, LocalDate end) {
        try {
            return budgetsTable.getAllTransactionsBetween(start, end);
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

    public List<Budget> getAllBudgetsOn(FilterType filter) {
        LocalDate begin;
        LocalDate end;
        switch (filter) {
            case FilterType.DAY:
                begin = LocalDate.from(LocalDate.now().atStartOfDay());
                end = LocalDate.from(LocalDate.now().atTime(23, 59, 59));
                return getAllBudgetsBetween(begin, end);
            case FilterType.WEEK:
                begin = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                end = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                return getAllBudgetsBetween(begin, end);
            case FilterType.MONTH:
                begin = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
                end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
                return getAllBudgetsBetween(begin, end);
            case FilterType.YEAR:
                begin = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
                end = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
                return getAllBudgetsBetween(begin, end);
        }
        return List.of();
    }

    public void updateBudget(LocalDate date, Currency amount) {
        try {
            budgetsTable.updateBudget(date, amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
