package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.subscriptions.Subscription;

public class SubscriptionsCardController {
    @FXML
    public Label name_lbl;
    @FXML
    public Label type_lbl;
    @FXML
    public Label frequency_lbl;
    @FXML
    public Label amount_lbl;
    @FXML
    public Label start_date_lbl;
    @FXML
    public Label end_date_lbl;

    private final Subscription subscription;

    public SubscriptionsCardController(Subscription subscription) {
        this.subscription = subscription;
    }

    public void initialize() {
        name_lbl.setText(subscription.name());
        type_lbl.setText(subscription.type().toString());
        frequency_lbl.setText(subscription.frequency().toString());
        amount_lbl.setText(subscription.amount().toString());
        start_date_lbl.setText(subscription.startDate().toString());
        end_date_lbl.setText(subscription.endDate().toString());
    }
}
