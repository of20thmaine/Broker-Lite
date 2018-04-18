# Broker-Lite

### Connect account managers to their customers assets quickly and reliably.


## Contribution Requirements/Dependencies

* Requires JDK-8.
* Download e(fx)clipse plugin in Eclipse Marketplace.
* Upon execution if connection status reads "not connected" or error thrown add lib directory to build path.
* Execute by running Main.java


## Database Design:

![Database](proposal/db-mockups/dbModel.png?raw=true "Title")

* Reference https://www.sqlite.org/datatype3.html for data types and how to correctly input date.
* ER-Diagram:
![Database](proposal/db-mockups/er-diagram.png?raw=true "Title")


## Database Rubric:
1. Primary Keys
	1. See "sql/create_tbl.sql"
	2. Primary Keys include BrokerId's, and Customer Id's.
2. Foreign Keys (and Cascade)
	1. See "sql/create_tbl.sql"
	2. Foreign keys included in broker, relationship, and stock_owned tables. All cascade delete.
3. SQL Construct Demonstration:
	1. See "sql/create_tbl.sql"
	2. See "src/UserModel.java" and "src/StockModel.java"
	3. Project makes extensive use of basic SQL functionality.
4. Views
	1. See "sql/create_tbl.sql"
	2. A total of 4 views were generated.
5. Triggers:
	1. See "sql/create_tbl.sql"
	2. A trigger is used to delete stock data older than 30 days.
6. Transaction Processing:
	1. Stock Buy/Sells handled via "CustomerTabController" and "StockModel".
	2. SQL-lite limitations not withstanding, transactions are handled "best-practice".
7. Prepared Statements
	1. All SQL handled via JDBC prepared statements.
	2. SQL-Injection would be difficult with this schema.
8. Stored Procedures / Stored Functions
	1. Not available out of box with SQL-Lite.
	2. We simulate in Java, for example, all inputted values are checked for validity before being sent to database.


## Front-End
![Login_Screen](proposal/ui-demo/login.png?raw=true "Login-Screen")

![Market_Tab](proposal/ui-demo/market-tab.png?raw=true "Market-Tab")

![Customer_Tab](proposal/ui-demo/customer-tab.png?raw=true "Customer-Tab")


## Authors

* **Bobby Palmer** - [of20thmaine](https://github.com/of20thmaine)
* **Samnang Pann** - [Spann303](https://github.com/Spann303)
