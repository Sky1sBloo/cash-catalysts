package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.time.LocalDate;
import java.util.Map;

public class AnalysisController {
    private final UserStatsSystem userStatsSystem;

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
    private LineChart<String, Integer> yearly_activity_linechart;
    @FXML
    private LineChart<String, Integer> income_and_expenses_linechart;
    @FXML
    private LineChart<String, Integer> savings_linechart;
    @FXML
    private PieChart monthly_expense_breakdown_pie;
    @FXML
    private PieChart yearly_expense_breakdown_pie;

    public AnalysisController(UserStatsSystem userStatsSystem) {
        this.userStatsSystem = userStatsSystem;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        loadHighestCategory();
        loadComparisonToLastMonth();
        loadYearlyActivity();
        loadMonthlyExpenseBreakdown();
        loadYearlyExpenseBreakdown();
        loadAverageMonthlyExpenses();
        loadTotalYearlyExpenses();
        loadIncomeAndExpenseGraph();
        loadSavingsGraph();
    }

    private void loadHighestCategory() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        var highestExpenseCategory = userStatsSystem.getHighestExpenseCategory(
                startOfMonth,
                endOfMonth
        );
        if (highestExpenseCategory != null) {
            highest_category_monthly_lbl.setText(highestExpenseCategory.getKey());
        } else {
            highest_category_monthly_lbl.setText("No expense found");
        }

        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());

        highestExpenseCategory = userStatsSystem.getHighestExpenseCategory(
                startOfYear,
                endOfYear
        );
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

        var yearlyExpenseBreakdown = userStatsSystem.getMonthlyExpenseBreakdown(startOfYear, endOfYear);
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

        Map<String, Integer> categoryExpenses = userStatsSystem.getCategoryExpenses(startOfMonth, endOfMonth);
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

        Map<String, Integer> categoryExpenses = userStatsSystem.getCategoryExpenses(startOfMonth, endOfMonth);
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

        Currency averageMonthlyExpenses = userStatsSystem.getAverageMonthlyExpenses(startOfYear, endOfYear);
        avg_monthly_expenses_lbl.setText(String.valueOf(averageMonthlyExpenses));
    }

    private void loadTotalYearlyExpenses() {
        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());

        Currency totalYearlyExpenses = userStatsSystem.getTotalYearlyExpenses(startOfYear, endOfYear);
        total_yearly_expense_lbl.setText(String.valueOf(totalYearlyExpenses));
    }

    private void loadIncomeAndExpenseGraph() {
        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());

        Map<String, Currency> totalYearlyIncome = userStatsSystem.getMonthlyBudgetsBreakdown(startOfYear, endOfYear);
        Map<String, Currency> totalYearlyExpenses = userStatsSystem.getMonthlyExpenseBreakdown(startOfYear, endOfYear);

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

        Map<String, Currency> totalYearlyIncome = userStatsSystem.getMonthlyBudgetsBreakdown(startOfYear, endOfYear);
        Map<String, Currency> totalYearlyExpenses = userStatsSystem.getMonthlyExpenseBreakdown(startOfYear, endOfYear);

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
}
