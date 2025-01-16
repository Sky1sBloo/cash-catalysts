package org.CashCatalysts.CashCatalysts.Expenses;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseHandler {
    private List<Transaction> transactions = new ArrayList<>();
    private int currentId = 1; // Unique ID generator for transactions
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Add a new transaction
    public void addTransaction(String expenseOrIncome, String name, String type, String date, double amount) {
        // Validate input
        if (!expenseOrIncome.equalsIgnoreCase("Income") && !expenseOrIncome.equalsIgnoreCase("Expense")) {
            throw new IllegalArgumentException("Transaction type must be 'Income' or 'Expense'.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER); // Ensure valid date format
        transactions.add(new Transaction(currentId++, expenseOrIncome, name, type, parsedDate.toString(), amount));
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions); // Return a copy to protect the internal list
    }

    // Get total income or expenses
    public double getTotal(String expenseOrIncome) {
        return transactions.stream()
                .filter(t -> t.expenseOrIncome().equalsIgnoreCase(expenseOrIncome))
                .mapToDouble(Transaction::amount)
                .sum();
    }

    // Get monthly expenses for a specific year
    public Map<Month, Double> getMonthlyExpenses(int year) {
        return transactions.stream()
                .filter(t -> t.expenseOrIncome().equalsIgnoreCase("Expense") &&
                             LocalDate.parse(t.date(), DATE_FORMATTER).getYear() == year)
                .collect(Collectors.groupingBy(
                        t -> LocalDate.parse(t.date(), DATE_FORMATTER).getMonth(),
                        Collectors.summingDouble(Transaction::amount)
                ));
    }

    // Get yearly expenses
    public Map<Integer, Double> getYearlyExpenses() {
        return transactions.stream()
                .filter(t -> t.expenseOrIncome().equalsIgnoreCase("Expense"))
                .collect(Collectors.groupingBy(
                        t -> LocalDate.parse(t.date(), DATE_FORMATTER).getYear(),
                        Collectors.summingDouble(Transaction::amount)
                ));
    }

    // Get the category with the highest expense
    public Map.Entry<String, Double> getHighestExpenseCategory() {
        return transactions.stream()
                .filter(t -> t.expenseOrIncome().equalsIgnoreCase("Expense"))
                .collect(Collectors.groupingBy(Transaction::type, Collectors.summingDouble(Transaction::amount)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null); // Return null if no expenses are found
    }

    // Get average monthly expenses for a given year
    public double getAverageMonthlyExpenses(int year) {
        Map<Month, Double> monthlyExpenses = getMonthlyExpenses(year);
        return monthlyExpenses.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0); // Default to 0 if no data
    }

    // Find recurring expenses
    public List<String> getRecurringExpenses() {
        return transactions.stream()
                .filter(t -> t.expenseOrIncome().equalsIgnoreCase("Expense"))
                .collect(Collectors.groupingBy(Transaction::name, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1) // Expenses occurring more than once
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Compare expenses for the current month to the previous month
    public double compareToLastMonth() {
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);

        double thisMonthExpenses = getTotalForMonth(now.getYear(), now.getMonth());
        double lastMonthExpenses = getTotalForMonth(lastMonth.getYear(), lastMonth.getMonth());

        return thisMonthExpenses - lastMonthExpenses;
    }

    // Helper method to get total expenses for a specific year and month
    private double getTotalForMonth(int year, Month month) {
        return transactions.stream()
                .filter(t -> t.expenseOrIncome().equalsIgnoreCase("Expense") &&
                             LocalDate.parse(t.date(), DATE_FORMATTER).getYear() == year &&
                             LocalDate.parse(t.date(), DATE_FORMATTER).getMonth() == month)
                .mapToDouble(Transaction::amount)
                .sum();
    }
}
