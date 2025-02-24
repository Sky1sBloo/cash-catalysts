package org.CashCatalysts.CashCatalysts.GoalsSavings;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class GoalHandlerTests {
    @Test
    public void getAllGoalsTest() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler(":memory:");
        GoalsHandler goalsHandler = new GoalsHandler(databaseHandler);
        List<Goal> goals = goalsHandler.getAllGoals();
        Assertions.assertEquals(0, goals.size());

        Goal goal = GoalsHandler.createGoal("Test Goal", new Currency(100, 10), LocalDate.of(2021, 12, 31), GoalsType.DEBT);
        int newId = goalsHandler.addGoal(goal);
        Goal expectedGoal = new Goal(newId, "Test Goal", new Currency(100, 10), LocalDate.of(2021, 12, 31), GoalsType.DEBT);
        goals = goalsHandler.getAllGoals();
        Assertions.assertEquals(1, goals.size());
        Assertions.assertEquals(expectedGoal, goals.getFirst());
    }

    @Test
    public void updateGoalsTest() throws SQLException {
         DatabaseHandler databaseHandler = new DatabaseHandler(":memory:");
        GoalsHandler goalsHandler = new GoalsHandler(databaseHandler);
        List<Goal> goals = goalsHandler.getAllGoals();
        Assertions.assertEquals(0, goals.size());

        Goal goal = GoalsHandler.createGoal("Test Goal", new Currency(100, 10), LocalDate.of(2021, 12, 31), GoalsType.CHARITY);
        Goal updatedGoal = GoalsHandler.createGoal("Test gallsllsls", new Currency(200, 10), LocalDate.of(2021, 5, 1), GoalsType.SAVINGS);
        int newId = goalsHandler.addGoal(goal);
        goalsHandler.updateGoal(newId, updatedGoal);
        Goal expectedGoal = new Goal(newId, "Test gallsllsls", new Currency(200, 10), LocalDate.of(2021, 5, 1), GoalsType.SAVINGS);
        goals = goalsHandler.getAllGoals();
        Assertions.assertEquals(1, goals.size());
        Assertions.assertEquals(expectedGoal, goals.getFirst());
    }
}
