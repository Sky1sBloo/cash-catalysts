package org.CashCatalysts.CashCatalysts.UserStats;

import org.CashCatalysts.CashCatalysts.Expenses.ExpenseHandler;

import java.time.Month;
import java.util.Map;

public class UserStatsSystem {
    private final ExpenseHandler expenseHandler; // Reference to ExpenseHandler

    // Constructor
    public UserStatsSystem(ExpenseHandler expenseHandler) {
        this.expenseHandler = expenseHandler;
    }

    // Display a breakdown of monthly expenses for a specific year
    public void displayMonthlyBreakdown(int year) {
        System.out.println(" Monthly Expenses Breakdown for " + year + " ");
        expenseHandler.getMonthlyExpenses(year).forEach(
            (month, amount) -> System.out.printf("%s: %.2f\n", month, amount)
        );
    }

    // Display a breakdown of yearly expenses
    public void displayYearlyBreakdown() {
        System.out.println(" Yearly Expenses Breakdown ");
        expenseHandler.getYearlyExpenses().forEach(
            (year, amount) -> System.out.printf("%d: %.2f\n", year, amount)
        );
    }

    // Display recurring expenses (items appearing more than once)
    public void displayRecurringExpenses() {
        System.out.println(" Recurring Expenses ");
        var recurringExpenses = expenseHandler.getRecurringExpenses();
        if (recurringExpenses.isEmpty()) {
            System.out.println("No recurring expenses found.");
        } else {
            recurringExpenses.forEach(expense -> System.out.println("- " + expense));
        }
    }

    // Display the highest expense category for a given period (monthly or overall)
    public void displayHighestExpenseCategory() {
        var highestCategory = expenseHandler.getHighestExpenseCategory();
        if (highestCategory != null) {
            System.out.println("===== Highest Expense Category =====");
            System.out.printf("%s: %.2f\n", highestCategory.getKey(), highestCategory.getValue());
        } else {
            System.out.println("No expenses available to determine the highest category.");
        }
    }

    // Compare the current month's expenses to the previous month's
    public void displayComparisonToLastMonth() {
        double difference = expenseHandler.compareToLastMonth();
        System.out.println(" Comparison to Last Month ");
        if (difference > 0) {
            System.out.printf("Expenses increased by %.2f compared to last month.\n", difference);
        } else if (difference < 0) {
            System.out.printf("Expenses decreased by %.2f compared to last month.\n", -difference);
        } else {
            System.out.println("Expenses are the same as last month.");
        }
    }
}
