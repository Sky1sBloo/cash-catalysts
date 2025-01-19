package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import org.CashCatalysts.CashCatalysts.GoalsSavings.Goal;
import org.CashCatalysts.CashCatalysts.GoalsSavings.GoalsHandler;

import java.io.IOException;
import java.util.List;

public class GoalsController {
    private final GoalsHandler goalsHandler;

    @FXML
    private ListView<String> goals_list;
    @FXML
    private Button add_goal_btn;
    @FXML
    private Button delete_goal_btn;
    @FXML
    private Button edit_goal_btn;

    public GoalsController(GoalsHandler goalsHandler) {
        this.goalsHandler = goalsHandler;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        add_goal_btn.setOnAction((ignore) -> addGoal());
        refresh();
    }

    public void refresh() {
        loadGoals();
    }

    private void loadGoals() {
        goals_list.getItems().clear();
        List<Goal> goals = goalsHandler.getAllGoals();
        for (Goal goal : goals) {
            goals_list.getItems().add(formatGoal(goal));
        }
    }

    private String formatGoal(Goal goal) {
        return goal.name() + "  -  " + goal.amount().getAmount() + "  -  " + goal.deadline();
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
