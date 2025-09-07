Order Processing System
Overview
This is a simplified, event-driven order processing system implemented in Java, simulating the backend of an e-commerce platform. The system processes order-related events (e.g., order creation, payment receipt, shipping scheduling, and order cancellation) and updates order states accordingly. It uses the observer pattern to notify external systems of significant changes, such as order status updates.
The system reads events from a JSON file (events.txt), processes them using an EventProcessor, and notifies observers (LoggerObserver and AlertObserver) of events and status changes. The project is built with Maven and uses Gson for JSON parsing.
Features

Domain Model: Includes Order, Item, and OrderStatus classes to represent orders and their states.
Event Model: Supports multiple event types (OrderCreatedEvent, PaymentReceivedEvent, ShippingScheduledEvent, OrderCancelledEvent) with a common Event base class.
Event Processing: Processes events to update order states (e.g., PENDING → PAID → SHIPPED or CANCELLED).
Observer Pattern: Notifies observers of events and status changes:
LoggerObserver: Logs all events and status updates to the console.
AlertObserver: Prints alerts for critical status changes (PAID, SHIPPED, CANCELLED).


Event Ingestion: Reads JSON events from a file (events.txt) using Gson.
Error Handling: Gracefully handles unknown event types and missing orders.

Prerequisites

Java: JDK 17 or later (tested with JDK 18.0.2.1).
Maven: For building and dependency management.
IDE: Optional, but recommended (e.g., IntelliJ IDEA, Eclipse, VS Code).
Gson: Included via Maven dependency (com.google.code.gson:gson:2.10.1).

Project Setup

Clone or Create the Project:

If starting fresh, create a Maven project:mvn archetype:generate -DgroupId=org.example -DartifactId=order-processing-system -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false


Alternatively, clone the project repository.


Directory Structure:
order-processing-system/
├── src
│   ├── main
│   │   ├── java
│   │   │   └── org
│   │   │       └── example
│   │   │           ├── model
│   │   │           │   ├── Item.java
│   │   │           │   ├── Order.java
│   │   │           │   └── OrderStatus.java
│   │   │           ├── event
│   │   │           │   ├── Event.java
│   │   │           │   ├── OrderCreatedEvent.java
│   │   │           │   ├── PaymentReceivedEvent.java
│   │   │           │   ├── ShippingScheduledEvent.java
│   │   │           │   └── OrderCancelledEvent.java
│   │   │           ├── observer
│   │   │           │   ├── OrderObserver.java
│   │   │           │   ├── LoggerObserver.java
│   │   │           │   └── AlertObserver.java
│   │   │           ├── processor
│   │   │           │   └── EventProcessor.java
│   │   │           ├── parser
│   │   │           │   └── EventParser.java
│   │   │           └── OrderProcessingSystem.java
│   │   └── resources
│   │       └── events.txt
├── pom.xml
├── README.md


Configure pom.xml:Ensure the pom.xml includes the Gson dependency and Java 17 configuration:
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.example</groupId>
    <artifactId>order-processing-system</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>
    </dependencies>
</project>


Prepare events.txt:Create src/main/resources/events.txt with sample JSON events (one per line):
{"eventId": "e1", "timestamp": "2025-07-29T10:00:00Z", "eventType": "OrderCreated", "orderId": "ORD001", "customerId": "CUST001", "items": [{"itemId": "P001", "qty": 2}], "totalAmount": 100.00}
{"eventId": "e2", "timestamp": "2025-07-29T10:05:00Z", "eventType": "PaymentReceived", "orderId": "ORD001", "amountPaid": 100.00}
{"eventId": "e3", "timestamp": "2025-07-29T10:10:00Z", "eventType": "ShippingScheduled", "orderId": "ORD001", "shippingDate": "2025-07-30"}
{"eventId": "e4", "timestamp": "2025-07-29T10:15:00Z", "eventType": "OrderCreated", "orderId": "ORD002", "customerId": "CUST002", "items": [{"itemId": "P002", "qty": 1}], "totalAmount": 50.00}
{"eventId": "e5", "timestamp": "2025-07-29T10:20:00Z", "eventType": "OrderCancelled", "orderId": "ORD002", "reason": "Customer request"}



Building and Running

Navigate to Project Directory:
cd order-processing-system


Compile the Project:
mvn clean compile


Run the Application:
mvn exec:java -Dexec.mainClass="org.example.OrderProcessingSystem"

Alternatively, in an IDE (e.g., IntelliJ IDEA):

Open the project.
Set the main class to org.example.OrderProcessingSystem.
Run the project.



Expected Output
The program processes events from events.txt and produces output like:
Event processed: OrderCreated for order ORD001. Current status: PENDING
Event processed: PaymentReceived for order ORD001. Current status: PAID
Sending alert for Order ORD001: Status changed to PAID
Event processed: ShippingScheduled for order ORD001. Current status: SHIPPED
Sending alert for Order ORD001: Status changed to SHIPPED
Event processed: OrderCreated for order ORD002. Current status: PENDING
Event processed: OrderCancelled for order ORD002. Current status: CANCELLED
Sending alert for Order ORD002: Status changed to CANCELLED

Final orders state:
Order ORD001: Status = SHIPPED, Events = 3
Order ORD002: Status = CANCELLED, Events = 2

Code Structure

Model (org.example.model):
Item: Represents an order item with ID and quantity.
Order: Represents an order with ID, customer ID, items, total amount, status, and event history.
OrderStatus: Enum for order statuses (PENDING, PAID, PARTIALLY_PAID, SHIPPED, CANCELLED).


Event (org.example.event):
Event: Abstract base class for events with ID, timestamp, and type.
OrderCreatedEvent, PaymentReceivedEvent, ShippingScheduledEvent, OrderCancelledEvent: Concrete event types.


Observer (org.example.observer):
OrderObserver: Interface for observers.
LoggerObserver: Logs all events and status changes.
AlertObserver: Alerts for critical status changes (PAID, SHIPPED, CANCELLED).


Processor (org.example.processor):
EventProcessor: Processes events, updates order states, and notifies observers.


Parser (org.example.parser):
EventParser: Parses JSON events from a file into event objects.


Main (org.example):
OrderProcessingSystem: Main class to load events, process them, and display the final state.



Notes

Error Handling: The system skips invalid JSON events and logs warnings for unknown event types or missing orders.
Extensibility: Add new event types by creating new Event subclasses and updating EventParser and EventProcessor.
Logging: Console logging is used for simplicity. For production, consider a logging framework like SLF4J.
File Path: Ensure events.txt is in src/main/resources/. Adjust the path in OrderProcessingSystem.java if needed.
Testing: Add unit tests in src/test/java using JUnit to verify event processing and parsing logic.

Troubleshooting

File Not Found: Verify events.txt is in src/main/resources/.
Dependency Issues: Run mvn clean install to download dependencies.
Compilation Errors: Ensure all classes have correct package declarations (org.example.*) and match the directory structure.


