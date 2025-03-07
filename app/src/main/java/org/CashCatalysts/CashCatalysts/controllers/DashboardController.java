package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.CashCatalysts.CashCatalysts.datatypes.DateFilterType;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;

import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    @FXML
    private ListView<String> transactions_list;

    private final TransactionHandler transactionHandler;

    public DashboardController(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        transactions_list.getItems().addAll(getTransactionsFormatted());
    }

    private List<String> getTransactionsFormatted() {
        List<Transaction> transactions = transactionHandler.getAllTransactionsOn(DateFilterType.WEEK);
        List<String> formattedTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            formattedTransactions.add(
                    transaction.type() + " - " + transaction.amount().getAmount() + "." + transaction.amount().getCents() + " - " + transaction.date().toString() + " - " + transaction.name()
            );
        }
        return formattedTransactions;
    }
}
