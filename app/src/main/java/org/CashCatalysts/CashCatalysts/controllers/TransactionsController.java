package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import org.CashCatalysts.CashCatalysts.Transactions.FilterType;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.budgets.Budget;
import org.CashCatalysts.CashCatalysts.budgets.BudgetHandler;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class TransactionsController {
    private final TransactionHandler transactionHandler;
    private final BudgetHandler budgetHandler;

    @FXML
    private VBox transaction_cards;
    @FXML
    private ListView<Budget> budget_list;
    @FXML
    private ComboBox<FilterType> filter_selection;
    @FXML
    private Button add_transaction_btn;
    @FXML
    private Button add_budget_btn;


    public TransactionsController(TransactionHandler transactionHandler, BudgetHandler budgetHandler) {
        this.transactionHandler = transactionHandler;
        this.budgetHandler = budgetHandler;
    }

    public void initialize() throws IOException {
        filter_selection.setOnAction((event) -> setFilter(filter_selection.getSelectionModel().getSelectedItem()));
        filter_selection.getItems().addAll(new FilterType[]{FilterType.DAY, FilterType.WEEK, FilterType.MONTH, FilterType.YEAR});
        filter_selection.getSelectionModel().selectFirst();

        add_transaction_btn.setOnAction((event) -> addTransaction());
        add_budget_btn.setOnAction((event -> addBudget()));

        try {
            loadTransactions(transactionHandler.getAllTransactionsOn(filter_selection.getSelectionModel().getSelectedItem()));
            loadBudgets();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTransactions(List<Transaction> transactions) throws IOException {
        transaction_cards.getChildren().clear();
        for (Transaction transaction : transactions) {
            FXMLLoader transactionCardLoader = new FXMLLoader(getClass().getResource("../forms/TransactionCard.fxml"));
            transactionCardLoader.setController(new TransactionCardController(transaction, this::deleteTransaction, this::addTransaction));

            transaction_cards.getChildren().add(transactionCardLoader.load());
        }
    }

    private void deleteTransaction(Transaction transaction) {
        transactionHandler.deleteTransaction(transaction.transactionId());
        try {
            loadTransactions(transactionHandler.getAllTransactionsOn(filter_selection.getSelectionModel().getSelectedItem()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFilter(FilterType filter) {
        try {
            loadTransactions(transactionHandler.getAllTransactionsOn(filter));
            loadBudgets();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new transaction
     * @param toEdit Set null for new transactions
     */
    private void addTransaction(Transaction toEdit) {
        FXMLLoader transactionFormLoader = new FXMLLoader(getClass().getResource("../forms/TransactionForm.fxml"));
        TransactionFormController transactionFormController;
        if (toEdit != null) {
            transactionFormController = new TransactionFormController(toEdit);
        } else {
            transactionFormController = new TransactionFormController();
        }

        transactionFormLoader.setController(transactionFormController);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(transactionFormLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dialog.initStyle(StageStyle.UTILITY);
        dialog.showAndWait().ifPresent((buttonType) -> {
            if (buttonType == ButtonType.OK) {
                try {
                    Transaction newTransaction = transactionFormController.getTransaction();
                    if (newTransaction == null) {
                        new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait();
                        addTransaction(toEdit);
                        return;
                    }
                    if (transactionFormController.getTransactionId() != null) {
                        transactionHandler.updateTransaction(transactionFormController.getTransactionId(), newTransaction);
                    } else {
                        transactionHandler.addTransaction(newTransaction);
                    }
                    loadTransactions(transactionHandler.getAllTransactionsOn(filter_selection.getSelectionModel().getSelectedItem()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Used for new transactions
    private void addTransaction() {
        addTransaction(null);
    }

    public void loadBudgets() {
        budget_list.getItems().clear();

        //budget_list.getItems().addAll(budgetHandler.getAllBudgets());
        budget_list.getItems().addAll(budgetHandler.getAllBudgetsOn(filter_selection.getSelectionModel().getSelectedItem()));
    }

    private void addBudget(Budget toEdit) {
        FXMLLoader budgetFormLoader = new FXMLLoader(getClass().getResource("../forms/BudgetForm.fxml"));
        BudgetFormController budgetFormController;
        if (toEdit != null) {
            budgetFormController = new BudgetFormController(toEdit);
        } else {
            budgetFormController = new BudgetFormController();
        }
        budgetFormLoader.setController(budgetFormController);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(budgetFormLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dialog.initStyle(StageStyle.UTILITY);
        dialog.showAndWait().ifPresent((buttonType -> {
            if (buttonType == ButtonType.OK) {
                Budget newBudget = budgetFormController.getBudget();
                if (newBudget == null) {
                    new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait();
                    addBudget(toEdit);
                    return;
                }
                if (budgetFormController.getBudgetId() != null || budgetHandler.getBudget(newBudget.date()) != null) {
                    budgetHandler.updateBudget(budgetFormController.getBudget().date(), newBudget.amount());
                } else {
                    budgetHandler.addBudget(newBudget);
                }
                loadBudgets();
            }
        }));
    }

    public void addBudget() {
        addBudget(null);
    }
}
