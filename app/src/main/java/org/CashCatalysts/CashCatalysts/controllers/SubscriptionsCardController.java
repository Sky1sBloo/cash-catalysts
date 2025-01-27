package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.CashCatalysts.CashCatalysts.subscriptions.Subscription;

import java.util.function.Consumer;

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
    @FXML
    public Button delete_btn;
    @FXML
    public Button edit_btn;

    private final Subscription subscription;
    private final Consumer<Subscription> deleteCallback;
    private final Consumer<Subscription> editCallback;

    public SubscriptionsCardController(Subscription subscription, Consumer<Subscription> deleteCallback, Consumer<Subscription> editCallback) {
        this.subscription = subscription;
        this.deleteCallback = deleteCallback;
        this.editCallback = editCallback;
    }

    public void initialize() {
        name_lbl.setText(subscription.name());
        type_lbl.setText(subscription.type().toString());
        frequency_lbl.setText(subscription.frequency().toString());
        amount_lbl.setText(subscription.amount().toString());
        start_date_lbl.setText(subscription.startDate().toString());
        end_date_lbl.setText(subscription.endDate().toString());

        delete_btn.setOnAction(ignore -> deleteCallback.accept(subscription));
        edit_btn.setOnAction(ignore -> editCallback.accept(subscription));
    }
}
