package org.CashCatalysts.CashCatalysts.GoalsSavings;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.GoalsTable;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Handles goal-related operations: retrieving and updating goals
 */
public class GoalsHandler {

    private final DatabaseHandler databaseHandler;

    /**
     * Initializes the GoalsHandler with a DatabaseHandler
     */
    public GoalsHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    /**
     * Retrieves all goals from the database
     */
    public List<Goals> getAllGoals() throws SQLException {
        GoalsTable goalsTable = databaseHandler.getGoalsTable();
        return goalsTable.getAllGoals();
    }

    /**
     * Retrieves goals of a specific type
     */
    public List<Goals> getGoalsByType(String type) throws SQLException {
        GoalsTable goalsTable = databaseHandler.getGoalsTable();
        return goalsTable.getGoalsByType(type);
    }

    /**
     * Retrieves goals within a specific deadline range
     */
    public List<Goals> getGoalsByDeadline(Date startDate, Date endDate) throws SQLException {
        GoalsTable goalsTable = databaseHandler.getGoalsTable();
        return goalsTable.getGoalsByDeadline(startDate, endDate);
    }

    /**
     * Checks if a goal has been reached based on the deposit amount
     */
    public boolean isGoalReached(Goals goal, int depositAmount, int depositAmountCents) {
        int totalAmountInCents = depositAmount * 100 + depositAmountCents;
        return totalAmountInCents >= goal.amount().getAmountCents();
    }

    /**
     * Checks if a goal has been reached by a specific date
     */
    public boolean isGoalReachedOnDate(Goals goal, Date goalDate) throws SQLException {
        // Fetch all deposits before the goal's deadline
        List<Transaction> transactions = databaseHandler.getTransactionsTable()
                .getAllTransactionsBetween(goal.deadline(), goalDate);

        int totalDepositCents = 0;
        for (Transaction transaction : transactions) {
            totalDepositCents += transaction.amount().getAmountCents();
        }

        int targetAmountInCents = goal.amount().getAmountCents();
        return totalDepositCents >= targetAmountInCents;
    }

    /**
     * Adds a new goal
     */
    public void addGoal(Goals goal) throws SQLException {
        GoalsTable goalsTable = databaseHandler.getGoalsTable();
        goalsTable.addGoal(goal);
    }

    /**
     * Updates an existing goal
     */
    public void updateGoal(int id, Goals goal) throws SQLException {
        GoalsTable goalsTable = databaseHandler.getGoalsTable();
        goalsTable.updateGoal(id, goal);
    }

    /**
     * Deletes a goal
     */
    public void deleteGoal(int id) throws SQLException {
        GoalsTable goalsTable = databaseHandler.getGoalsTable();
        goalsTable.deleteGoal(id);
    }
}
