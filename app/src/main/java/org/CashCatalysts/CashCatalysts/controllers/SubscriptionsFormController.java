package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionType;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;
import org.CashCatalysts.CashCatalysts.subscriptions.Subscription;
import org.CashCatalysts.CashCatalysts.subscriptions.SubscriptionFrequency;

public class SubscriptionsFormController {
    private final Integer subscriptionId;  // For editing subscriptions. set null for new subscriptions

    @FXML
    private TextField name_txtbx;
    @FXML
    private ComboBox<TransactionType> type_selector;
    @FXML
    private ComboBox<SubscriptionFrequency> frequency_selector;
    @FXML
    private TextField amount_txtbx;
    @FXML
    private DatePicker start_date_selector;
    @FXML
    private DatePicker end_date_selector;

    private final Subscription subscription;

    public SubscriptionsFormController() {
        subscriptionId = null;
        subscription = null;
    }

    public SubscriptionsFormController(Subscription subscription) {
        subscriptionId = subscription.id();
        this.subscription = subscription;
    }

    @SuppressWarnings("unused")
    public void initialize() {
        type_selector.getItems().addAll(TransactionType.values());
        frequency_selector.getItems().addAll(SubscriptionFrequency.values());

        if (subscription != null) {
            name_txtbx.setText(subscription.name());
            type_selector.setValue(subscription.type());
            frequency_selector.setValue(subscription.frequency());
            start_date_selector.setValue(subscription.startDate());
            end_date_selector.setValue(subscription.endDate());
            amount_txtbx.setText(subscription.amount().getAmount() + "." + subscription.amount().getCents());
        }
    }

    public Subscription getSubscription() {
        if (name_txtbx.getText().isEmpty()) {
            return null;
        }
        if (amount_txtbx.getText().isEmpty()
                || !amount_txtbx.getText().matches("\\d+(\\.\\d+)?")) {
            return null;
        }
        if (type_selector.getSelectionModel().isEmpty()) {
            return null;
        }
        if (frequency_selector.getSelectionModel().isEmpty()) {
            return null;
        }
        if (start_date_selector.getValue() == null) {
            return null;
        }
        if (end_date_selector.getValue() == null) {
            return null;
        }
        int amountCents = (int) (Double.parseDouble(amount_txtbx.getText()) * 100);
        return new Subscription(
                subscriptionId,
                name_txtbx.getText(),
                type_selector.getSelectionModel().getSelectedItem(),
                frequency_selector.getSelectionModel().getSelectedItem(),
                start_date_selector.getValue(),
                end_date_selector.getValue(),
                new Currency(amountCents)
        );
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }
}
