package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import org.CashCatalysts.CashCatalysts.GoalsSavings.Goal;
import org.CashCatalysts.CashCatalysts.GoalsSavings.GoalsHandler;
import org.CashCatalysts.CashCatalysts.GoalsSavings.GoalsType;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class GoalsController {
    private final GoalsHandler goalsHandler;

    @FXML
    private ListView<Goal> goals_list;
    @FXML
    private Button add_goal_btn;
    @FXML
    private Button delete_goal_btn;
    @FXML
    private Button edit_goal_btn;

    public GoalsController(GoalsHandler goalsHandler) {
        this.goalsHandler = goalsHandler;
        // Add some test goals
        goalsHandler.addGoal(GoalsHandler.createGoal("Test goal 1", new Currency(1000, 20), Date.valueOf("2021-10-21"), GoalsType.CHARITY));
        goalsHandler.addGoal(GoalsHandler.createGoal("Test goal 2", new Currency(2000, 0), Date.valueOf("2021-12-21"), GoalsType.SAVINGS));
        goalsHandler.addGoal(GoalsHandler.createGoal("Test goal 3", new Currency(3000, 0), Date.valueOf("2021-11-21"), GoalsType.INVESTMENT));

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
            int selectedIndex = goals_list.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                return;
            }
            goalsHandler.deleteGoal(goalsHandler.getAllGoals().get(selectedIndex).id());
            refresh();
        });
        refresh();
    }

    public void refresh() {
        loadGoals();
    }

    private void loadGoals() {
        goals_list.getItems().clear();
        goals_list.getItems().addAll(goalsHandler.getAllGoals());
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
