package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.GoalsSavings.Goals;

import java.sql.*;
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
                new DbField("targetAmount", "INTEGER", "NOT NULL"),
                new DbField("targetAmountCents", "INTEGER", "NOT NULL"),
                new DbField("deadline", "DATE", "NOT NULL"),
                new DbField("type", "VARCHAR(255)", "NOT NULL")
        };
        super.createTable("goals", fields);
    }

    /**
     * Retrieves all goals from the goals table
     */
    public List<Goals> getAllGoals() throws SQLException {
        List<Goals> goals = new ArrayList<>();
        String sql = "SELECT * FROM goals";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            goals.add(new Goals(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("targetAmount"),
                    rs.getInt("targetAmountCents"),
                    rs.getDate("deadline"),
                    rs.getString("type")
            ));
        }
        return goals;
    }

    /**
     * Retrieves goals of a specific type from the goals table
     */
    public List<Goals> getGoalsByType(String type) throws SQLException {
        List<Goals> goals = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE type = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, type);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            goals.add(new Goals(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("targetAmount"),
                    rs.getInt("targetAmountCents"),
                    rs.getDate("deadline"),
                    rs.getString("type")
            ));
        }
        return goals;
    }

    /**
     * Retrieves goals that are within a specific deadline range from the goals table
     */
    public List<Goals> getGoalsByDeadline(Date startDate, Date endDate) throws SQLException {
        List<Goals> goals = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE deadline BETWEEN ? AND ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            goals.add(new Goals(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("targetAmount"),
                    rs.getInt("targetAmountCents"),
                    rs.getDate("deadline"),
                    rs.getString("type")
            ));
        }
        return goals;
    }

    /**
     * Adds a goal
     */
    public void addGoal(Goals goal) throws SQLException {
        String sql = "INSERT INTO goals (name, targetAmount, targetAmountCents, deadline, type) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, goal.name());
        statement.setInt(2, goal.targetAmount());
        statement.setInt(3, goal.targetAmountCents());
        statement.setDate(4, goal.deadline());
        statement.setString(5, goal.type());
        statement.executeUpdate();
    }

    /**
     * Updates an existing goal
     */
    public void updateGoal(int id, Goals goal) throws SQLException {
        String sql = "UPDATE goals SET name = ?, targetAmount = ?, targetAmountCents = ?, deadline = ?, type = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, goal.name());
        statement.setInt(2, goal.targetAmount());
        statement.setInt(3, goal.targetAmountCents());
        statement.setDate(4, goal.deadline());
        statement.setString(5, goal.type());
        statement.setInt(6, id);
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
