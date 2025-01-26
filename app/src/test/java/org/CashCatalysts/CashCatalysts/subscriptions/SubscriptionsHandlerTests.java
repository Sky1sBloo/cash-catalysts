package org.CashCatalysts.CashCatalysts.subscriptions;

import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionType;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class SubscriptionsHandlerTests {
    private SubscriptionsHandler subscriptionsHandler;
    private TransactionHandler transactionHandler;

    @BeforeEach
    public void load() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler(":memory:");
        transactionHandler = new TransactionHandler(databaseHandler);
        subscriptionsHandler = new SubscriptionsHandler(databaseHandler, transactionHandler);
    }


    @Test
    public void addSubscription() {
        LocalDate startDate = LocalDate.of(2025, 5, 25);
        LocalDate endDate = LocalDate.of(2026, 4, 23);
        Subscription subscription =
                SubscriptionsHandler.createSubscription(
                        "Car",
                        TransactionType.MAINTENANCE,
                        SubscriptionFrequency.MONTHLY,
                        startDate,
                        endDate,
                        new Currency(200, 25));
        int id = subscriptionsHandler.addSubscription(subscription);
        Subscription retrieved = subscriptionsHandler.getSubscription(id);


        Assertions.assertEquals(subscription.name(), retrieved.name());
        Assertions.assertEquals(subscription.type(), retrieved.type());
        Assertions.assertEquals(subscription.frequency(), retrieved.frequency());
        Assertions.assertEquals(subscription.startDate(), retrieved.startDate());
        Assertions.assertEquals(subscription.endDate(), retrieved.endDate());
        Assertions.assertEquals(subscription.amount(), retrieved.amount());
        subscriptionsHandler.addTransactionForSubscription(retrieved);

        List<Transaction> subscriptionTransactions = transactionHandler.getAllTransactionsOnSubscription(retrieved);
        subscriptionTransactions.forEach(transaction -> Assertions.assertEquals(retrieved.id(), transaction.subscriptionId()));
        int monthsBetweenDates = Period.between(startDate, endDate).getMonths();
        Assertions.assertEquals(monthsBetweenDates + 1, subscriptionTransactions.size());
    }

    @Test
    public void deleteSubscription() {
        LocalDate startDate = LocalDate.of(2025, 5, 25);
        LocalDate endDate = LocalDate.of(2026, 4, 23);
        LocalDate deletionDate = LocalDate.of(2025, 10, 13);
        Subscription subscription =
                SubscriptionsHandler.createSubscription(
                        "Car",
                        TransactionType.MAINTENANCE,
                        SubscriptionFrequency.MONTHLY,
                        startDate,
                        endDate,
                        new Currency(200, 25));
        int id = subscriptionsHandler.addSubscription(subscription);
        Subscription retrievedBefore = subscriptionsHandler.getSubscription(id);
        subscriptionsHandler.addTransactionForSubscription(retrievedBefore);
        subscriptionsHandler.deleteSubscription(id, LocalDate.of(2025, 10, 13));
        Subscription retrievedAfter = subscriptionsHandler.getSubscription(id);
        Assertions.assertNull(retrievedAfter);
    }

    @Test
    public void editSubscription() {
        LocalDate startDate = LocalDate.of(2025, 5, 25);
        LocalDate endDate = LocalDate.of(2026, 4, 23);
        LocalDate testNow = LocalDate.of(2025, 10, 13);

        Subscription subscription =
                SubscriptionsHandler.createSubscription(
                        "Car",
                        TransactionType.MAINTENANCE,
                        SubscriptionFrequency.MONTHLY,
                        startDate,
                        endDate,
                        new Currency(200, 25));
        int id = subscriptionsHandler.addSubscription(subscription);
        Subscription retrieved1 = subscriptionsHandler.getSubscription(id);
        subscriptionsHandler.addTransactionForSubscription(retrieved1);
        subscriptionsHandler.addTransactionForSubscription(retrieved1);
        Subscription subscriptionNew =
                new Subscription(
                        id,
                        "Car",
                        TransactionType.MAINTENANCE,
                        SubscriptionFrequency.BIWEEKLY,
                        startDate,
                        endDate.plusMonths(2),
                        new Currency(200, 25)
                );
        subscriptionsHandler.editSubscription(subscriptionNew);
        Subscription retrieved = subscriptionsHandler.getSubscription(id);
        subscriptionsHandler.deleteAllTransactionsOnSubscriptionAfterDate(retrieved, testNow);
        subscriptionsHandler.addTransactionForSubscription(retrieved, testNow);

        Assertions.assertEquals(subscriptionNew.name(), retrieved.name());
        Assertions.assertEquals(subscriptionNew.type(), retrieved.type());
        Assertions.assertEquals(subscriptionNew.frequency(), retrieved.frequency());
        Assertions.assertEquals(subscriptionNew.startDate(), retrieved.startDate());
        Assertions.assertEquals(subscriptionNew.endDate(), retrieved.endDate());
        Assertions.assertEquals(subscriptionNew.amount(), retrieved.amount());
        subscriptionsHandler.addTransactionForSubscription(retrieved, testNow);

        List<Transaction> subscriptionTransactions = transactionHandler.getAllTransactionsOnSubscription(retrieved);
        subscriptionTransactions.forEach(transaction -> Assertions.assertEquals(retrieved.id(), transaction.subscriptionId()));
        Assertions.assertEquals(24, subscriptionTransactions.size());
    }
}
