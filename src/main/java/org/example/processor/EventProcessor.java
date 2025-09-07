package org.example.processor;

import org.example.event.*;
import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.observer.OrderObserver;
import java.util.*;

public class EventProcessor {
    private Map<String, Order> orders;
    private List<OrderObserver> observers;

    public EventProcessor() {
        this.orders = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public Map<String, Order> getOrders() {
        return orders;
    }

    public void process(Event event) {
        String orderId = event.getOrderId();
        Order order = orders.get(orderId);
        OrderStatus oldStatus = (order != null) ? order.getStatus() : null;

        if (event instanceof OrderCreatedEvent) {
            OrderCreatedEvent createdEvent = (OrderCreatedEvent) event;
            order = new Order(createdEvent.getOrderId(), createdEvent.getCustomerId(),
                    createdEvent.getItems(), createdEvent.getTotalAmount());
            order.addEvent(event);
            orders.put(orderId, order);
        } else {
            if (order == null) {
                System.out.println("Warning: No order found for event " + event.getEventType() + " on order " + orderId);
                return;
            }
            order.addEvent(event);
            if (event instanceof PaymentReceivedEvent) {
                PaymentReceivedEvent paymentEvent = (PaymentReceivedEvent) event;
                if (paymentEvent.getAmountPaid() >= order.getTotalAmount()) {
                    order.setStatus(OrderStatus.PAID);
                } else {
                    order.setStatus(OrderStatus.PARTIALLY_PAID);
                }
            } else if (event instanceof ShippingScheduledEvent) {
                order.setStatus(OrderStatus.SHIPPED);
            } else if (event instanceof OrderCancelledEvent) {
                order.setStatus(OrderStatus.CANCELLED);
            } else {
                System.out.println("Unsupported event type: " + event.getEventType());
                return;
            }
        }

        // Notify observers
        for (OrderObserver observer : observers) {
            observer.onEventProcessed(order, event);
        }
    }
}
