package org.CashCatalysts.CashCatalysts.Subscription;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;

public class SubscriptionTable extends DbTable{
    public SubscriptionsTable(Connection connection) throws SQLException {
        super(connection);

    }

    public void insertSubscription(SubscriptionRecord subscription) throws SQLException {
        String query = "INSERT INTO subscriptions (name, type, paymentTime, startDate, endDate, startAmount, amountCents) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subscription.getName());
            stmt.setString(2, subscription.getType());
            stmt.setString(3, subscription.getPaymentTime());
            stmt.setDate(4, subscription.getStartDate());
            stmt.setDate(5, subscription.getEndDate());
            stmt.setInt(6, subscription.getStartAmount());
            stmt.setInt(7, subscription.getAmountCents());
            stmt.executeUpdate();
        }
    }

    public void editSubscription(SubscriptionRecord subscription) throws SQLException {
        String query = "UPDATE subscriptions SET name = ?, type = ?, paymentTime = ?, startDate = ?, endDate = ?, startAmount = ?, amountCents = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subscription.getName());
            stmt.setString(2, subscription.getType());
            stmt.setString(3, subscription.getPaymentTime());
            stmt.setDate(4, subscription.getStartDate());
            stmt.setDate(5, subscription.getEndDate());
            stmt.setInt(6, subscription.getStartAmount());
            stmt.setInt(7, subscription.getAmountCents());
            stmt.setInt(8, subscription.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteSubscription(int id) throws SQLException {
        String query = "DELETE FROM subscriptions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public ResultSet filterSubscriptionsByDateAndType(String type, Date startDate, Date endDate) throws SQLException {
        String query = "SELECT * FROM subscriptions WHERE type = ? AND startDate >= ? AND endDate <= ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, type);
        stmt.setDate(2, startDate);
        stmt.setDate(3, endDate);
        return stmt.executeQuery();
    }
    public void addTransactionsForSubscription(int subscriptionId, Date startDate, Date endDate, String paymentTime) throws SQLException {
        Date currentDate = startDate;

        while (!currentDate.after(endDate)) {
            String checkQuery = "SELECT COUNT(*) FROM transactions WHERE subscription_id = ? AND transaction_date = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, subscriptionId);
                checkStmt.setDate(2, currentDate);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getInt(1) == 0) {
                    String insertQuery = "INSERT INTO transactions (subscription_id, transaction_date) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, subscriptionId);
                        insertStmt.setDate(2, currentDate);
                        insertStmt.executeUpdate();
                    }
                }
            }

            currentDate = switch (paymentTime.toLowerCase()) {
                case "daily" -> Date.valueOf(currentDate.toLocalDate().plusDays(1));
                case "monthly" -> Date.valueOf(currentDate.toLocalDate().plusMonths(1));
                case "yearly" -> Date.valueOf(currentDate.toLocalDate().plusYears(1));
                default -> throw new IllegalArgumentException("Invalid payment frequency: " + paymentTime);
            };
        }
    }
    public ResultSet getAllSubscriptions() throws SQLException {
        String query = "SELECT * FROM subscriptions";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

}
