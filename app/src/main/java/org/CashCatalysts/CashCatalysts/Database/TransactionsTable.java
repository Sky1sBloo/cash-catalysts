package org.CashCatalysts.CashCatalysts.Database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for managing the transactions table
 */
public class TransactionsTable extends DbTable {
    /**
     * Constructs the transactions table
     * <p>
     * Note: Amount field is calculated in 2 decimal cents since SQLite does not support currency datatypes
     * </p>
     */
    public TransactionsTable(Connection connection) throws SQLException {
        super(connection);
        DbField[] fields = {
                new DbField("transaction_id", "INT", "PRIMARY KEY"),
                new DbField("name", "VARCHAR(255)"),
                new DbField("type", "VARCHAR(255)", "NOT NULL"),
                new DbField("date", "DATE", "NOT NULL"),
                new DbField("amount_cents", "INTEGER", "NOT NULL")
        };
        super.createTable("transactions", fields);
    }

    // Todo: Will accept a record representing a transaction
    public void addTransaction() {
    }
}
