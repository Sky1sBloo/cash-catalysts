package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.Transactions.Transaction;

import java.sql.*;
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

    /**
     * Gets the transaction by id
     * Note: Ignores the following transactions if more than 1 returned from db
     */
    public Transaction getTransaction(int id) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        getStatement.setInt(1, id);
        ResultSet rs = getStatement.executeQuery();

        if (rs.next()) {
            int amountCents = rs.getInt("amount_cents");
            int amount = amountCents / 100;
            int cents = amountCents % 100;

            return new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDate("date"),
                    amount,
                    cents
            );
        }
        return null;
    }

    /**
     * Returns the transactions between dates
     */
    public List<Transaction> getAllTransactionsBetween(Date start, Date end) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE date BETWEEN ? and ?";
        PreparedStatement getStatement = connection.prepareStatement(sql);

        getStatement.setDate(1, start);
        getStatement.setDate(2, end);

        ResultSet rs = getStatement.executeQuery();
        while (rs.next()) {
            int amountCents = rs.getInt("amount_cents");
            int amount = amountCents / 100;
            int cents = amountCents % 100;
            transactions.add(new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDate("date"),
                    amount,
                    cents
            ));
        }
        return transactions;
    }

    /**
     * Updates the transaction by id
     */
    public void updateTransaction(int id, Transaction toUpdate) throws SQLException {
        String sql = "UPDATE transactions SET name = ?, type = ?, date = ?, amount_cents = ? WHERE transaction_id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(sql);

        updateStatement.setString(1, toUpdate.name());
        updateStatement.setString(2, toUpdate.type());
        updateStatement.setDate(3, toUpdate.date());
        updateStatement.setInt(4, toUpdate.amount() * 100 + toUpdate.amountCents());
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
