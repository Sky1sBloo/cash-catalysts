package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;

import java.util.function.Consumer;


public class TransactionCardController {
    @FXML
    private Label type;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label amount;
    private final Transaction transaction;
    private final Consumer<Transaction> deleteCallback;
    private final Consumer<Transaction> editCallback;

    public TransactionCardController(Transaction transaction, Consumer<Transaction> deleteCallback, Consumer<Transaction> editCallback) {
        this.transaction = transaction;
        this.deleteCallback = deleteCallback;
        this.editCallback = editCallback;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        type.setText(transaction.type());
        name.setText(transaction.name());
        date.setText(transaction.date().toString());
        amount.setText(transaction.amount().getAmount() + "." + transaction.amount().getCents());
    }

    @SuppressWarnings("unused")
    public void deleteTransaction() {
        deleteCallback.accept(transaction);
    }

    @SuppressWarnings("unused")
    public void editTransaction() {
        editCallback.accept(transaction);
    }
}
