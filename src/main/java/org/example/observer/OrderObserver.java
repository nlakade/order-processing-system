package org.example.observer;

import org.example.event.Event;
import org.example.model.Order;

public interface OrderObserver {
    void onEventProcessed(Order order, Event event);
}
