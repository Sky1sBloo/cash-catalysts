package org.CashCatalysts.CashCatalysts.subscriptions;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.datatypes.DateRange;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SubscriptionsHandler {
    private final DatabaseHandler dbHandler;
    private final TransactionHandler transactionHandler;

    public SubscriptionsHandler(DatabaseHandler dbHandler, TransactionHandler transactionHandler) {
        this.dbHandler = dbHandler;
        this.transactionHandler = transactionHandler;
    }

    public void addSubscription(Subscription subscription) throws SQLException {
        dbHandler.getSubscriptionsTable().insertSubscription(subscription);
        addTransactionForSubscription(subscription);
    }

    public void editSubscription(Subscription subscription) throws SQLException {
        dbHandler.getSubscriptionsTable().editSubscription(subscription);
    }

    public void deleteSubscription(int id) throws SQLException {
        dbHandler.getSubscriptionsTable().deleteSubscription(id);
    }

    public ResultSet filterSubscriptionsByDateAndType(String type, DateRange dateRange) throws SQLException {
        return dbHandler.getSubscriptionsTable().filterSubscriptionsByDateAndType(type, dateRange.begin(), dateRange.end());
    }

    private void addTransactionForSubscription(Subscription subscription) {
        LocalDate currentDate = subscription.startDate();
    }

    /*
    public void addTransactionsForSubscriptions() throws SQLException {
        ResultSet subscriptions = dbHandler.getSubscriptionsTable().getAllSubscriptions();
        while (subscriptions.next()) {
            int id = subscriptions.getInt("id");
            LocalDate startDate = subscriptions.getDate("startDate").toLocalDate();
            LocalDate endDate = subscriptions.getDate("endDate").toLocalDate();
            String frequencyStr = subscriptions.getString("paymentTime");
            SubscriptionFrequency frequency = SubscriptionFrequency.valueOf(frequencyStr);


            // Automatically add transactions based on the subscription details
            System.out.println("Processing subscription ID: " + id + " from " + startDate + " to " + endDate + " with payment frequency: " + frequencyStr);

            // Add transactions for this subscription
            dbHandler.getSubscriptionsTable().addTransactionsForSubscription(id, startDate, endDate, frequency);
        }
    } */
}
