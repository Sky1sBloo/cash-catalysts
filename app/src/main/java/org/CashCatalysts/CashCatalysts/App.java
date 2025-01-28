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
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
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
        int userId = 0;
        if (usersHandler.getUser(userId) == null) {
            userId = usersHandler.registerUser(UsersHandler.createUser("user"));
        }
        UserGameStatsHandler userGameStatsHandler = new UserGameStatsHandler(userId, databaseHandler);
        BudgetHandler budgetHandler = new BudgetHandler(databaseHandler);
        GoalsHandler goalsHandler = new GoalsHandler(databaseHandler);
        UserStatsSystem userStatsSystem = new UserStatsSystem(transactionHandler, budgetHandler);
        SubscriptionsHandler subscriptionsHandler = new SubscriptionsHandler(databaseHandler, transactionHandler);

        GameActionHandler gameActionHandler = new GameActionHandler(databaseHandler, userId);
        ChallengeHandler challengeHandler = new ChallengeHandler(databaseHandler, userStatsSystem, gameActionHandler);
        LandHandler landHandler = new LandHandler(userId, databaseHandler);
        PlantsHandler plantsHandler = new PlantsHandler(userId, databaseHandler);

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("forms/Main.fxml")));
        MainWindowController controller = new MainWindowController(transactionHandler,
                budgetHandler,
                goalsHandler,
                userStatsSystem,
                subscriptionsHandler,
                challengeHandler,
                userGameStatsHandler);
        loader.setController(controller);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Cash Catalysts");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
