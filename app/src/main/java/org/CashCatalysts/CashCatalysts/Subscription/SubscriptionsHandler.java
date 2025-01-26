package org.CashCatalysts.CashCatalysts.Subscription;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionsHandler {
    private final DatabaseHandler dbHandler;

    public SubscriptionsHandler(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void addSubscription(SubscriptionRecord subscription) throws SQLException {
        dbHandler.getSubscriptionsTable().insertSubscription(subscription);
    }

    public void editSubscription(SubscriptionRecord subscription) throws SQLException {
        dbHandler.getSubscriptionsTable().editSubscription(subscription);
    }

    public void deleteSubscription(int id) throws SQLException {
        dbHandler.getSubscriptionsTable().deleteSubscription(id);
    }

    public ResultSet filterSubscriptionsByDateAndType(String type, Date startDate, Date endDate) throws SQLException {
        return dbHandler.getSubscriptionsTable().filterSubscriptionsByDateAndType(type, startDate, endDate);
    }

    public void addTransactionsForSubscriptions() throws SQLException {
        ResultSet subscriptions = dbHandler.getSubscriptionsTable().getAllSubscriptions();
        while (subscriptions.next()) {
            int id = subscriptions.getInt("id");
            Date startDate = subscriptions.getDate("startDate");
            Date endDate = subscriptions.getDate("endDate");
            String paymentTime = subscriptions.getString("paymentTime");

            // Automatically add transactions based on the subscription details
            System.out.println("Processing subscription ID: " + id + " from " + startDate + " to " + endDate + " with payment frequency: " + paymentTime);


        }
    }
}
