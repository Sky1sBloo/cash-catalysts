package org.CashCatalysts.CashCatalysts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.CashCatalysts.CashCatalysts.GoalsSavings.GoalsHandler;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.UserStats.UserStatsSystem;
import org.CashCatalysts.CashCatalysts.budgets.BudgetHandler;
import org.CashCatalysts.CashCatalysts.game.UserGameStatsHandler;
import org.CashCatalysts.CashCatalysts.game.challenges.ChallengeHandler;
import org.CashCatalysts.CashCatalysts.game.chests.ChestHandler;
import org.CashCatalysts.CashCatalysts.game.plants.PlantsHandler;
import org.CashCatalysts.CashCatalysts.subscriptions.SubscriptionsHandler;
import java.io.IOException;
import java.time.LocalDate;

public class MainWindowController {
    private final TransactionHandler transactionHandler;
    private final BudgetHandler budgetHandler;
    private final GoalsHandler goalsHandler;
    private final UserStatsSystem userStatsSystem;
    private final SubscriptionsHandler subscriptionsHandler;
    private final ChallengeHandler challengeHandler;
    private final UserGameStatsHandler userGameStatsHandler;
    private final PlantsHandler plantsHandler;
    private final ChestHandler chestHandler;

    @FXML
    private Pane main_root;
    @FXML
    private Pane main_pane;
    @FXML
    private Pane nav_menu;
    @FXML
    private ListView<String> challenge_list;

    public MainWindowController(TransactionHandler transactionHandler,
                                BudgetHandler budgetHandler,
                                GoalsHandler goalsHandler,
                                UserStatsSystem userStatsSystem,
                                SubscriptionsHandler subscriptionsHandler, ChallengeHandler challengeHandler, UserGameStatsHandler userGameStatsHandler, PlantsHandler plantsHandler, ChestHandler chestHandler) {
        this.transactionHandler = transactionHandler;
        this.budgetHandler = budgetHandler;
        this.goalsHandler = goalsHandler;
        this.userStatsSystem = userStatsSystem;
        this.subscriptionsHandler = subscriptionsHandler;
        this.challengeHandler = challengeHandler;
        this.userGameStatsHandler = userGameStatsHandler;
        this.plantsHandler = plantsHandler;
        this.chestHandler = chestHandler;
    }

    @SuppressWarnings("unused")
    public void initialize() throws IOException {
        nav_menu.setVisible(false);
        loadPage("../forms/Garden.fxml", new GardenController(userGameStatsHandler, plantsHandler));
    }

    private void refresh() {
        loadDailyChallenges();
    }

    @SuppressWarnings("unused")
    public void toggleMenu(ActionEvent ignore) {
        nav_menu.setVisible(!nav_menu.isVisible());
    }

    private void loadPage(String path) throws IOException {
        nav_menu.setVisible(false);
        main_pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        main_pane.getChildren().add(loader.load());
        refresh();
    }

    private void loadPage(String path, Object controller) throws IOException {
        nav_menu.setVisible(false);
        main_pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setController(controller);
        main_pane.getChildren().add(loader.load());
        refresh();
    }

    private void loadDailyChallenges() {
        challenge_list.getItems().clear();
        challengeHandler.getDailyChallengesOnDate(LocalDate.now()).forEach(challenge -> challenge_list.getItems().add(challenge.name()));
    }

    @SuppressWarnings("unused")
    public void onTransactionsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Transactions.fxml", new TransactionsController(transactionHandler, budgetHandler, userStatsSystem));
    }

    @SuppressWarnings("unused")
    public void onDashboardClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Garden.fxml", new GardenController(userGameStatsHandler, plantsHandler));
        //loadPage("../forms/Dashboard.fxml", new DashboardController(transactionHandler));
    }

    @SuppressWarnings("unused")
    public void onAccountsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/AccountManagement.fxml");
    }

    @SuppressWarnings("unused")
    public void onSubscriptionsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Subscriptions.fxml", new SubscriptionsController(subscriptionsHandler));
    }

    @SuppressWarnings("unused")
    public void onAnalyticsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Analytics.fxml", new AnalysisController(userStatsSystem, transactionHandler));
    }

    @SuppressWarnings("unused")
    public void onGoalsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Goals.fxml", new GoalsController(goalsHandler, transactionHandler, budgetHandler));
    }

    @SuppressWarnings("unused")
    public void onChallengesClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Challenges.fxml", new ChallengesController(challengeHandler, userGameStatsHandler));
    }

    @SuppressWarnings("unused")
    public void onInventoryClick(ActionEvent ignore) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/Inventory.fxml"));
        InventoryController controller = new InventoryController(userGameStatsHandler, plantsHandler);
        loader.setController(controller);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(loader.load());
        dialog.setOnCloseRequest(e -> dialog.close());
        dialog.showAndWait();
        /*loadPage("../forms/Inventory.fxml", new InventoryController(userGameStatsHandler, plantsHandler)); */
    }

    @SuppressWarnings("unused")
    public void onMarketClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Market.fxml", new MarketController(chestHandler, userGameStatsHandler));
    }
}
