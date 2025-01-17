package org.CashCatalysts.CashCatalysts.Users;

import org.CashCatalysts.CashCatalysts.Transactions.Transaction;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class UserStatsSystem {

    /**
     * Retrieves recurring transactions (occurs more than once)
     *
     * @param transactions List of all transactions
     * @return a list of recurring transaction names
     */
    public List<String> getRecurringTransactions(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.type().equalsIgnoreCase("Expense"))
                .collect(Collectors.groupingBy(Transaction::name, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the highest expense category for a specific year
     *
     * @param transactions List of all transactions
     * @param year         The year to check
     * @return the highest expense category with its total amount
     */
    public Map.Entry<String, Double> getHighestExpenseCategoryForYear(List<Transaction> transactions, int year) {
        return transactions.stream()
                .filter(t -> t.type().equalsIgnoreCase("Expense") &&
                        t.date().toLocalDate().getYear() == year)
                .collect(Collectors.groupingBy(Transaction::name, Collectors.summingDouble(t -> t.amount() + (t.amountCents() / 100.0))))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
    }

    /**
     * Retrieves monthly expenses for a specific year
     *
     * @param transactions List of all transactions
     * @param year         The year to check
     * @return a map of month numbers to total expenses
     */
    public Map<Integer, Double> getMonthlyExpenses(List<Transaction> transactions, int year) {
        return transactions.stream()
                .filter(t -> t.type().equalsIgnoreCase("Expense") &&
                        t.date().toLocalDate().getYear() == year)
                .collect(Collectors.groupingBy(t -> t.date().toLocalDate().getMonthValue(),
                        Collectors.summingDouble(t -> t.amount() + (t.amountCents() / 100.0))));
    }

    /**
     * Compares this month's expenses to last month's expenses
     *
     * @param transactions List of all transactions
     * @return the difference in expenses
     */
    public double compareToLastMonth(List<Transaction> transactions) {
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);

        double thisMonthExpenses = getTotalExpensesForMonth(transactions, now.getYear(), now.getMonthValue());
        double lastMonthExpenses = getTotalExpensesForMonth(transactions, lastMonth.getYear(), lastMonth.getMonthValue());

        return thisMonthExpenses - lastMonthExpenses;
    }

    /**
     * Retrieves total expenses for a specific month and year
     *
     * @param transactions List of all transactions
     * @param year         The year
     * @param month        The month
     * @return the total expenses
     */
    private double getTotalExpensesForMonth(List<Transaction> transactions, int year, int month) {
        return transactions.stream()
                .filter(t -> t.type().equalsIgnoreCase("Expense") &&
                        t.date().toLocalDate().getYear() == year &&
                        t.date().toLocalDate().getMonthValue() == month)
                .mapToDouble(t -> t.amount() + (t.amountCents() / 100.0))
                .sum();

    /**
     * Retrieves yearly expenses for a specific year.
     *
     * @param transactions List of all transactions
     * @param year         The year to check
     * @return the total expenses for the given year
     */
    public double getYearlyExpenses(List<Transaction> transactions, int year) {
        return transactions.stream()
                .filter(t -> t.type().equalsIgnoreCase("Expense") &&
                        t.date().toLocalDate().getYear() == year)
                .mapToDouble(t -> t.amount() + (t.amountCents() / 100.0))
                .sum();
    }

    /**
     * Retrieves yearly expenses for all years with their totals.
     *
     * @param transactions List of all transactions
     * @return a map of year to total expenses
     */
    public Map<Integer, Double> getYearlyExpensesForAllYears(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.type().equalsIgnoreCase("Expense"))
                .collect(Collectors.groupingBy(
                        t -> t.date().toLocalDate().getYear(),
                        Collectors.summingDouble(t -> t.amount() + (t.amountCents() / 100.0))
                ));
    }
}