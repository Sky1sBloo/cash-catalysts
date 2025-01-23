package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;

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

    public AnalysisController(UserStatsSystem userStatsSystem) {
        this.userStatsSystem = userStatsSystem;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        loadHighestCategory();
        loadComparisonToLastMonth();
        loadYearlyActivity();
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
        double lastMonthComparison = userStatsSystem.compareToLastMonth(Date.valueOf(startOfMonth));
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
        for (Map.Entry<String, Integer> entry : yearlyExpenseBreakdown.entrySet()) {
            series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
        }
        yearly_activity_linechart.getData().add(series);
    }
}
