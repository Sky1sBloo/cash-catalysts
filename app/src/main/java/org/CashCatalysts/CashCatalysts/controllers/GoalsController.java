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

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class GoalsController {
    private final GoalsHandler goalsHandler;

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

    public GoalsController(GoalsHandler goalsHandler) {
        this.goalsHandler = goalsHandler;
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
    }

    private void loadGoals() {
        goals_list.getItems().clear();
        goals_list.getItems().addAll(goalsHandler.getAllGoals());
    }

    private void loadUpcomingGoals() {
        goals_upcoming.getItems().clear();
        goals_upcoming.getItems().addAll(goalsHandler.getGoalsByDeadline(LocalDate.now(), LocalDate.now().plusDays(7)));
    }

    private void loadCalendar() {
        LocalDate now = LocalDate.now();
        LocalDate sunday = now.with(DayOfWeek.SUNDAY);
        ObservableList<LocalDate> days = FXCollections.observableArrayList(sunday);

        for (int i = 0; i < 7; i++) {
            @SuppressWarnings("unchecked")
            TableColumn<LocalDate, String> column = (TableColumn<LocalDate, String>) calendar_week.getColumns().get(i);
            int day = i;
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().plusDays(day).getDayOfMonth() + ""));
        }

        calendar_week.setItems(days);
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
