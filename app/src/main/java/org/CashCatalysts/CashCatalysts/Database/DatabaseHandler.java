package org.CashCatalysts.CashCatalysts.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private Connection connection;

    /**
     * Path to database
     */
    public DatabaseHandler(String pathToDb) throws SQLException {
        String url = "jdbc:sqlite:pathToDb" + pathToDb;
        connection = DriverManager.getConnection(url);
    }
}
