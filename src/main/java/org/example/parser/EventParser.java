package org.example.parser;

import org.example.event.*;
import org.example.model.Item;
import com.google.gson.*;
import java.io.*;
import java.util.*;

public class EventParser {
    public static List<Event> parseEvents(String filename) {
        List<Event> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                JsonElement je = JsonParser.parseString(line);
                if (!je.isJsonObject()) continue;
                JsonObject json = je.getAsJsonObject();
                if (!json.has("eventType")) continue;

                String eventId = json.get("eventId").getAsString();
                String timestamp = json.get("timestamp").getAsString();
                String eventType = json.get("eventType").getAsString();
                Event event = null;

                switch (eventType) {
                    case "OrderCreated":
                        if (!json.has("orderId") || !json.has("customerId") || !json.has("totalAmount")) break;
                        String orderIdCreated = json.get("orderId").getAsString();
                        String customerId = json.get("customerId").getAsString();
                        double totalAmount = json.get("totalAmount").getAsDouble();
                        List<Item> items = new ArrayList<>();
                        if (json.has("items")) {
                            JsonArray itemsArray = json.getAsJsonArray("items");
                            for (JsonElement itemEl : itemsArray) {
                                if (!itemEl.isJsonObject()) continue;
                                JsonObject itemObj = itemEl.getAsJsonObject();
                                if (itemObj.has("itemId") && itemObj.has("qty")) {
                                    String itemId = itemObj.get("itemId").getAsString();
                                    int qty = itemObj.get("qty").getAsInt();
                                    items.add(new Item(itemId, qty));
                                }
                            }
                        }
                        OrderCreatedEvent createdEvent = new OrderCreatedEvent(eventId, timestamp);
                        createdEvent.setOrderId(orderIdCreated);
                        createdEvent.setCustomerId(customerId);
                        createdEvent.setItems(items);
                        createdEvent.setTotalAmount(totalAmount);
                        event = createdEvent;
                        break;
                    case "PaymentReceived":
                        if (!json.has("orderId") || !json.has("amountPaid")) break;
                        String orderIdPayment = json.get("orderId").getAsString();
                        double amountPaid = json.get("amountPaid").getAsDouble();
                        PaymentReceivedEvent paymentEvent = new PaymentReceivedEvent(eventId, timestamp);
                        paymentEvent.setOrderId(orderIdPayment);
                        paymentEvent.setAmountPaid(amountPaid);
                        event = paymentEvent;
                        break;
                    case "ShippingScheduled":
                        if (!json.has("orderId") || !json.has("shippingDate")) break;
                        String orderIdShipping = json.get("orderId").getAsString();
                        String shippingDate = json.get("shippingDate").getAsString();
                        ShippingScheduledEvent shippingEvent = new ShippingScheduledEvent(eventId, timestamp);
                        shippingEvent.setOrderId(orderIdShipping);
                        shippingEvent.setShippingDate(shippingDate);
                        event = shippingEvent;
                        break;
                    case "OrderCancelled":
                        if (!json.has("orderId") || !json.has("reason")) break;
                        String orderIdCancel = json.get("orderId").getAsString();
                        String reason = json.get("reason").getAsString();
                        OrderCancelledEvent cancelEvent = new OrderCancelledEvent(eventId, timestamp);
                        cancelEvent.setOrderId(orderIdCancel);
                        cancelEvent.setReason(reason);
                        event = cancelEvent;
                        break;
                    default:
                        break;
                }
                if (event != null) {
                    events.add(event);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return events;
    }
}