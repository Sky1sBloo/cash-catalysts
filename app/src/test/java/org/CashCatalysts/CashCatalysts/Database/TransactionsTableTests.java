package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TransactionsTableTests {
    /**
     * Tests the construction of transactions table
     */
    @Test
    public void testAddTransactions() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        TransactionsTable transactionsTable = new TransactionsTable(connection);

        Transaction addTransaction = TransactionHandler.createTransaction("testTransaction", "testType", Date.valueOf("2023-01-01"), 100, 0);
        Transaction expectedTransaction = new Transaction(1, "testTransaction", "testType", Date.valueOf("2023-01-01"), 100, 0);
        int transactionId = transactionsTable.addTransaction(addTransaction);

        Transaction getTransaction = transactionsTable.getTransaction(transactionId);
        Assertions.assertEquals(expectedTransaction, getTransaction);
    }

    @Test
    public void testUpdateTransaction() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        TransactionsTable transactionsTable = new TransactionsTable(connection);

        Transaction addTransaction = TransactionHandler.createTransaction("testTransaction", "testType", Date.valueOf("2023-01-01"), 100, 0);
        int transactionId = transactionsTable.addTransaction(addTransaction);

        Transaction updateTransaction = new Transaction(transactionId, "updatedTransaction", "updatedType", Date.valueOf("2023-01-01"), 200, 0);
        transactionsTable.updateTransaction(transactionId, updateTransaction);
        Assertions.assertEquals(updateTransaction, transactionsTable.getTransaction(transactionId));
    }

    @Test
    public void getBetweenTransaction() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        TransactionsTable transactionsTable = new TransactionsTable(connection);

        Transaction transaction1 = TransactionHandler.createTransaction("oldDate", "type1", Date.valueOf("2023-01-01"), 100, 0);
        Transaction transaction2 = TransactionHandler.createTransaction("slightDate", "type2", Date.valueOf("2023-05-13"), 200, 25);
        Transaction transaction3 = TransactionHandler.createTransaction("newDate", "testType", Date.valueOf("2023-06-01"), 300, 50);
        Transaction transaction4 = TransactionHandler.createTransaction("futureDate", "testType", Date.valueOf("2023-12-31"), 400, 75);

        transactionsTable.addTransaction(transaction1);
        int transaction2Id = transactionsTable.addTransaction(transaction2);
        int transaction3Id = transactionsTable.addTransaction(transaction3);
        transactionsTable.addTransaction(transaction4);

        Transaction[] expectedTransactions = {
                new Transaction(transaction2Id, "slightDate", "type2", Date.valueOf("2023-05-13"), 200, 25),
                new Transaction(transaction3Id, "newDate", "testType", Date.valueOf("2023-06-01"), 300, 50)
        };
        List<Transaction> actualTransactions = transactionsTable.getAllTransactionsBetween(Date.valueOf("2023-05-01"), Date.valueOf("2023-06-01"));
        Assertions.assertArrayEquals(expectedTransactions, actualTransactions.toArray());
    }

    @Test
    public void deleteTransactionTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        TransactionsTable transactionsTable = new TransactionsTable(connection);

        Transaction transaction1 = TransactionHandler.createTransaction("oldDate", "type1", Date.valueOf("2023-01-01"), 100, 0);
        Transaction transaction2 = TransactionHandler.createTransaction("slightDate", "type2", Date.valueOf("2023-05-13"), 200, 25);
        Transaction transaction3 = TransactionHandler.createTransaction("newDate", "testType", Date.valueOf("2023-06-01"), 300, 50);
        Transaction transaction4 = TransactionHandler.createTransaction("futureDate", "testType", Date.valueOf("2023-12-31"), 400, 75);

        transactionsTable.addTransaction(transaction1);
        int transaction2Id = transactionsTable.addTransaction(transaction2);
        transactionsTable.addTransaction(transaction3);
        transactionsTable.addTransaction(transaction4);

        transactionsTable.deleteTransaction(2);

        Assertions.assertThrows(SQLException.class, () -> transactionsTable.getTransaction(transaction2Id));
    }
}
