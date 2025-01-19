package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.CashCatalysts.CashCatalysts.GoalsSavings.Goal;
import org.CashCatalysts.CashCatalysts.GoalsSavings.GoalsHandler;

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
        loadGoals();
    }

    private void loadGoals() {
        List<Goal> goals = goalsHandler.getAllGoals();
        for (Goal goal : goals) {
            goals_list.getItems().add(formatGoal(goal));
        }
    }

    private String formatGoal(Goal goal) {
        return goal.name() + " - " + goal.amount().getAmount() + " - " + goal.deadline();
    }
}
