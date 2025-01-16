package org.CashCatalysts.CashCatalysts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TransactionsController {
    @FXML
    private VBox transactionCards;
    private FXMLLoader transactionCardLoader;


    public void initialize() {
        List<Transaction> transactionsTest = new ArrayList<>();
        transactionsTest.add(TransactionHandler.createTransaction("Test1", "potato", Date.valueOf("2025-01-16"), 100, 10));

        transactionCardLoader = new FXMLLoader(getClass().getResource("../forms/TransactionCard.fxml"));

        try {
            loadTransactions(transactionsTest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTransactions(List<Transaction> transactions) throws IOException {
        for (Transaction transaction : transactions) {
            transactionCardLoader.setController(new TransactionCardController(transaction));

            transactionCards.getChildren().add(transactionCardLoader.load());
        }
    }
}
