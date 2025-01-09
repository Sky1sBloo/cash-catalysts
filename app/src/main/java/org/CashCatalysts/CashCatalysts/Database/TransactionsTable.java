package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.Transactions.Transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
                new DbField("transaction_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"),
                new DbField("name", "VARCHAR(255)"),
                new DbField("type", "VARCHAR(255)", "NOT NULL"),
                new DbField("date", "DATE", "NOT NULL"),
                new DbField("amount_cents", "INTEGER", "NOT NULL")
        };
        super.createTable("transactions", fields);
    }

    /**
     * Adds a new transaction to database
     * Note: Converts the transaction amount to cents
     */
    public int addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (name, type, date, amount_cents) VALUES(?, ?, ?, ?);";
        PreparedStatement addStatement = connection.prepareStatement(sql);

        addStatement.setString(1, transaction.name());
        addStatement.setString(2, transaction.type());
        addStatement.setDate(3, transaction.date());
        // Get decimal part
        int amountCents = transaction.amount() * 100 + transaction.amountCents();
        addStatement.setInt(4, amountCents);

        addStatement.executeUpdate();
        return getLastRowId();
    }
}
