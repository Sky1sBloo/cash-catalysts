package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.subscriptions.Subscription;
import org.CashCatalysts.CashCatalysts.subscriptions.SubscriptionFrequency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;

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
        stmt.setString(2, subscription.type());
        stmt.setString(3, subscription.frequency().name());
        stmt.setDate(4, Date.valueOf(subscription.startDate()));
        stmt.setDate(5, Date.valueOf(subscription.endDate()));
        stmt.setInt(6, subscription.amount().getAmountCents());
        stmt.executeUpdate();
        return getLastRowId();
    }

    public void editSubscription(Subscription subscription) throws SQLException {
        String query = "UPDATE subscriptions SET name = ?, type = ?, frequency = ?, startDate = ?, endDate = ?, startAmount = ?, amountCents = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, subscription.name());
        stmt.setString(2, subscription.type());
        stmt.setString(3, subscription.frequency().name());
        stmt.setDate(4, Date.valueOf(subscription.startDate()));
        stmt.setDate(5, Date.valueOf(subscription.endDate()));
        stmt.setInt(6, subscription.amount().getAmountCents());
        stmt.setInt(8, subscription.id());
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

    public void addTransactionsForSubscription(int subscriptionId, LocalDate startDate, LocalDate endDate, SubscriptionFrequency frequency) throws SQLException {
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            String checkQuery = "SELECT COUNT(*) FROM transactions WHERE subscription_id = ? AND transaction_date = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setInt(1, subscriptionId);
            checkStmt.setDate(2, Date.valueOf(currentDate));
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                String insertQuery = "INSERT INTO transactions (subscription_id, transaction_date) VALUES (?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, subscriptionId);
                    insertStmt.setDate(2, Date.valueOf(currentDate));
                    insertStmt.executeUpdate();
                }
            }

            currentDate = switch (frequency) {
                case SubscriptionFrequency.DAILY -> {
                    yield currentDate.plusDays(1);
                }
                case SubscriptionFrequency.WEEKLY -> {
                    yield currentDate.plusWeeks(1);
                }
                case SubscriptionFrequency.MONTHLY -> {
                    yield currentDate.plusMonths(1);
                }
                case SubscriptionFrequency.YEARLY -> {
                    yield currentDate.plusYears(1);
                }
            };
        }
    }

    public ResultSet getAllSubscriptions() throws SQLException {
        String query = "SELECT * FROM subscriptions";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }
}
