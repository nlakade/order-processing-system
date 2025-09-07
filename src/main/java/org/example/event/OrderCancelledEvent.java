package org.example.event;

public class OrderCancelledEvent extends Event {
    private String reason;

    public OrderCancelledEvent(String eventId, String timestamp) {
        super(eventId, timestamp, "OrderCancelled");
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}