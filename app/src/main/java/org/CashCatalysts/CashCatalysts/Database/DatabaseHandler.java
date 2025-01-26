package org.CashCatalysts.CashCatalysts.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to handle database connections and tables
 */
public class DatabaseHandler {
    private final Connection connection;
    private final TransactionsTable transactionsTable;
    private final UsersTable usersTable;
    private final BudgetsTable budgetsTable;
    private final GoalsTable goalsTable;
    private final CooldownsTable cooldownsTable;
    private final GameInventoryTable gameInventoryTable;

    /**
     * Path to database file
     * Note: Automatically adds the jdbc prefix
     */
    public DatabaseHandler(String pathToDb) throws SQLException {
        String url = "jdbc:sqlite:" + pathToDb;
        connection = DriverManager.getConnection(url);
        this.transactionsTable = new TransactionsTable(connection);
        this.usersTable = new UsersTable(connection);
        this.budgetsTable = new BudgetsTable(connection);
        this.goalsTable = new GoalsTable(connection);
        this.cooldownsTable = new CooldownsTable(connection);
        this.gameInventoryTable = new GameInventoryTable(connection, usersTable);
    }

    /**
     * Performs a generic non returnable query
     */
    public void performQuery(String query) throws SQLException {
        connection.createStatement().execute(query);
    }

    /**
     * Returns the transactions table functions
     */
    public TransactionsTable getTransactionsTable() {
        return transactionsTable;
    }

    /**
     * Returns Users table functions
     */
    public UsersTable getUsersTable() {
        return usersTable;
    }

    /**
     * Returns the budgets table functions
     */
    public BudgetsTable getBudgetsTable() {
        return budgetsTable;
    }

    /**
     * Returns Goals table functions
     */
    public GoalsTable getGoalsTable() {
        return goalsTable;
    }

    /**
     * Returns Cooldowns table functions
     */
    public CooldownsTable getCooldownsTable() {
        return cooldownsTable;
    }

    /**
     * Returns GameInventory table functions
     */
    public GameInventoryTable getGameInventoryTable() {
        return gameInventoryTable;
    }
}
