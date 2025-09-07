package org.example;

import org.example.event.Event;
import org.example.model.Order;
import org.example.parser.EventParser;
import org.example.processor.EventProcessor;
import org.example.observer.LoggerObserver;
import org.example.observer.AlertObserver;
import java.util.*;

public class OrderProcessingSystem {
    public static void main(String[] args) {
        List<Event> events = EventParser.parseEvents("src/main/resources/events.txt");
        EventProcessor processor = new EventProcessor();
        processor.addObserver(new LoggerObserver());
        processor.addObserver(new AlertObserver());

        for (Event event : events) {
            processor.process(event);
        }

        System.out.println("\nFinal orders state:");
        for (Order order : processor.getOrders().values()) {
            System.out.println("Order " + order.getOrderId() + ": Status = " + order.getStatus() + ", Events = " + order.getEventHistory().size());
        }
    }
}