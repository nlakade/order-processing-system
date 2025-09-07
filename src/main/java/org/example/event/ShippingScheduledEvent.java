package org.example.event;

public class ShippingScheduledEvent extends Event {
    private String shippingDate;

    public ShippingScheduledEvent(String eventId, String timestamp) {
        super(eventId, timestamp, "ShippingScheduled");
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingDate() {
        return shippingDate;
    }
}
