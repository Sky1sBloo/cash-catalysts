package org.CashCatalysts.CashCatalysts.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to handle database connections
 */
public class DatabaseHandler {
    private final Connection connection;

    /**
     * Path to database
     * Automatically includes the path to the database
     */
    public DatabaseHandler(String pathToDb) throws SQLException {
        String url = "jdbc:sqlite:pathToDb" + pathToDb;
        connection = DriverManager.getConnection(url);
    }

    public void performQuery(String query) throws SQLException {
        connection.createStatement().execute(query);
    }
}
