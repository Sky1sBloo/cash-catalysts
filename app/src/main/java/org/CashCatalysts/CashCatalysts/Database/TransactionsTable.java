package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.CashCatalysts.CashCatalysts.subscriptions.Subscription;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                new DbField("amount_cents", "INTEGER", "NOT NULL"),
                new DbField("subscription_id", "INTEGER")
        };

        String[] constraints = {
                "FOREIGN KEY (subscription_id) REFERENCES subscriptions(id) ON DELETE SET NULL"
        };
        super.createTable("transactions", fields, constraints);
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
        addStatement.setDate(3, Date.valueOf(transaction.date()));
        // Get decimal part
        addStatement.setInt(4, transaction.amount().getAmountCents());

        addStatement.executeUpdate();
        return getLastRowId();
    }

    /**
     * Gets the transaction by id
     * Note: Ignores the following transactions if more than 1 returned from db
     *
     * @return null if not found
     */
    public Transaction getTransaction(int id) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        getStatement.setInt(1, id);
        ResultSet rs = getStatement.executeQuery();

        if (rs.next()) {
            Integer subscriptionId = rs.getInt("subscription_id");
            if (rs.wasNull()) {
                subscriptionId = null;
            }
            return new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDate("date").toLocalDate(),
                    new Currency(rs.getInt("amount_cents")),
                    subscriptionId
            );
        }
        return null;
    }

    /**
     * Returns the transactions between dates
     */
    public List<Transaction> getAllTransactionsBetween(LocalDate start, LocalDate end) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE date BETWEEN ? and ? ORDER BY date ASC;";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        getStatement.setDate(1, Date.valueOf(start));
        getStatement.setDate(2, Date.valueOf(end));

        ResultSet rs = getStatement.executeQuery();
        while (rs.next()) {
            Integer subscriptionId = rs.getInt("subscription_id");
            if (rs.wasNull()) {
                subscriptionId = null;
            }
            transactions.add(new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDate("date").toLocalDate(),
                    new Currency(rs.getInt("amount_cents")),
                    subscriptionId
            ));
        }
        return transactions;
    }

    public List<Transaction> getAllTransactionsOnSubscription(Subscription subscription) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE subscription_id = ? ORDER BY date ASC;";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        getStatement.setInt(1, subscription.id());

        ResultSet rs = getStatement.executeQuery();
        while (rs.next()) {
            Integer subscriptionId = rs.getInt("subscription_id");
            if (rs.wasNull()) {
                subscriptionId = null;
            }
            transactions.add(new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDate("date").toLocalDate(),
                    new Currency(rs.getInt("amount_cents")),
                    subscriptionId
            ));
        }
        return transactions;
    }

    public Transaction getTransactionBySubscriptionWithDate(Subscription subscription, LocalDate date) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE subscription_id = ? AND date = ?;";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        getStatement.setInt(1, subscription.id());
        getStatement.setDate(2, Date.valueOf(date));

        ResultSet rs = getStatement.executeQuery();
        if (rs.next()) {
            Integer subscriptionId = rs.getInt("subscription_id");
            if (rs.wasNull()) {
                subscriptionId = null;
            }
            return new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDate("date").toLocalDate(),
                    new Currency(rs.getInt("amount_cents")),
                    subscriptionId
            );
        }
        return null;
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY date;";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        ResultSet rs = getStatement.executeQuery();
        while (rs.next()) {
            Integer subscriptionId = rs.getInt("subscription_id");
            if (rs.wasNull()) {
                subscriptionId = null;
            }
            transactions.add(new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDate("date").toLocalDate(),
                    new Currency(rs.getInt("amount_cents")),
                    subscriptionId
            ));
        }
        return transactions;
    }

    /**
     * Updates the transaction by id
     */
    public void updateTransaction(int id, Transaction toUpdate) throws SQLException {
        String sql = "UPDATE transactions SET name = ?, type = ?, date = ?, amount_cents = ? WHERE transaction_id = ?;";
        PreparedStatement updateStatement = connection.prepareStatement(sql);

        updateStatement.setString(1, toUpdate.name());
        updateStatement.setString(2, toUpdate.type());
        updateStatement.setDate(3, Date.valueOf(toUpdate.date()));
        updateStatement.setInt(4, toUpdate.amount().getAmountCents());
        updateStatement.setInt(5, id);
        updateStatement.executeUpdate();
    }

    /**
     * Deletes the transaction by id
     */
    public void deleteTransaction(int id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(sql);

        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }
}
