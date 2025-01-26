package org.CashCatalysts.CashCatalysts.UserStats;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.datatypes.DateRange;

public class UserStatsSystem {

    private final TransactionHandler transactionHandler;

    // Constructor to initialize the TransactionHandler
    public UserStatsSystem(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }

    // Helper method to extract year, month, and day from Date using LocalDate
    private String extractDateParams(Date date) {
        LocalDate localDate = date.toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        return String.format("Year: %d, Month: %d, Day: %d", year, month, day);
    }

    // Get yearly expense breakdown
    public Map<Integer, Integer> getYearlyExpenseBreakdown(DateRange dateRange) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(dateRange);
        return transactions.stream()
                .collect(Collectors.groupingBy(t -> {
                    return t.date().getYear(); // Group by year
                }, Collectors.summingInt((t) -> t.amount().getAmountCents())));
    }

    // Get monthly expense breakdown
    public Map<String, Integer> getMonthlyExpenseBreakdown(DateRange dateRange) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(dateRange);
        return transactions.stream()
                .collect(Collectors.groupingBy(t -> {
                    int month = t.date().getMonthValue();
                    return String.format("%d-%02d", t.date().getYear(), month); // Return as "YYYY-MM"
                }, Collectors.summingInt((t) -> t.amount().getAmountCents())));
    }

    // Get the highest expense category for the month and year
    public Map.Entry<String, Integer> getHighestExpenseCategory(DateRange dateRange) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(dateRange);
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.summingInt((t) -> t.amount().getAmountCents())))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null); // Return null if no expense found
    }

    // Compare expenses for the current month to the previous month
    public double compareToLastMonth(LocalDate currentMonthDate) {
        int currentYear = currentMonthDate.getYear();
        int currentMonth = currentMonthDate.getMonthValue();

        // Get current month expenses
        double currentMonthExpenses = getTotalForMonth(currentYear, currentMonth);

        // Get previous month expenses
        currentMonthDate = currentMonthDate.minusMonths(1); // Go back 1 month
        double lastMonthExpenses = getTotalForMonth(currentMonthDate.getYear(), currentMonthDate.getMonthValue());

        return currentMonthExpenses - lastMonthExpenses;
    }

    // Helper method to get total expenses for a specific month
    private double getTotalForMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(new DateRange(startDate, endDate));
        return transactions.stream()
                .mapToInt(t -> t.amount().getAmountCents())
                .sum();
    }

    // Get recurring expenses
    public List<String> getRecurringExpenses(DateRange dateRange) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsBetween(dateRange);
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::name, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1) // Expenses occurring more than once
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}