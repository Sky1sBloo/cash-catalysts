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
    private final UserGameStatsTable userGameStatsTable;
    private final SubscriptionsTable subscriptionsTable;
    private final LandsTable landsTable;
    private final PlantsInventoryTable plantsInventoryTable;
    private final ChestsInventoryTable chestsInventoryTable;
    private final SeedsInventoryTable seedsInventoryTable;
    private final ChallengesTable challengesTable;
    private final GameActionTable gameActionTable;

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
        this.subscriptionsTable = new SubscriptionsTable(connection);
        this.userGameStatsTable = new UserGameStatsTable(connection, usersTable);
        this.landsTable = new LandsTable(connection);
        this.plantsInventoryTable = new PlantsInventoryTable(connection);
        this.chestsInventoryTable = new ChestsInventoryTable(connection);
        this.seedsInventoryTable = new SeedsInventoryTable(connection);
        this.challengesTable = new ChallengesTable(connection);
        this.gameActionTable = new GameActionTable(connection);
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
     * Returns Subscriptions table functions
     */
    public SubscriptionsTable getSubscriptionsTable() {
        return subscriptionsTable;
    }

    /**
     * Returns GameInventory table functions
     */
    public UserGameStatsTable getUserGameStatsTable() {
        return userGameStatsTable;
    }

    /**
     * Returns Lands table functions
     */
    public LandsTable getLandsTable() {
        return landsTable;
    }

    /**
     * Returns PlantsInventory table functions
     */
    public PlantsInventoryTable getPlantsInventoryTable() {
        return plantsInventoryTable;
    }

    /**
     * Returns ChestsInventory table functions
     */
    public ChestsInventoryTable getChestsInventoryTable() {
        return chestsInventoryTable;
    }

    /**
     * Returns SeedsInventory table functions
     */
    public SeedsInventoryTable getSeedsInventoryTable() {
        return seedsInventoryTable;
    }

    /**
     * Returns Challenges table functions
     */
    public ChallengesTable getChallengesTable() {
        return challengesTable;
    }

    /**
     * Returns GameAction table functions
     */
    public GameActionTable getGameActionTable() {
        return gameActionTable;
    }
}
