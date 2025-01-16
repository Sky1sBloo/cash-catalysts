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

    public TransactionCardController(Transaction transaction, Consumer<Transaction> deleteCallback) {
        this.transaction = transaction;
        this.deleteCallback = deleteCallback;
    }

    public void initialize() {
        type.setText(transaction.type());
        name.setText(transaction.name());
        date.setText(transaction.date().toString());
        amount.setText(transaction.amount() + "." + transaction.amountCents());
    }

    public void deleteTransaction() {
        deleteCallback.accept(transaction);
    }
}
