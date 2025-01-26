package org.CashCatalysts.CashCatalysts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.GoalsSavings.GoalsHandler;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionExamples;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.budgets.Budget;
import org.CashCatalysts.CashCatalysts.budgets.BudgetHandler;
import org.CashCatalysts.CashCatalysts.budgets.BudgetTests;
import org.CashCatalysts.CashCatalysts.controllers.MainWindowController;

import java.util.Objects;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseHandler databaseHandler = new DatabaseHandler(":memory:");
        TransactionHandler transactionHandler = new TransactionHandler(databaseHandler);
        BudgetHandler budgetHandler = new BudgetHandler(databaseHandler);
        GoalsHandler goalsHandler = new GoalsHandler(databaseHandler);
        UserStatsSystem userStatsSystem = new UserStatsSystem(transactionHandler, budgetHandler);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("forms/Main.fxml")));
        MainWindowController controller = new MainWindowController(transactionHandler, budgetHandler, goalsHandler, userStatsSystem);
        loader.setController(controller);

        /*
        for (Transaction transaction : TransactionExamples.getExampleTransactions()) {
            transactionHandler.addTransaction(transaction);
        }
        for (Budget budget : BudgetTests.createTestBudgets()) {
            budgetHandler.addBudget(budget);
        } */
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Cash Catalysts");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
