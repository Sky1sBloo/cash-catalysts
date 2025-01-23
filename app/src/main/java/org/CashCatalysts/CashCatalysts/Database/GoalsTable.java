package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.GoalsSavings.Goal;
import org.CashCatalysts.CashCatalysts.GoalsSavings.GoalsType;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for interacting with the goals table in the database
 * Handles operations: retrieving, adding, updating, and deleting goals.
 */
public class GoalsTable extends DbTable {

    /**
     * Initializes a new GoalsTable instance with a database connection
     * Creates the goals table if it does not already exist
     */
    public GoalsTable(Connection connection) throws SQLException {
        super(connection);
        DbField[] fields = {
                new DbField("id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"),
                new DbField("name", "VARCHAR(255)", "NOT NULL"),
                new DbField("targetAmountCents", "INTEGER", "NOT NULL"),
                new DbField("deadline", "DATE", "NOT NULL"),
                new DbField("type", "VARCHAR(255)", "NOT NULL")
        };
        super.createTable("goals", fields);
    }

    public Goal getGoal(int id) throws SQLException {
        // Create code for this
        String sql = "SELECT * FROM goals WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new Goal(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new Currency(rs.getInt("targetAmountCents")),
                    rs.getDate("deadline").toLocalDate(),
                    GoalsType.valueOf(rs.getString("type"))
            );
        }
        return null;
    }

    /**
     * Retrieves all goals from the goals table
     */
    public List<Goal> getAllGoals() throws SQLException {
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT * FROM goals";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            goals.add(new Goal(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new Currency(rs.getInt("targetAmountCents")),
                    rs.getDate("deadline").toLocalDate(),
                    GoalsType.valueOf(rs.getString("type"))
            ));
        }
        return goals;
    }

    /**
     * Retrieves goals of a specific type from the goals table
     */
    public List<Goal> getGoalsByType(String type) throws SQLException {
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE type = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, type);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            goals.add(new Goal(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new Currency(rs.getInt("targetAmountCents")),
                    rs.getDate("deadline").toLocalDate(),
                    GoalsType.valueOf(rs.getString("type"))
            ));
        }
        return goals;
    }

    /**
     * Retrieves goals that are within a specific deadline range from the goals table
     */
    public List<Goal> getGoalsByDeadline(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE deadline BETWEEN ? AND ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(startDate));
        statement.setDate(2, Date.valueOf(endDate));
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            goals.add(new Goal(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new Currency(rs.getInt("targetAmountCents")),
                    rs.getDate("deadline").toLocalDate(),
                    GoalsType.valueOf(rs.getString("type"))
            ));
        }
        return goals;
    }

    /**
     * Adds a goal
     */
    public int addGoal(Goal goal) throws SQLException {
        String sql = "INSERT INTO goals (name, targetAmountCents, deadline, type) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, goal.name());
        statement.setInt(2, goal.amount().getAmountCents());
        statement.setDate(3, Date.valueOf(goal.deadline()));
        statement.setString(4, goal.type().toString());
        statement.executeUpdate();
        return getLastRowId();
    }

    /**
     * Updates an existing goal
     */
    public void updateGoal(int id, Goal goal) throws SQLException {
        String sql = "UPDATE goals SET name = ?, targetAmountCents = ?, deadline = ?, type = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, goal.name());
        statement.setInt(2, goal.amount().getAmountCents());
        statement.setDate(3, Date.valueOf(goal.deadline()));
        statement.setString(4, goal.type().toString());
        statement.setInt(5, id);
        statement.executeUpdate();
    }

    /**
     * Deletes a goal
     */
    public void deleteGoal(int id) throws SQLException {
        String sql = "DELETE FROM goals WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
