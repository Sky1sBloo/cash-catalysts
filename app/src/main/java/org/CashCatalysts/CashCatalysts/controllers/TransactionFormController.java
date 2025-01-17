package org.CashCatalysts.CashCatalysts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionType;

import java.sql.Date;

public class TransactionFormController {
    private final Integer transactionId;  // For editing transactions. set null for new transactions

    @FXML
    private TextField name_txtbx;
    @FXML
    private ChoiceBox<TransactionType> type_selector;
    @FXML
    private DatePicker date_selector;
    @FXML
    private TextField amount_txtbx;


    public TransactionFormController() {
        transactionId = null;
    }

    public TransactionFormController(Transaction transaction) {
        transactionId = transaction.transactionId();
    }

    public void initialize() {
        type_selector.getItems().addAll(TransactionType.values());
    }

    public Transaction getTransaction() {
        if (name_txtbx.getText().isEmpty()) {
            return null;
        }
        if (amount_txtbx.getText().isEmpty()
                || !amount_txtbx.getText().matches("\\d+(\\.\\d+)?")) {
            return null;
        }
        if (type_selector.getSelectionModel().isEmpty()) {
            return null;
        }
        int amount = Integer.parseInt(amount_txtbx.getText());
        int amountCents = (int) (Double.parseDouble(amount_txtbx.getText()) * 100);
        return TransactionHandler.createTransaction(
                name_txtbx.getText(),
                type_selector.getSelectionModel().getSelectedItem().toString(),
                Date.valueOf(date_selector.getValue()),
                amount, amountCents
        );
    }

    /**
     * Retrieves the transaction id
     * @return null if the transaction is new, otherwise the transaction id
     */
    public Integer getTransactionId() {
        return transactionId;
    }


}
