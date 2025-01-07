package org.CashCatalysts.CashCatalysts.Database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class TransactionsTableTests {
    /**
     * Tests the construction of transactions table
     */
    @Test
    public void testConstruction() {
        Assertions.assertDoesNotThrow(() -> {
           Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
           TransactionsTable transactionsTable = new TransactionsTable(connection);
        });
    }

    // TODO: Add insertion when user expenses record is added
}
