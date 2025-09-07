package org.example.observer;

import org.example.event.Event;
import org.example.model.Order;

public class LoggerObserver implements OrderObserver {
    @Override
    public void onEventProcessed(Order order, Event event) {
        System.out.println("Event processed: " + event.getEventType() + " for order " + order.getOrderId() + ". Current status: " + order.getStatus());
    }
}
