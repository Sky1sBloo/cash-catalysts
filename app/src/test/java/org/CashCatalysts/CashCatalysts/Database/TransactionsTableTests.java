package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TransactionsTableTests {
    TransactionsTable transactionsTable;

    @BeforeEach
    public void setup() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        transactionsTable = new TransactionsTable(connection);
    }

    /**
     * Tests the construction of transactions table
     */
    @Test
    public void testAddTransactions() throws SQLException {
        Transaction addTransaction = TransactionHandler.createTransaction("testTransaction", "testType", LocalDate.of(2023, 1, 1), new Currency(100, 0));
        int transactionId = transactionsTable.addTransaction(addTransaction);

        Transaction expectedTransaction = new Transaction(transactionId, "testTransaction", "testType", LocalDate.of(2023, 1, 1), new Currency(100, 0), null);
        Transaction retrievedTransaction = transactionsTable.getTransaction(transactionId);
        Assertions.assertEquals(expectedTransaction, retrievedTransaction);
    }

    @Test
    public void testUpdateTransaction() throws SQLException {
        Transaction addTransaction = TransactionHandler.createTransaction("testTransaction", "testType", LocalDate.of(2023, 1, 1), new Currency(100, 0));
        int transactionId = transactionsTable.addTransaction(addTransaction);

        Transaction updateTransaction = new Transaction(transactionId, "updatedTransaction", "updatedType", LocalDate.of(2023, 1, 1), new Currency(100, 0), null);
        transactionsTable.updateTransaction(transactionId, updateTransaction);
        Assertions.assertEquals(updateTransaction, transactionsTable.getTransaction(transactionId));
    }

    @Test
    public void getBetweenTransaction() throws SQLException {
        Transaction transaction1 = TransactionHandler.createTransaction("oldDate", "type1", LocalDate.of(2023, 1, 1), new Currency(100, 0));
        Transaction transaction2 = TransactionHandler.createTransaction("slightDate", "type2", LocalDate.of(2023, 5, 13), new Currency(200, 25));
        Transaction transaction3 = TransactionHandler.createTransaction("newDate", "testType", LocalDate.of(2023, 6, 1), new Currency(300, 50));
        Transaction transaction4 = TransactionHandler.createTransaction("futureDate", "testType", LocalDate.of(2023, 12, 31), new Currency(400, 75));

        transactionsTable.addTransaction(transaction1);
        int transaction2Id = transactionsTable.addTransaction(transaction2);
        int transaction3Id = transactionsTable.addTransaction(transaction3);
        transactionsTable.addTransaction(transaction4);

        Transaction[] expectedTransactions = {
                new Transaction(transaction2Id, "slightDate", "type2", LocalDate.of(2023, 5, 13), new Currency(200, 25), null),
                new Transaction(transaction3Id, "newDate", "testType", LocalDate.of(2023, 6, 1), new Currency(300, 50), null)
        };
        List<Transaction> actualTransactions = transactionsTable.getAllTransactionsBetween(LocalDate.of(2023, 5, 1), LocalDate.of(2023, 6, 1));
        Assertions.assertArrayEquals(expectedTransactions, actualTransactions.toArray());
    }

    @Test
    public void deleteTransactionTest() throws SQLException {
        Transaction transaction1 = TransactionHandler.createTransaction("oldDate", "type1", LocalDate.of(2023, 1, 1), new Currency(100, 0));
        Transaction transaction2 = TransactionHandler.createTransaction("slightDate", "type2", LocalDate.of(2023, 5, 13), new Currency(200, 25));
        Transaction transaction3 = TransactionHandler.createTransaction("newDate", "testType", LocalDate.of(2023, 6, 1), new Currency(300, 50));
        Transaction transaction4 = TransactionHandler.createTransaction("futureDate", "testType", LocalDate.of(2023, 12, 31), new Currency(400, 75));

        transactionsTable.addTransaction(transaction1);
        int transaction2Id = transactionsTable.addTransaction(transaction2);
        transactionsTable.addTransaction(transaction3);
        transactionsTable.addTransaction(transaction4);

        transactionsTable.deleteTransaction(2);

        Assertions.assertNull(transactionsTable.getTransaction(transaction2Id));
    }
}
