package org.CashCatalysts.CashCatalysts.subscriptions;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.SubscriptionsTable;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.CashCatalysts.CashCatalysts.datatypes.DateRange;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SubscriptionsHandler {
    private final SubscriptionsTable subscriptionsTable;
    private final TransactionHandler transactionHandler;

    public SubscriptionsHandler(DatabaseHandler dbHandler, TransactionHandler transactionHandler) {
        this.subscriptionsTable = dbHandler.getSubscriptionsTable();
        this.transactionHandler = transactionHandler;
    }

    public static Subscription createSubscription(String name, String type, SubscriptionFrequency frequency, LocalDate startDate, LocalDate endDate, Currency amount) {
        return new Subscription(null, name, type, frequency, startDate, endDate, amount);
    }

    public void addSubscription(Subscription subscription) throws SQLException {
        subscriptionsTable.insertSubscription(subscription);
        addTransactionForSubscription(subscription);
    }

    public Subscription getSubscription(int id) throws SQLException {
        return subscriptionsTable.getSubscription(id);
    }

    public void editSubscription(Subscription subscription) throws SQLException {
        subscriptionsTable.editSubscription(subscription);
    }

    /**
     * Deletes a subscription and all transactions after startDeletion associated with it
     */
    public void deleteSubscription(int id, LocalDate startDeletion) throws SQLException {
        subscriptionsTable.deleteSubscription(id);
        deleteAllTransactionsOnSubscriptionAfterDate(getSubscription(id), startDeletion);
    }

    public ResultSet filterSubscriptionsByDateAndType(String type, DateRange dateRange) throws SQLException {
        return subscriptionsTable.filterSubscriptionsByDateAndType(type, dateRange.begin(), dateRange.end());
    }

    private void addTransactionForSubscription(Subscription subscription) {
        LocalDate currentDate = subscription.startDate();
    }

    public void addTransactionsForSubscriptions(Subscription subscription) {
        LocalDate currentDate = subscription.startDate();

        while (currentDate.isBefore(subscription.endDate())) {
            if (transactionHandler.getTransactionOnSubscriptionWithDate(subscription, currentDate) == null) {
                continue;
            }
            transactionHandler.addTransaction(TransactionHandler.createTransaction(subscription.name(), subscription.type(), currentDate, subscription.amount(), subscription.id()));
            currentDate = switch (subscription.frequency()) {
                case DAILY -> currentDate.plusDays(1);
                case WEEKLY -> currentDate.plusWeeks(1);
                case BIWEEKLY -> currentDate.plusWeeks(2);
                case MONTHLY -> currentDate.plusMonths(1);
                case YEARLY -> currentDate.plusYears(1);
            };
        }
    }

    public void deleteAllTransactionsOnSubscriptionAfterDate(Subscription subscription, LocalDate date) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsOnSubscription(subscription);
        transactions.stream()
                .filter(transaction -> transaction.date().isAfter(date))
                .forEach(transaction -> {
                    transactionHandler.deleteTransaction(transaction.transactionId());
                });
    }
}
