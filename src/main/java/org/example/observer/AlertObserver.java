package org.example.observer;

import org.example.event.Event;
import org.example.model.Order;
import org.example.model.OrderStatus;

public class AlertObserver implements OrderObserver {
    @Override
    public void onEventProcessed(Order order, Event event) {
        if (event.getEventType().equals("OrderCancelled") ||
                order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.CANCELLED ||
                order.getStatus() == OrderStatus.PAID) {
            System.out.println("Sending alert for Order " + order.getOrderId() + ": Status changed to " + order.getStatus());
        }
    }
}