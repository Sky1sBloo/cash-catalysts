package org.CashCatalysts.CashCatalysts.GoalsSavings;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.GoalsTable;
import org.CashCatalysts.CashCatalysts.Database.TransactionsTable;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Handles goal-related operations: retrieving and updating goals
 */
public class GoalsHandler {

    private final GoalsTable goalsTable;
    private final TransactionsTable transactionsTable;

    /**
     * Method for creating goal without id
     * Generally used for registration
     */
    public static Goal createGoal(String name, Currency currency, Date deadline, String type) {
        return new Goal(null, name, currency, deadline, type);
    }

    /**
     * Initializes the GoalsHandler with a DatabaseHandler
     */
    public GoalsHandler(DatabaseHandler databaseHandler) {
        this.goalsTable = databaseHandler.getGoalsTable();
        this.transactionsTable = databaseHandler.getTransactionsTable();
    }

    /**
     * Retrieves all goals from the database
     */
    public List<Goal> getAllGoals() throws SQLException {
        return goalsTable.getAllGoals();
    }

    /**
     * Retrieves goals of a specific type
     */
    public List<Goal> getGoalsByType(String type) throws SQLException {
        return goalsTable.getGoalsByType(type);
    }

    /**
     * Retrieves goals within a specific deadline range
     */
    public List<Goal> getGoalsByDeadline(Date startDate, Date endDate) throws SQLException {
        return goalsTable.getGoalsByDeadline(startDate, endDate);
    }

    /**
     * Checks if a goal has been reached based on the deposit amount
     */
    public boolean isGoalReached(Goal goal, Currency depositAmount) {
        return depositAmount.getAmountCents() >= goal.amount().getAmountCents();
    }

    /**
     * Checks if a goal has been reached by a specific date
     */
    public boolean isGoalReachedOnDate(Goal goal, Date goalDate) throws SQLException {
        // Fetch all deposits before the goal's deadline
        List<Transaction> transactions = transactionsTable.getAllTransactionsBetween(goal.deadline(), goalDate);

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
    public int addGoal(Goal goal) throws SQLException {
        return goalsTable.addGoal(goal);
    }

    /**
     * Updates an existing goal
     */
    public void updateGoal(int id, Goal goal) throws SQLException {
        goalsTable.updateGoal(id, goal);
    }

    /**
     * Deletes a goal
     */
    public void deleteGoal(int id) throws SQLException {
        goalsTable.deleteGoal(id);
    }
}
