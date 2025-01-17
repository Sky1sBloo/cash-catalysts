package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import org.CashCatalysts.CashCatalysts.Transactions.FilterType;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class TransactionsController {
    private final TransactionHandler transactionHandler;

    @FXML
    private VBox transaction_cards;
    @FXML
    private ComboBox<FilterType> filter_selection;

    public TransactionsController(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }

    public void initialize() throws IOException {
        filter_selection.setOnAction((event) -> setFilter(filter_selection.getSelectionModel().getSelectedItem()));
        filter_selection.getItems().addAll(new FilterType[]{FilterType.DAY, FilterType.WEEK, FilterType.MONTH, FilterType.YEAR});
        filter_selection.getSelectionModel().selectFirst();

        // TODO: Remove this in prod
        transactionHandler.addTransaction(TransactionHandler.createTransaction("Test1", "potato", Date.valueOf("2025-01-17"), 100, 10));
        transactionHandler.addTransaction(TransactionHandler.createTransaction("TestPop", "banana", Date.valueOf("2025-01-17"), 200, 0));
        transactionHandler.addTransaction(TransactionHandler.createTransaction("TestPop", "banana", Date.valueOf("2025-01-19"), 200, 0));
        transactionHandler.addTransaction(TransactionHandler.createTransaction("TestPop", "banana", Date.valueOf("2025-01-29"), 200, 0));
        transactionHandler.addTransaction(TransactionHandler.createTransaction("TestPop", "banana", Date.valueOf("2025-05-29"), 200, 0));


        try {
            loadTransactions(transactionHandler.getAllTransactionsOn(filter_selection.getSelectionModel().getSelectedItem()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTransactions(List<Transaction> transactions) throws IOException {
        transaction_cards.getChildren().clear();
        for (Transaction transaction : transactions) {
            FXMLLoader transactionCardLoader = new FXMLLoader(getClass().getResource("../forms/TransactionCard.fxml"));
            transactionCardLoader.setController(new TransactionCardController(transaction, this::deleteTransaction));

            transaction_cards.getChildren().add(transactionCardLoader.load());
        }
    }

    public void deleteTransaction(Transaction transaction) {
        transactionHandler.deleteTransaction(transaction.transactionId());
        try {
            loadTransactions(transactionHandler.getAllTransactionsOn(filter_selection.getSelectionModel().getSelectedItem()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFilter(FilterType filter) {
        try {
            loadTransactions(transactionHandler.getAllTransactionsOn(filter));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
