package org.example.model;

import org.example.event.Event;
import java.util.*;

public class Order {
    private String orderId;
    private String customerId;
    private List<Item> items;
    private double totalAmount;
    private OrderStatus status;
    private List<Event> eventHistory;

    public Order(String orderId, String customerId, List<Item> items, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
        this.eventHistory = new ArrayList<>();
    }

    public String getOrderId() {
        return orderId;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<Event> getEventHistory() {
        return eventHistory;
    }

    public void addEvent(Event event) {
        eventHistory.add(event);
    }
}
