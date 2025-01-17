package org.CashCatalysts.CashCatalysts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionsController {
    private final TransactionHandler transactionHandler;
    @FXML
    private VBox transactionCards;

    public TransactionsController(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }

    public void initialize() throws IOException {
        transactionHandler.addTransaction(TransactionHandler.createTransaction("Test1", "potato", Date.valueOf("2025-01-17"), 100, 10));
        transactionHandler.addTransaction(TransactionHandler.createTransaction("TestPop", "banana", Date.valueOf("2025-01-17"), 200, 0));

        try {
            LocalDate startOfDay = LocalDate.from(LocalDate.now().atStartOfDay());
            LocalDate endOfDay = LocalDate.from(LocalDate.now().atTime(23, 59, 59));
            loadTransactions(transactionHandler.getAllTransactionsBetween(
                    Date.valueOf(startOfDay), Date.valueOf(endOfDay)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTransactions(List<Transaction> transactions) throws IOException {
        transactionCards.getChildren().clear();
        for (Transaction transaction : transactions) {
            FXMLLoader transactionCardLoader = new FXMLLoader(getClass().getResource("../forms/TransactionCard.fxml"));
            transactionCardLoader.setController(new TransactionCardController(transaction, this::deleteTransaction));

            transactionCards.getChildren().add(transactionCardLoader.load());
        }
    }

    public void deleteTransaction(Transaction transaction) {
        transactionHandler.deleteTransaction(transaction.transactionId());
        LocalDate startOfDay = LocalDate.from(LocalDate.now().atStartOfDay());
        LocalDate endOfDay = LocalDate.from(LocalDate.now().atTime(23, 59, 59));
        try {
            loadTransactions(transactionHandler.getAllTransactionsBetween(
                    Date.valueOf(startOfDay), Date.valueOf(endOfDay)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
