# Broker-Lite

### Connect account managers to their customers assets quickly and reliably.

## Contribution Requirements/Dependencies

* Requires JDK-8.
* Download e(fx)clipse plugin in Eclipse Marketplace.
* Manually interact with SQL-lite database "BrokerLite.sqlite" via DB manager such as 'DB Browser for SQLite".
* Upon execution if connection status reads "not connected" or error thrown add lib directory to build path.
* Execute by running Main.java
* Edit '.fxml' documents for GUI via Oracle Scene Builder v 2.0+ only.

## Current Models:
* User (test-model): (Username (*PK), Password, FirstName, LastName, Email) ~ All not nullable.
** Login with test user "Username: test, Password: test"



### To do list:
* Create formal natural language models for database tables.
* Don't do anything else until this, otherwise project will be clusterfuck.
* I'm using this tutorial to figure out best way for stock API calls: https://iadviseblog.wordpress.com/2015/03/09/a-javafx-8-stock-ticker-application/
* Stock Controller class currently broken, actively working on it.
