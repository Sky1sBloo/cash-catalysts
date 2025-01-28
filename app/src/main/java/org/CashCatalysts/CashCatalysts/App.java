package org.CashCatalysts.CashCatalysts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.CashCatalysts.CashCatalysts.Database.DatabaseHandler;
import org.CashCatalysts.CashCatalysts.GoalsSavings.GoalsHandler;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.UserProfile.UsersHandler;
import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.budgets.BudgetHandler;
import org.CashCatalysts.CashCatalysts.controllers.MainWindowController;
import org.CashCatalysts.CashCatalysts.game.LandHandler;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeHandler;
import org.CashCatalysts.CashCatalysts.game.gameaction.GameActionHandler;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;
import org.CashCatalysts.CashCatalysts.subscriptions.SubscriptionsHandler;

import java.util.Objects;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseHandler databaseHandler = new DatabaseHandler(":memory:");
        TransactionHandler transactionHandler = new TransactionHandler(databaseHandler);
        UsersHandler usersHandler = new UsersHandler(databaseHandler);
        if (usersHandler.getUser(0) == null) {
            usersHandler.registerUser(UsersHandler.createUser("user"));
        }
        BudgetHandler budgetHandler = new BudgetHandler(databaseHandler);
        GoalsHandler goalsHandler = new GoalsHandler(databaseHandler);
        UserStatsSystem userStatsSystem = new UserStatsSystem(transactionHandler, budgetHandler);
        SubscriptionsHandler subscriptionsHandler = new SubscriptionsHandler(databaseHandler, transactionHandler);

        GameActionHandler gameActionHandler = new GameActionHandler(databaseHandler, 0);
        ChallengeHandler challengeHandler = new ChallengeHandler(databaseHandler, userStatsSystem);
        LandHandler landHandler = new LandHandler(0, databaseHandler);
        PlantsHandler plantsHandler = new PlantsHandler(0, databaseHandler);

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("forms/Main.fxml")));
        MainWindowController controller = new MainWindowController(transactionHandler, budgetHandler, goalsHandler, userStatsSystem, subscriptionsHandler, challengeHandler);
        loader.setController(controller);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Cash Catalysts");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
