package org.example.event;

import org.example.model.Item;
import java.util.*;

public class OrderCreatedEvent extends Event {
    private String customerId;
    private List<Item> items;
    private double totalAmount;

    public OrderCreatedEvent(String eventId, String timestamp) {
        super(eventId, timestamp, "OrderCreated");
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
