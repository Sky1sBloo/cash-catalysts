package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import org.CashCatalysts.CashCatalysts.subscriptions.Subscription;
import org.CashCatalysts.CashCatalysts.subscriptions.SubscriptionsHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class SubscriptionsController {
    private final SubscriptionsHandler subscriptionsHandler;

    @FXML
    public VBox subscriptions_list;
    @FXML
    public Button add_subscription_btn;

    public SubscriptionsController(SubscriptionsHandler subscriptionsHandler) {
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        add_subscription_btn.setOnAction(e -> addSubscription());
        refresh();
    }

    public void refresh() {
        try {
            loadSubscriptions(subscriptionsHandler.getAllSubscriptions());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSubscriptions(List<Subscription> subscriptions) throws IOException {
        subscriptions_list.getChildren().clear();
        for (Subscription subscription : subscriptions) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/SubscriptionsCard.fxml"));
            loader.setController(new SubscriptionsCardController(subscription));
            subscriptions_list.getChildren().add(loader.load());
        }
    }

    /**
     * Opens the subscription form to add a new subscription
     */
    public void addSubscription() {
        addSubscription(null);
    }

    /**
     * Opens the subscription form to add a new subscription
     * @param toEdit The subscription to edit
     */
    public void addSubscription(Subscription toEdit) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/SubscriptionsForm.fxml"));

        SubscriptionsFormController controller;
        if (toEdit != null) {
            controller = new SubscriptionsFormController(toEdit);
        } else {
            controller = new SubscriptionsFormController();
        }

        loader.setController(controller);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dialog.initStyle(StageStyle.UTILITY);
        dialog.showAndWait().ifPresent((buttonType) -> {
            if (buttonType == ButtonType.OK) {
                Subscription newSubscription = controller.getSubscription();
                if (newSubscription == null) {
                    new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait();
                    addSubscription(toEdit);
                    return;
                }
                if (controller.getSubscriptionId() != null) {
                    subscriptionsHandler.editSubscription(newSubscription);
                    subscriptionsHandler.deleteAllTransactionsOnSubscriptionAfterDate(newSubscription, LocalDate.now());
                    subscriptionsHandler.addTransactionForSubscription(newSubscription, LocalDate.now());
                } else {
                    int id = subscriptionsHandler.addSubscription(newSubscription);
                    Subscription toAddSubscription = subscriptionsHandler.getSubscription(id);
                    subscriptionsHandler.addTransactionForSubscription(toAddSubscription);
                }
                refresh();
            }
        });
    }
}
