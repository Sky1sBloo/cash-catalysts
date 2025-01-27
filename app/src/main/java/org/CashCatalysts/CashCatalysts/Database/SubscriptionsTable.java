package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.Transactions.TransactionType;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.CashCatalysts.CashCatalysts.subscriptions.Subscription;
import org.CashCatalysts.CashCatalysts.subscriptions.SubscriptionFrequency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionsTable extends DbTable {

    public SubscriptionsTable(Connection connection) throws SQLException {
        super(connection);
        DbField[] fields = {
                new DbField("id", "INTEGER PRIMARY KEY"),
                new DbField("name", "VARCHAR(255)"),
                new DbField("type", "VARCHAR(128)"),
                new DbField("frequency", "TEXT"),
                new DbField("startDate", "DATE"),
                new DbField("endDate", "DATE"),
                new DbField("amountCents", "INTEGER")
        };
        super.createTable("subscriptions", fields);
    }

    public int insertSubscription(Subscription subscription) throws SQLException {
        String query = "INSERT INTO subscriptions (name, type, frequency, startDate, endDate, amountCents) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, subscription.name());
        stmt.setString(2, subscription.type().toString());
        stmt.setString(3, subscription.frequency().name());
        stmt.setDate(4, Date.valueOf(subscription.startDate()));
        stmt.setDate(5, Date.valueOf(subscription.endDate()));
        stmt.setInt(6, subscription.amount().getAmountCents());
        stmt.executeUpdate();
        return getLastRowId();
    }

    public void editSubscription(Subscription subscription) throws SQLException {
        String query = "UPDATE subscriptions SET name = ?, type = ?, frequency = ?, startDate = ?, endDate = ?, amountCents = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, subscription.name());
        stmt.setString(2, subscription.type().toString());
        stmt.setString(3, subscription.frequency().name());
        stmt.setDate(4, Date.valueOf(subscription.startDate()));
        stmt.setDate(5, Date.valueOf(subscription.endDate()));
        stmt.setInt(6, subscription.amount().getAmountCents());
        stmt.setInt(7, subscription.id());
        stmt.executeUpdate();
    }

    public void deleteSubscription(int id) throws SQLException {
        String query = "DELETE FROM subscriptions WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public ResultSet filterSubscriptionsByDateAndType(String type, LocalDate startDate, LocalDate endDate) throws SQLException {
        String query = "SELECT * FROM subscriptions WHERE type = ? AND startDate >= ? AND endDate <= ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, type);
        stmt.setDate(2, Date.valueOf(startDate));
        stmt.setDate(3, Date.valueOf(endDate));
        return stmt.executeQuery();
    }

    public List<Subscription> getAllSubscriptions() throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = "SELECT * FROM subscriptions";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            subscriptions.add(new Subscription(
                    rs.getInt("id"),
                    rs.getString("name"),
                    TransactionType.valueOf(rs.getString("type")),
                    SubscriptionFrequency.valueOf(rs.getString("frequency")),
                    rs.getDate("startDate").toLocalDate(),
                    rs.getDate("endDate").toLocalDate(),
                    new Currency(rs.getInt("amountCents"))
            ));
        }
        return subscriptions;
    }

    public Subscription getSubscription(int id) throws SQLException {
        String query = "SELECT * FROM subscriptions WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Subscription(
                    rs.getInt("id"),
                    rs.getString("name"),
                    TransactionType.valueOf(rs.getString("type")),
                    SubscriptionFrequency.valueOf(rs.getString("frequency")),
                    rs.getDate("startDate").toLocalDate(),
                    rs.getDate("endDate").toLocalDate(),
                    new Currency(rs.getInt("amountCents"))
            );
        }
        return null;
    }
}
