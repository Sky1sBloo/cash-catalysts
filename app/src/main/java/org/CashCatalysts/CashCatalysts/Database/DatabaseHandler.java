package org.CashCatalysts.CashCatalysts.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to handle database connections and tables
 */
public class DatabaseHandler {
    private final Connection connection;

    /**
     * Path to database file
     * Note: Automatically adds the jdbc prefix
     */
    public DatabaseHandler(String pathToDb) throws SQLException {
        String url = "jdbc:sqlite:" + pathToDb;
        connection = DriverManager.getConnection(url);
    }

    /**
     * Performs a generic non returnable query
     * @param query
     * @throws SQLException
     */
    public void performQuery(String query) throws SQLException {
        connection.createStatement().execute(query);
    }
}
