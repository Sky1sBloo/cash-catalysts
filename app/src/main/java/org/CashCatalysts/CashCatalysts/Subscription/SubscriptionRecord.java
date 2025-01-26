package org.CashCatalysts.CashCatalysts.Subscription;
import java.sql.Date;


public class SubscriptionRecord {
    private int id;
    private String name;
    private String type;
    private String paymentTime;  // Daily, Monthly, Yearly
    private Date startDate;
    private Date endDate;
    private int startAmount;
    private int amountCents;

    // Constructor
    public SubscriptionRecord(int id, String name, String type, String paymentTime, Date startDate, Date endDate, int startAmount, int amountCents) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.paymentTime = paymentTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startAmount = startAmount;
        this.amountCents = amountCents;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(int startAmount) {
        this.startAmount = startAmount;
    }

    public int getAmountCents() {
        return amountCents;
    }

    public void setAmountCents(int amountCents) {
        this.amountCents = amountCents;
    }

    // toString, equals, and hashCode methods
    @Override
    public String toString() {
        return "SubscriptionRecord{id=" + id + ", name='" + name + "', type='" + type + "', paymentTime='" + paymentTime + "', startDate=" + startDate + ", endDate=" + endDate + ", startAmount=" + startAmount + ", amountCents=" + amountCents + '}';
    }

}

