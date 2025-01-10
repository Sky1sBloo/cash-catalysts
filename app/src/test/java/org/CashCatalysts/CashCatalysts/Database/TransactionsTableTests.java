package org.CashCatalysts.CashCatalysts.Database;

import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
