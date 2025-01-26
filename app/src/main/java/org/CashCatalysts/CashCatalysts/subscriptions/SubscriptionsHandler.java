package org.CashCatalysts.CashCatalysts.subscriptions;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Database.SubscriptionsTable;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionType;
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

    public static Subscription createSubscription(String name, TransactionType type, SubscriptionFrequency frequency, LocalDate startDate, LocalDate endDate, Currency amount) {
        return new Subscription(null, name, type, frequency, startDate, endDate, amount);
    }

    public int addSubscription(Subscription subscription) {
        try {
            return subscriptionsTable.insertSubscription(subscription);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Subscription getSubscription(int id) {
        try {
            return subscriptionsTable.getSubscription(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editSubscription(Subscription subscription) {
        try {
            subscriptionsTable.editSubscription(subscription);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a subscription and all transactions after startDeletion associated with it
     */
    public void deleteSubscription(int id, LocalDate startDeletion) {
        deleteAllTransactionsOnSubscriptionAfterDate(getSubscription(id), startDeletion);
        try {
            subscriptionsTable.deleteSubscription(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet filterSubscriptionsByDateAndType(String type, DateRange dateRange) {
        try {
            return subscriptionsTable.filterSubscriptionsByDateAndType(type, dateRange.begin(), dateRange.end());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Automatically adds transactions for a subscription from the parameter start date to the last date
     * Generally used after editing a subscription
     */
    public void addTransactionForSubscription(Subscription subscription, LocalDate startDate) {
        LocalDate currentDate = startDate;

        while (currentDate.isBefore(subscription.endDate())) {
            if (transactionHandler.getTransactionOnSubscriptionWithDate(subscription, currentDate) == null) {
                transactionHandler.addTransaction(TransactionHandler.createTransaction(subscription.name(), subscription.type().toString(), currentDate, subscription.amount(), subscription.id()));
            }
            currentDate = switch (subscription.frequency()) {
                case DAILY -> currentDate.plusDays(1);
                case WEEKLY -> currentDate.plusWeeks(1);
                case BIWEEKLY -> currentDate.plusWeeks(2);
                case MONTHLY -> currentDate.plusMonths(1);
                case YEARLY -> currentDate.plusYears(1);
            };
        }
    }

    /**
     * Automatically adds transactions for a subscription from the start date to the last date
     */
    public void addTransactionForSubscription(Subscription subscription) {
        addTransactionForSubscription(subscription, subscription.startDate());
    }

    /**
     * Deletes all transactions on a subscription after a certain date
     */
    public void deleteAllTransactionsOnSubscriptionAfterDate(Subscription subscription, LocalDate date) {
        List<Transaction> transactions = transactionHandler.getAllTransactionsOnSubscription(subscription);
        transactions.stream()
                .filter(transaction -> transaction.date().isAfter(date))
                .forEach(transaction -> {
                    transactionHandler.deleteTransaction(transaction.transactionId());
                });
    }
}
