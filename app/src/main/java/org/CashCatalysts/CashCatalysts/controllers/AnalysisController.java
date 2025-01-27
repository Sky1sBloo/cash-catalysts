package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.CashCatalysts.CashCatalysts.datatypes.DateFilterHandler;
import org.CashCatalysts.CashCatalysts.datatypes.DateFilterType;
import org.CashCatalysts.CashCatalysts.datatypes.DateRange;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AnalysisController {
    private final UserStatsSystem userStatsSystem;
    private final TransactionHandler transactionHandler;

    @FXML
    private Label highest_category_monthly_lbl;
    @FXML
    private Label highest_category_yearly_lbl;
    @FXML
    private Label comparison_to_last_month_lbl;
    @FXML
    private Label avg_monthly_expenses_lbl;
    @FXML
    private Label total_yearly_expense_lbl;
    @FXML
    private Label highest_transaction_lbl;
    @FXML
    private Label recurring_expenses_lbl;
    @FXML
    private LineChart<String, Integer> yearly_activity_linechart;
    @FXML
    private LineChart<String, Integer> income_and_expenses_linechart;
    @FXML
    private LineChart<String, Integer> savings_linechart;
    @FXML
    private PieChart monthly_expense_breakdown_pie;
    @FXML
    private PieChart yearly_expense_breakdown_pie;

    public AnalysisController(UserStatsSystem userStatsSystem, TransactionHandler transactionHandler) {
        this.userStatsSystem = userStatsSystem;
        this.transactionHandler = transactionHandler;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        loadHighestCategory();
        loadComparisonToLastMonth();
        loadHighestExpenseThisMonth();
        loadYearlyActivity();
        loadMonthlyExpenseBreakdown();
        loadYearlyExpenseBreakdown();
        loadAverageMonthlyExpenses();
        loadTotalYearlyExpenses();
        loadIncomeAndExpenseGraph();
        loadSavingsGraph();
        loadRecurringExpenses();
    }

    private void loadHighestCategory() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        DateRange dateRangeMonth = new DateRange(startOfMonth, endOfMonth);

        var highestExpenseCategory = userStatsSystem.getHighestExpenseCategory(dateRangeMonth);
        if (highestExpenseCategory != null) {
            highest_category_monthly_lbl.setText(highestExpenseCategory.getKey());
        } else {
            highest_category_monthly_lbl.setText("No expense found");
        }

        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());
        DateRange dateRangeYear = new DateRange(startOfYear, endOfYear);

        highestExpenseCategory = userStatsSystem.getHighestExpenseCategory(dateRangeYear);
        if (highestExpenseCategory != null) {
            highest_category_yearly_lbl.setText(highestExpenseCategory.getKey());
        } else {
            highest_category_yearly_lbl.setText("No expense found");
        }
    }

    private void loadComparisonToLastMonth() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        Currency lastMonthComparison = userStatsSystem.compareToLastMonth(startOfMonth);
        comparison_to_last_month_lbl.setText(String.valueOf(lastMonthComparison));
    }

    private void loadYearlyActivity() {
        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());
        DateRange dateRangeYear = new DateRange(startOfYear, endOfYear);

        var yearlyExpenseBreakdown = userStatsSystem.getMonthlyExpenseBreakdown(dateRangeYear);
        yearly_activity_linechart.getData().clear();

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Yearly Expenses");
        for (Map.Entry<String, Currency> entry : yearlyExpenseBreakdown.entrySet()) {
            series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue().getAmount()));
        }
        yearly_activity_linechart.getData().add(series);
    }

    private void loadMonthlyExpenseBreakdown() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        DateRange dateRangeMonth = new DateRange(startOfMonth, endOfMonth);

        Map<String, Integer> categoryExpenses = userStatsSystem.getCategoryExpenses(dateRangeMonth);
        monthly_expense_breakdown_pie.getData().clear();
        for (Map.Entry<String, Integer> entry : categoryExpenses.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            monthly_expense_breakdown_pie.getData().add(slice);
        }
    }

    private void loadYearlyExpenseBreakdown() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfYear(1);
        LocalDate endOfMonth = now.withDayOfYear(now.lengthOfYear());
        DateRange dateRangeMonth = new DateRange(startOfMonth, endOfMonth);

        Map<String, Integer> categoryExpenses = userStatsSystem.getCategoryExpenses(dateRangeMonth);
        yearly_expense_breakdown_pie.getData().clear();
        for (Map.Entry<String, Integer> entry : categoryExpenses.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            yearly_expense_breakdown_pie.getData().add(slice);
        }
    }

    private void loadAverageMonthlyExpenses() {
        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());
        DateRange dateRangeYear = new DateRange(startOfYear, endOfYear);

        Currency averageMonthlyExpenses = userStatsSystem.getAverageMonthlyExpenses(dateRangeYear);
        avg_monthly_expenses_lbl.setText(String.valueOf(averageMonthlyExpenses));
    }

    private void loadTotalYearlyExpenses() {
        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());
        DateRange dateRangeYear = new DateRange(startOfYear, endOfYear);

        Currency totalYearlyExpenses = userStatsSystem.getTotalYearlyExpenses(dateRangeYear);
        total_yearly_expense_lbl.setText(String.valueOf(totalYearlyExpenses));
    }

    private void loadIncomeAndExpenseGraph() {
        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());
        DateRange dateRangeYear = new DateRange(startOfYear, endOfYear);

        Map<String, Currency> totalYearlyIncome = userStatsSystem.getMonthlyBudgetsBreakdown(dateRangeYear);
        Map<String, Currency> totalYearlyExpenses = userStatsSystem.getMonthlyExpenseBreakdown(dateRangeYear);

        income_and_expenses_linechart.getData().clear();
        XYChart.Series<String, Integer> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        for (Map.Entry<String, Currency> entry : totalYearlyIncome.entrySet()) {
            incomeSeries.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue().getAmount()));
        }
        income_and_expenses_linechart.getData().add(incomeSeries);
        XYChart.Series<String, Integer> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Expenses");
        for (Map.Entry<String, Currency> entry : totalYearlyExpenses.entrySet()) {
            expenseSeries.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue().getAmount()));
        }
        income_and_expenses_linechart.getData().add(expenseSeries);
    }

    private void loadSavingsGraph() {
        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());
        DateRange dateRangeYear = new DateRange(startOfYear, endOfYear);

        Map<String, Currency> totalYearlyIncome = userStatsSystem.getMonthlyBudgetsBreakdown(dateRangeYear);
        Map<String, Currency> totalYearlyExpenses = userStatsSystem.getMonthlyExpenseBreakdown(dateRangeYear);

        savings_linechart.getData().clear();
        XYChart.Series<String, Integer> netSavingsSeries = new XYChart.Series<>();
        netSavingsSeries.setName("Net Savings");

        for (String key : totalYearlyIncome.keySet()) {
            int income = totalYearlyIncome.get(key).getAmount();
            int expense = totalYearlyExpenses.getOrDefault(key, new Currency(0)).getAmount();
            int netSavings = income - expense;
            netSavingsSeries.getData().add(new XYChart.Data<>(key, netSavings));
        }

        savings_linechart.getData().add(netSavingsSeries);
    }

    private void loadHighestExpenseThisMonth() {
        List<Transaction> transactionList = transactionHandler.getAllTransactionsOn(DateFilterType.MONTH);

        Transaction highestTransaction = null;
        for (Transaction transaction : transactionList) {
            if (highestTransaction == null || transaction.amount().getAmountCents() > highestTransaction.amount().getAmountCents()) {
                highestTransaction = transaction;
            }
        }
        highest_transaction_lbl.setText(transactionToAnalysisString(highestTransaction));
    }

    private void loadRecurringExpenses() {
        String recurringExpenseName = userStatsSystem.getRecurringExpenses(DateFilterHandler.getDateRangeFromFilterType(DateFilterType.ALL)).stream()
                .findFirst()
                .orElse(null);
        recurring_expenses_lbl.setText(Objects.requireNonNullElse(recurringExpenseName, "None"));
    }

    private static String transactionToAnalysisString(Transaction transaction) {
        return transaction != null ? transaction.type() + ": " + transaction.name() + " - " + transaction.amount() : null;
    }
}
