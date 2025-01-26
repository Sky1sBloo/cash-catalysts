package org.CashCatalysts.CashCatalysts.Transactions;

import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionExamples {

    public static List<Transaction> getExampleTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, "Walmart", "Groceries", LocalDate.of(2025, 1, 1), new Currency(50, 0)));
        transactions.add(new Transaction(2, "Landlord", "Rent", LocalDate.of(2025, 1, 2), new Currency(1500, 0)));
        transactions.add(new Transaction(3, "Electric Company", "Utilities", LocalDate.of(2025, 1, 3), new Currency(200, 0)));
        transactions.add(new Transaction(4, "Restaurant", "Dining", LocalDate.of(2025, 1, 4), new Currency(30, 0)));
        transactions.add(new Transaction(5, "Gas Station", "Transport", LocalDate.of(2025, 1, 5), new Currency(40, 0)));
        transactions.add(new Transaction(6, "Movie Theater", "Entertainment", LocalDate.of(2025, 1, 6), new Currency(25, 0)));
        transactions.add(new Transaction(7, "Supermarket", "Groceries", LocalDate.of(2025, 1, 7), new Currency(60, 0)));
        transactions.add(new Transaction(8, "Landlord", "Rent", LocalDate.of(2025, 1, 8), new Currency(1500, 0)));
        transactions.add(new Transaction(9, "Water Company", "Utilities", LocalDate.of(2025, 3, 9), new Currency(200, 0)));
        transactions.add(new Transaction(10, "Cafe", "Dining", LocalDate.of(2025, 2, 1), new Currency(22, 0)));

        return transactions;
    }
}