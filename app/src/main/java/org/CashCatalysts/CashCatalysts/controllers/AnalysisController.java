package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

public class AnalysisController {
    private final UserStatsSystem userStatsSystem;

    @FXML
    private Label highest_category_monthly_lbl;
    @FXML
    private Label comparison_to_last_month_lbl;
    @FXML
    private LineChart<String, Integer> yearly_activity_linechart;
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
    }

    private void loadHighestCategory() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        var highestExpenseCategory = userStatsSystem.getHighestExpenseCategory(
                Date.valueOf(startOfMonth),
                Date.valueOf(endOfMonth)
        );
        if (highestExpenseCategory != null) {
            highest_category_monthly_lbl.setText(highestExpenseCategory.getKey());
        } else {
            highest_category_monthly_lbl.setText("No expense found");
        }
    }

    private void loadComparisonToLastMonth() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        Currency lastMonthComparison = userStatsSystem.compareToLastMonth(Date.valueOf(startOfMonth));
        comparison_to_last_month_lbl.setText(String.valueOf(lastMonthComparison));
    }

    private void loadYearlyActivity() {
        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());

        var yearlyExpenseBreakdown = userStatsSystem.getMonthlyExpenseBreakdown(
                Date.valueOf(startOfYear),
                Date.valueOf(endOfYear)
        );
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

        Map<String, Integer> categoryExpenses = userStatsSystem.getCategoryExpenses(
                Date.valueOf(startOfMonth),
                Date.valueOf(endOfMonth)
        );
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

        Map<String, Integer> categoryExpenses = userStatsSystem.getCategoryExpenses(
                Date.valueOf(startOfMonth),
                Date.valueOf(endOfMonth)
        );
        yearly_expense_breakdown_pie.getData().clear();
        for (Map.Entry<String, Integer> entry : categoryExpenses.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            yearly_expense_breakdown_pie.getData().add(slice);
        }
    }
}
