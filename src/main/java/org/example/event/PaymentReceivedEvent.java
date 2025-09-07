package org.example.event;

public class PaymentReceivedEvent extends Event {
    private double amountPaid;

    public PaymentReceivedEvent(String eventId, String timestamp) {
        super(eventId, timestamp, "PaymentReceived");
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getAmountPaid() {
        return amountPaid;
    }
}