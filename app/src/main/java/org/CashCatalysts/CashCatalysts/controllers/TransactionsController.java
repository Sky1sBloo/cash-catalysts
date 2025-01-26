package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import org.CashCatalysts.CashCatalysts.datatypes.DateFilterHandler;
import org.CashCatalysts.CashCatalysts.datatypes.DateFilterType;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.budgets.Budget;
import org.CashCatalysts.CashCatalysts.budgets.BudgetHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Class for the TransactionsController
 */
public class TransactionsController {
    private final TransactionHandler transactionHandler;
    private final BudgetHandler budgetHandler;
    private DateFilterType dateFilterType;
    private final UserStatsSystem userStatsSystem;

    @FXML
    private Label daily_budget_lbl;
    @FXML
    private VBox transaction_cards;
    @FXML
    private ListView<Budget> budget_list;
    @FXML
    private ComboBox<DateFilterType> filter_selection;
    @FXML
    private Button add_transaction_btn;
    @FXML
    private Button add_budget_btn;
    @FXML
    private Label number_of_transactions;
    @FXML
    private Label savings_lbl;


    public TransactionsController(TransactionHandler transactionHandler, BudgetHandler budgetHandler, UserStatsSystem userStatsSystem) {
        this.transactionHandler = transactionHandler;
        this.budgetHandler = budgetHandler;
        this.userStatsSystem = userStatsSystem;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        filter_selection.setOnAction((ignore) -> setFilter(filter_selection.getSelectionModel().getSelectedItem()));
        filter_selection.getItems().addAll(DateFilterType.DAY, DateFilterType.WEEK, DateFilterType.MONTH, DateFilterType.YEAR);
        filter_selection.getSelectionModel().selectFirst();
        dateFilterType = filter_selection.getSelectionModel().getSelectedItem();

        add_transaction_btn.setOnAction((ignore) -> addTransaction());
        add_budget_btn.setOnAction((ignore) -> addBudget());

        refresh();
    }

    private void refresh() {
        try {
            loadTransactions(transactionHandler.getAllTransactionsOn(dateFilterType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadBudgets();
        Budget dailyBudget = budgetHandler.getBudget(LocalDate.now());
        if (dailyBudget != null) {
            daily_budget_lbl.setText(dailyBudget.amount().toString());
        } else {
            daily_budget_lbl.setText("None");
        }
        number_of_transactions.setText(String.valueOf(transactionHandler.getAllTransactionsOn(dateFilterType).size()));
        savings_lbl.setText(userStatsSystem.getSavings(DateFilterHandler.getDateRangeFromFilterType(dateFilterType)).toString());
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
            loadTransactions(transactionHandler.getAllTransactionsOn(dateFilterType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFilter(DateFilterType filter) {
        dateFilterType = filter;
        refresh();
    }

    /**
     * Adds a new transaction
     *
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
                refresh();
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
        budget_list.getItems().addAll(budgetHandler.getAllBudgetsOn(dateFilterType));
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
                refresh();
            }
        }));
    }

    public void addBudget() {
        addBudget(null);
    }
}
