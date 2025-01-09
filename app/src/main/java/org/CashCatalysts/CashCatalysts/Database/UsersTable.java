package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.UserProfile.User;

import java.sql.*;

public class UsersTable extends DbTable {
    public UsersTable(Connection connection) throws SQLException {
        super(connection);

        DbField[] fields = {
                new DbField("user_id", "INT", "PRIMARY KEY AUTO INCREMENT"),
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
}
