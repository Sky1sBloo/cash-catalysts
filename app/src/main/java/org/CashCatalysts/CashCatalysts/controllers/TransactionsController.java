package org.CashCatalysts.CashCatalysts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TransactionsController {
    @FXML
    private ListView<String> transactions_list;

    @FXML
    public void initialize() {
        List<Transaction> transactionsTest = new ArrayList<>();
        transactionsTest.add(TransactionHandler.createTransaction("Test1", "potato", Date.valueOf("2025-01-16"), 100, 10));
        loadTransactions(transactionsTest);
    }

    private void loadTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            transactions_list.getItems().add(formatTransaction(transaction));
        }
    }

    private String formatTransaction(Transaction transaction) {
        return transaction.type() + " " + transaction.name() + " " + transaction.date().toString() + " " + transaction.amount() + "." + transaction.amountCents();
    }
}
