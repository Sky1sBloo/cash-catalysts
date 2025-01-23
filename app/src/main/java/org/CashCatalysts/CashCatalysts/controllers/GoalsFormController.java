package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.CashCatalysts.CashCatalysts.GoalsSavings.Goal;
import org.CashCatalysts.CashCatalysts.GoalsSavings.GoalsType;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

public class GoalsFormController {
    @FXML
    private TextField goal_name;
    @FXML
    private ComboBox<GoalsType> type_selection;
    @FXML
    private TextField target_amount;
    @FXML
    private DatePicker deadline;

    private final Integer goalId;
    private final Goal goal;

    public GoalsFormController() {
        goalId = null;
        goal = null;
    }

    public GoalsFormController(Goal goal) {
        this.goalId = goal.id();
        this.goal = goal;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        type_selection.getItems().addAll(GoalsType.values());
        if (goalId != null) {
            goal_name.setText(goal.name());
            type_selection.setValue(goal.type());
            target_amount.setText(goal.amount().getAmount() + "." + goal.amount().getCents());
            deadline.setValue(goal.deadline());
        }
    }

    public Goal getGoal() {
        if (goal_name.getText().isEmpty()
                || target_amount.getText().isEmpty()
                || !target_amount.getText().matches("\\d+(\\.\\d+)?")) {
            return null;
        }
        if (type_selection.getValue() == null) {
            return null;
        }
        int amountCents = (int) (Double.parseDouble(target_amount.getText()) * 100);
        return new Goal(goalId, goal_name.getText(), new Currency(amountCents), deadline.getValue(), type_selection.getValue());
    }

    public Integer getGoalId() {
        return goalId;
    }
}
