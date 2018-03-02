# Broker-Lite

#### Connect account managers to their customers assets quickly and reliably.

## Contribution Requirements/Dependencies

* Requires JDK-8.
* Download e(fx)clipse plugin in Eclipse Marketplace.
* Manually interact with SQL-lite database "BrokerLite.sqlite" via DB manager such as 'DB Browser for SQLite".
* Upon execution if connection status reads "not connected" or error thrown add lib directory to build path.
* Execute by running Main.java
* Edit '.fxml' documents for GUI via Oracle Scene Builder v 2.0+ only.

## Current Models:
* See database schema in proposal directory.
* A test user has been generated with full set of data; login with "test", "test".

## Current DB:

![Database](proposal/db-mockups/dbModel.png?raw=true "Title")
* Reference https://www.sqlite.org/datatype3.html for data types and how to correctly input date.

## UI Goals:

### Preliminary Main Page Mockup
![Main Page](proposal/ui-mockups/Main-Mockup.png?raw=true "Title")

### Preliminary Customer Page Mockup
![Customer Page](proposal/ui-mockups/Customer-Mockup.png?raw=true "Title")

### Preliminary Style Guide
* Unified color palette.
* Unified text scheme.
* Keep user interaction between any given location and desired response minimal.
* "Wired-In Design" - Design should exploit user's desire to feel connected to larger world. Design should make user feel as though they're plugged into humanity's collective central nervous system.

### To do list:
* Complete database table design.
* Add registration of users (brokers) to login window.
* Add registration of customers for users form to main window.
* Implement preliminary UI designs for main page and customer tabs.
