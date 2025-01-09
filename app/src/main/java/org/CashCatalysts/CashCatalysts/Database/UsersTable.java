package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.UserProfile.User;
import org.CashCatalysts.CashCatalysts.UserProfile.UserProfileHandler;

import java.sql.*;

public class UsersTable extends DbTable {
    public UsersTable(Connection connection) throws SQLException {
        super(connection);

        DbField[] fields = {
                new DbField("user_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"),
                new DbField("username", "VARCHAR(255)", "NOT NULL"),
                new DbField("rank", "INT", "NOT NULL")
        };
        super.createTable("users", fields);
    }

    /**
     * Registers the user in the database
     *
     * @param user Only requires the username and the rank
     * @return The id of the user
     */
    public int registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, rank) VALUES(?, ?);";
        PreparedStatement insertStatement = connection.prepareStatement(sql);

        insertStatement.setString(1, user.username());
        insertStatement.setInt(2, user.rank());
        insertStatement.executeUpdate();

        Statement lastIdStatement = connection.createStatement();

        ResultSet rs = lastIdStatement.executeQuery("select last_insert_rowid();");
        if (rs.next()) {
            return rs.getInt(1);
        }

        throw new SQLException("Register get last user id ResultSet doesn't return anything");
    }

    /**
     * Gets the user from the database
     */
    public User getUser(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement getUserStatement = connection.prepareStatement(sql);

        getUserStatement.setInt(1, id);

        ResultSet rs = getUserStatement.executeQuery();
        if (rs.next()) {
            return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getInt("rank")
            );
        }
        throw new SQLException("Cannot find user or user id returns more than 1");
    }

    /**
     * Updates the user in the database based on id
     */
    public void updateUser(int id, User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, rank = ? WHERE user_id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(sql);

        updateStatement.setString(1, user.username());
        updateStatement.setInt(2, user.rank());
        updateStatement.setInt(3, id);
        updateStatement.executeUpdate();
    }
}
