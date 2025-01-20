package org.CashCatalysts.CashCatalysts.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.CashCatalysts.CashCatalysts.budgets.Budget;
import org.CashCatalysts.CashCatalysts.datatypes.Currency;

import java.sql.Date;

public class BudgetFormController {
    @FXML
    private DatePicker date_selector;
    @FXML
    private TextField amount_txtbx;

    private final Integer transactionId;
    private final Budget budget;

    public BudgetFormController() {
        transactionId = null;
        budget = null;
    }

    public BudgetFormController(Budget budget) {
        transactionId = budget.id();
        this.budget = budget;
    }

    public void initialize() {
        if (budget != null) {
            date_selector.setValue(budget.date().toLocalDate());
            amount_txtbx.setText(budget.amount().getAmount() + "." + budget.amount().getCents());
        }
    }

    public Budget getBudget() {
        if (amount_txtbx.getText().isEmpty()
                || !amount_txtbx.getText().matches("\\d+(\\.\\d+)?")) {
            return null;
        }
        if (date_selector.getValue() == null) {
            return null;
        }
        int amountCents = (int) (Double.parseDouble(amount_txtbx.getText()) * 100);
        return new Budget(transactionId, Date.valueOf(date_selector.getValue()), new Currency(amountCents));
    }

    public Integer getBudgetId() {
        return transactionId;
    }
}
