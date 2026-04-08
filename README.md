# Enterprise E-Ticketing Data Management System

## Project Overview
The Enterprise E-Ticketing System is a lightweight, secure Java desktop application architected to facilitate streamlined booking operations. Built using Java Swing, the system integrates a robust NoSQL persistence layer via **MongoDB** and features automated downstream document generation using the **iTextPDF** API. The application is designed to operate locally with optimal resource efficiency, utilizing dedicated custom port binding for isolated database telemetry.

## Core Features & Architecture

* **Authentication Gateway:** Implements a decoupled `LoginScreen` module functioning as the primary authentication gateway before initializing the primary `ETicketingSystem` container, ensuring authorized-only access.
* **Document-Oriented Persistence:** Leverages MongoDB's schema-less document architecture to store transactional booking data. The integration utilizes `mongodb-driver-sync` to instantiate a direct `MongoClient` connection stream, ensuring reliable data commits to the `TicketDB` operational database.
* **Isolated Environment Context:** The MongoDB instance is explicitly configured to listen on a non-standard port (`27020`). This decoupled architecture prevents port collision with overlapping development clusters standardly bound to `27017`.
* **Automated PDF Rendering:** Integrates `iTextPDF` to serialize transactional confirmation strings from the Java UI (`JTextArea`) directly into immutable `.pdf` artifacts. The generated file leverages `FileOutputStream` to execute localized I/O processes sequentially upon user instruction.
* **Event-Driven UI:** The graphical interface uses `JFrame` components engineered with the `ActionListener` interface to handle asynchronous UI thread events synchronously (e.g., commit validation and interface clearing).

## System Architecture Requirements

* **Language:** Java 8+ (JDK)
* **Datastore:** MongoDB (Local Cluster)
* **Dependencies:** 
  * `mongo-java-driver-3.12.14.jar` (NoSQL interaction)
  * `itextpdf-5.5.13.3.jar` (PDF Generation)

## Build & Execution Instructions

This project does not rely on transient build orchestration layers like Maven or Gradle; it compiles and executes directly via localized classpath mapping.

### 1. Database Initialization
Ensure the MongoDB daemon is active and bound to the customized project port by executing the following in your CLI:
```bash
mongod --dbpath ./mongodb_data --port 27020
```

### 2. Compilation
To compile the `.java` files within the JVM, map the classpath (`-cp`) to the included `libs/` directory containing the dependency archives:
```bash
javac -cp "libs/*:." ETicketingSystem.java
```

### 3. Execution
Launch the Java virtual machine with the mapped external configurations:
```bash
java -cp "libs/*:." ETicketingSystem
```

Upon successful compilation and execution, the isolated login gateway will validate credentials prior to permitting database access configuration.
