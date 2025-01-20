package org.CashCatalysts.CashCatalysts.datatypes;

/**
 * Class for managing the currency
 */
public class Currency {
    private final int amountCents;

    /**
     * Constructor for separated amount and cents
     */
    public Currency(int amount, int cents) {
        this.amountCents = amount * 100 + cents;
    }

    /**
     * Constructor for combined amount and cents
     * Generally used for database
     */
    public Currency(int amountCents) {
        this.amountCents = amountCents;
    }

    public int getAmount() {
        return amountCents / 100;
    }

    public int getCents() {
        return amountCents % 100;
    }

    // Generally used for database
    public int getAmountCents() {
        return amountCents;
    }

    @Override
    public boolean equals(Object currency) {
        if (currency == null) {
            return false;
        }
        if (currency.getClass() != this.getClass()) {
            return false;
        }
        return ((Currency) currency).amountCents == amountCents;
    }

    @Override
    public String toString() {
        return String.format("$%d.%02d", getAmount(), getCents());
    }
}
