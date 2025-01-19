package org.CashCatalysts.CashCatalysts.GoalsSavings;

import java.sql.Date;

public record Goals(int id, String name, int targetAmount, int targetAmountCents, Date deadline, String type) {

    @Override
    public String toString() {
        return "Goals[" +
                "ID: " + id + ", " +
                "Name: " + name + ", " +
                "TargetAmount: " + targetAmount + ", " +
                "TargetAmountCents: " + targetAmountCents + ", " +
                "Deadline: " + deadline + ", " +
                "Type: " + type + ']';
    }

    public Date getDeadline() {
        return deadline;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public int getTargetAmountCents() {
        return targetAmountCents;
    }
}