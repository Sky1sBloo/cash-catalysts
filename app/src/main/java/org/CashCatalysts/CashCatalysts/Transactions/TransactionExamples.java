package org.CashCatalysts.CashCatalysts.Transactions;

import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TransactionExamples {

    public static List<Transaction> getExampleTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, "Walmart", "Groceries", Date.valueOf("2025-01-01"), new Currency(50, 0)));
        transactions.add(new Transaction(2, "Landlord", "Rent", Date.valueOf("2025-01-02"), new Currency(1500, 0)));
        transactions.add(new Transaction(3, "Electric Company", "Utilities", Date.valueOf("2025-01-03"), new Currency(200, 0)));
        transactions.add(new Transaction(4, "Restaurant", "Dining", Date.valueOf("2025-01-04"), new Currency(30, 0)));
        transactions.add(new Transaction(5, "Gas Station", "Transport", Date.valueOf("2025-01-05"), new Currency(40, 0)));
        transactions.add(new Transaction(6, "Movie Theater", "Entertainment", Date.valueOf("2025-01-06"), new Currency(25, 0)));
        transactions.add(new Transaction(7, "Supermarket", "Groceries", Date.valueOf("2025-01-07"), new Currency(60, 0)));
        transactions.add(new Transaction(8, "Landlord", "Rent", Date.valueOf("2025-01-08"), new Currency(1500, 0)));
        transactions.add(new Transaction(9, "Water Company", "Utilities", Date.valueOf("2025-01-09"), new Currency(18, 0)));
        transactions.add(new Transaction(10, "Cafe", "Dining", Date.valueOf("2025-01-01"), new Currency(22, 0)));

        return transactions;
    }
}