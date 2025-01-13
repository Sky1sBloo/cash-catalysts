package org.CashCatalysts.CashCatalysts.Database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatabaseHandlerTests {
    @Test
    public void testPerformQuery() {
        Assertions.assertDoesNotThrow(() -> {
            DatabaseHandler databaseHandler = new DatabaseHandler(":memory:");
            String query = "CREATE TABLE test (id INTEGER PRIMARY KEY, name TEXT NOT NULL)";

            databaseHandler.performQuery(query);
        });
    }
}
