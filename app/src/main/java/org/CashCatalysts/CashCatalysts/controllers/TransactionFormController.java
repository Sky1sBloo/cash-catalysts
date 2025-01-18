package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionType;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

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

    private final Transaction transaction;


    public TransactionFormController() {
        transactionId = null;
        transaction = null;
    }

    public TransactionFormController(Transaction transaction) {
        transactionId = transaction.transactionId();
        this.transaction = transaction;
    }

    public void initialize() {
        type_selector.getItems().addAll(TransactionType.values());
        if (transaction != null) {
            name_txtbx.setText(transaction.name());
            //type_selector.setValue(TransactionType.valueOf(transaction.type()));
            date_selector.setValue(transaction.date().toLocalDate());
            amount_txtbx.setText(transaction.amount().getAmount() + "." + transaction.amount().getCents());
        }
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

        int amountCents = (int) (Double.parseDouble(amount_txtbx.getText()) * 100);
        return TransactionHandler.createTransaction(
                name_txtbx.getText(),
                type_selector.getSelectionModel().getSelectedItem().toString(),
                Date.valueOf(date_selector.getValue()),
                new Currency(amountCents)
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
