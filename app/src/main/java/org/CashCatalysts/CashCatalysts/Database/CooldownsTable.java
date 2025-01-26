package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.game.cooldown.Cooldown;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CooldownsTable extends DbTable {
    public CooldownsTable(Connection connection) throws SQLException {
        super(connection);

        DbField[] fields = {
            new DbField("id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"),
            new DbField("cooldown_end", "TEXT", "NOT NULL")
        };
        super.createTable("cooldowns", fields);
    }

    /**
     * Adds a new cooldown to the database
     * @param cooldown The cooldown to add
     * @return The id of the new cooldown
     */
    public int addCooldown(Cooldown cooldown) throws SQLException {
        String sql = "INSERT INTO cooldowns (cooldown_end) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cooldown.cooldownEnd().toString());
        statement.executeUpdate();
        return getLastRowId();
    }

    /**
     * Retrieves a cooldown from the database
     * @param id The id of the cooldown
     * @return The cooldown with the specified id
     */
    public Cooldown getCooldown(int id) throws SQLException {
        String sql = "SELECT * FROM cooldowns WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new Cooldown(
                    rs.getInt("id"),
                    LocalDateTime.parse(rs.getString("cooldown_end"))
            );
        }
        return null;
    }

    /**
     * Retrieves all cooldowns that are finished
     */
    public List<Cooldown> getAllFinishedCooldowns(LocalDateTime dateTime) throws SQLException {
        String sql = "SELECT * FROM cooldowns WHERE cooldown_end < ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dateTime.toString());

        ResultSet rs = statement.executeQuery();
        List<Cooldown> finishedCooldowns = new ArrayList<>();
        while (rs.next()) {
            finishedCooldowns.add(new Cooldown(
                    rs.getInt("id"),
                    LocalDateTime.parse(rs.getString("cooldown_end"))
            ));
        }
        return finishedCooldowns;
    }

    /**
     * Updates the end time of a cooldown
     * @param id The id of the cooldown
     * @param cooldownEnd The new end time of the cooldown
     */
    public void updateCooldown(int id, LocalDateTime cooldownEnd) throws SQLException {
        String sql = "UPDATE cooldowns SET cooldown_end = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cooldownEnd.toString());
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    /**
     * Deletes a cooldown from the database
     * @param id The id of the cooldown
     */
    public void deleteCooldown(int id) throws SQLException {
        String sql = "DELETE FROM cooldowns WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
