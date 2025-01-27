package org.CashCatalysts.CashCatalysts.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import org.CashCatalysts.CashCatalysts.GoalsSavings.Goal;
import org.CashCatalysts.CashCatalysts.GoalsSavings.GoalsHandler;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.budgets.Budget;
import org.CashCatalysts.CashCatalysts.budgets.BudgetHandler;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.CashCatalysts.CashCatalysts.datatypes.DateFilterType;
import org.CashCatalysts.CashCatalysts.datatypes.DateRange;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class GoalsController {
    private final GoalsHandler goalsHandler;
    private final TransactionHandler transactionHandler;
    private final BudgetHandler budgetHandler;

    @FXML
    private ListView<Goal> goals_list;
    @FXML
    private ListView<Goal> goals_upcoming;
    @FXML
    private Button add_goal_btn;
    @FXML
    private Button delete_goal_btn;
    @FXML
    private Button edit_goal_btn;
    @FXML
    private TableView<LocalDate> calendar_week;
    @FXML
    private Label month_expense_lbl;
    @FXML
    private Label month_budget_lbl;
    @FXML
    private Label budget_remaining_lbl;

    @FXML
    private Label sun_lbl;
    @FXML
    private Label mon_lbl;
    @FXML
    private Label tues_lbl;
    @FXML
    private Label wed_lbl;
    @FXML
    private Label thurs_lbl;
    @FXML
    private Label fri_lbl;
    @FXML
    private Label sat_lbl;

    public GoalsController(GoalsHandler goalsHandler, TransactionHandler transactionHandler, BudgetHandler budgetHandler) {
        this.goalsHandler = goalsHandler;
        this.transactionHandler = transactionHandler;
        this.budgetHandler = budgetHandler;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        add_goal_btn.setOnAction((ignore) -> addGoal());
        edit_goal_btn.setOnAction((ignore) -> {
            int selectedIndex = goals_list.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                return;
            }
            Goal toEdit = goalsHandler.getAllGoals().get(selectedIndex);
            addGoal(toEdit);
        });

        delete_goal_btn.setOnAction((ignore) -> {
            Goal selectedGoal = goals_list.getSelectionModel().getSelectedItem();
            if (selectedGoal == null) {
                return;
            }
            goalsHandler.deleteGoal(selectedGoal.id());
            refresh();
        });
        refresh();
    }

    public void refresh() {
        loadGoals();
        loadUpcomingGoals();
        loadCalendar();
        loadBudgetOverview();
    }

    private void loadBudgetOverview() {
        int totalExpense = 0;
        for (Transaction transaction : transactionHandler.getAllTransactionsOn(DateFilterType.MONTH)) {
            totalExpense += transaction.amount().getAmountCents();
        }

        int totalBudget = 0;
        for (Budget budget : budgetHandler.getAllBudgets()) {
            totalBudget += budget.amount().getAmountCents();
        }
        month_expense_lbl.setText(new Currency(totalExpense).toString());
        month_budget_lbl.setText(new Currency(totalBudget).toString());
        budget_remaining_lbl.setText(new Currency(totalBudget - totalExpense).toString());
    }

    private void loadGoals() {
        goals_list.getItems().clear();
        goals_list.getItems().addAll(goalsHandler.getAllGoals());
    }

    private void loadUpcomingGoals() {
        goals_upcoming.getItems().clear();
        DateRange dateRangeThisWeek = new DateRange(
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );
        goals_upcoming.getItems().addAll(goalsHandler.getGoalsByDeadline(dateRangeThisWeek));
    }

    private void loadCalendar() {
        LocalDate now = LocalDate.now();
        LocalDate sunday = now.with(DayOfWeek.SUNDAY);
        sunday = sunday.minusWeeks(1);
        Integer[] days = new Integer[7];
        for (int i = 0; i < 7; i++) {
            days[i] = sunday.plusDays(i).getDayOfMonth();
        }

        sun_lbl.setText(days[0].toString());
        mon_lbl.setText(days[1].toString());
        tues_lbl.setText(days[2].toString());
        wed_lbl.setText(days[3].toString());
        thurs_lbl.setText(days[4].toString());
        fri_lbl.setText(days[5].toString());
        sat_lbl.setText(days[6].toString());
    }

    private void addGoal(Goal toEdit) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/GoalForm.fxml"));
        GoalsFormController goalsFormController;

        if (toEdit == null) {
            goalsFormController = new GoalsFormController();
        } else {
            goalsFormController = new GoalsFormController(toEdit);
        }
        loader.setController(goalsFormController);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dialog.initStyle(StageStyle.UTILITY);
        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType != ButtonType.OK) {
                return;
            }

            Goal newGoal = goalsFormController.getGoal();
            if (newGoal == null) {
                new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait();
                addGoal(toEdit);
                return;
            }

            if (goalsFormController.getGoalId() != null) {
                goalsHandler.updateGoal(goalsFormController.getGoalId(), newGoal);
            } else {
                goalsHandler.addGoal(newGoal);
            }
            refresh();
        });
    }

    private void addGoal() {
        addGoal(null);
    }
}
