package org.CashCatalysts.CashCatalysts.UserStats;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.budgets.Budget;
import org.CashCatalysts.CashCatalysts.budgets.BudgetHandler;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

public class UserStatsSystem {

    private final TransactionHandler transactionHandler;
    private final BudgetHandler budgetHandler;

    // Constructor to initialize the TransactionHandler
    public UserStatsSystem(TransactionHandler transactionHandler, BudgetHandler budgetHandler) {
        this.transactionHandler = transactionHandler;
        this.budgetHandler = budgetHandler;
    }

    // Helper method to extract year, month, and day from Date using LocalDate
    private String extractDateParams(Date date) {
        LocalDate localDate = date.toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        return String.format("Year: %d, Month: %d, Day: %d", year, month, day);
    }

    /**
     * Returns a sorted map of yearly expenses between the given start and end dates.
     * The map contains the total expenses for each year.
     */
    public Map<Integer, Currency> getYearlyExpenseBreakdown(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(startDate, endDate);
        return transactions.stream()
                .collect(Collectors.groupingBy(t -> {
                    LocalDate localDate = t.date();
                    return localDate.getYear(); // Group by year
                }, Collectors.collectingAndThen(
                        Collectors.summingInt((t) -> t.amount().getAmountCents()),
                        Currency::new
                )));
    }

    /**
     * Returns a sorted map of monthly expenses between the given start and end dates.
     * The map contains the total expenses for each month in the format "YYYY-MM".
     */
    public Map<String, Currency> getMonthlyExpenseBreakdown(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(startDate, endDate);
        Map<String, Currency> unsortedMap = transactions.stream()
                .collect(Collectors.groupingBy(t -> {
                            int month = t.date().getMonthValue();
                            return String.format("%d-%02d", t.date().getYear(), month); // Return as "YYYY-MM"
                        },
                        Collectors.collectingAndThen(
                                Collectors.summingInt((t) -> t.amount().getAmountCents()),
                                Currency::new
                        )));
        return unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Currency getAverageMonthlyExpenses(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(startDate, endDate);
        int totalAmount = transactions.stream()
                .mapToInt((t) -> t.amount().getAmountCents())
                .sum();
        int totalMonths = (int) transactions.stream()
                .collect(Collectors.groupingBy(t -> {
                            int month = t.date().getMonthValue();
                            return String.format("%d-%02d", t.date().getYear(), month); // Return as "YYYY-MM"
                        },
                        Collectors.counting()
                )).size();
        if (totalMonths == 0) {
            return new Currency(0);
        }
        return new Currency(totalAmount / totalMonths);
    }


    public Currency getTotalYearlyExpenses(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(startDate, endDate);
        return new Currency(transactions.stream()
                .mapToInt((t) -> t.amount().getAmountCents())
                .sum());
    }

    public Map<String, Integer> getCategoryExpenses(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(startDate, endDate);
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.summingInt((t) -> t.amount().getAmountCents())));
    }

    // Get the highest expense category for the month and year
    public Map.Entry<String, Integer> getHighestExpenseCategory(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(startDate, endDate);
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.summingInt((t) -> t.amount().getAmountCents())))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null); // Return null if no expense found
    }

    // Compare expenses for the current month to the previous month
    public Currency compareToLastMonth(LocalDate currentMonthDate) {
        int currentYear = currentMonthDate.getYear();
        int currentMonth = currentMonthDate.getMonthValue();

        // Get current month expenses
        Currency currentMonthExpenses = getTotalForMonth(currentYear, currentMonth);

        // Get previous month expenses
        LocalDate prevMonth = currentMonthDate.minusMonths(1); // Go back 1 month
        Currency lastMonthExpenses = getTotalForMonth(prevMonth.getYear(), prevMonth.getMonthValue());

        return new Currency(currentMonthExpenses.getAmountCents() - lastMonthExpenses.getAmountCents());
    }

    // Helper method to get total expenses for a specific month
    private Currency getTotalForMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(startDate, endDate);
        return new Currency(transactions.stream()
                .mapToInt((t) -> t.amount().getAmountCents())
                .sum());
    }

    // Get recurring expenses
    public List<String> getRecurringExpenses(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(startDate, endDate);
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::name, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1) // Expenses occurring more than once
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Returns a sorted map of monthly budgets between the given start and end dates.
     */
    public Map<String, Currency> getMonthlyBudgetsBreakdown(LocalDate startDate, LocalDate endDate) {
        List<Budget> budgets = budgetHandler.getAllBudgetsBetween(startDate, endDate);
        Map<String, Currency> unsortedMap = budgets.stream()
                .collect(Collectors.groupingBy(b -> {
                            int month = b.date().getMonthValue();
                            return String.format("%d-%02d", b.date().getYear(), month); // Return as "YYYY-MM"
                        },
                        Collectors.collectingAndThen(
                                Collectors.summingInt((b) -> b.amount().getAmountCents()),
                                Currency::new
                        )));
        return unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}