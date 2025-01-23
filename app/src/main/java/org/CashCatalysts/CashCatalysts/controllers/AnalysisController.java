package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;

import java.sql.Date;
import java.time.LocalDate;

public class AnalysisController {
    private final UserStatsSystem userStatsSystem;

    @FXML
    private Label highest_category_monthly_lbl;
    @FXML
    private Label comparison_to_last_month_lbl;

    public AnalysisController(UserStatsSystem userStatsSystem) {
        this.userStatsSystem = userStatsSystem;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        loadHighestCategory();
        loadComparisonToLastMonth();
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
}
