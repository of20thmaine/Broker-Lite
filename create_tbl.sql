DROP TABLE IF EXISTS stock_owned;  
DROP TABLE IF EXISTS relationship;  
DROP TABLE IF EXISTS stock;  
DROP TABLE IF EXISTS client;  
DROP TABLE IF EXISTS credentials; 
DROP TABLE IF EXISTS broker; 


CREATE TABLE broker
	(id INTEGER PRIMARY KEY,
	last_name TEXT NOT NULL,
	first_name TEXT NOT NULL,
	phone_num INTEGER,
    email TEXT,
	address TEXT
);
	
CREATE TABLE credentials (
	username TEXT PRIMARY KEY,
	password TEXT NOT NULL,
	client_id INTEGER,
	broker_id INTEGER,
	FOREIGN KEY (client_id) REFERENCES client(id),
	FOREIGN KEY (broker_id) REFERENCES broker(id)
);

CREATE TABLE client (
    id INTEGER PRIMARY KEY,
	last_name TEXT NOT NULL,
	first_name TEXT NOT NULL,
	phone_num INTEGER,
    email TEXT,
	address TEXT,
    cash INTEGER NOT NULL
);

CREATE TABLE stock (
	name TEXT PRIMARY KEY,
	opening_price INTEGER NOT NULL,
	closing_price INTEGER NOT NULL,
    change INTEGER NOT NULL
);

CREATE TABLE relationship (
    broker_id INTEGER NOT NULL,
    client_id INTEGER NOT NULL,
    FOREIGN KEY(broker_id) REFERENCES broker(id),
    FOREIGN KEY(client_id) REFERENCES client(id)
);

CREATE TABLE stock_owned (
    client_id INTEGER NOT NULL,
    stock_name TEXT NOT NULL,
    amt_shares INTEGER NOT NULL,
    FOREIGN KEY(client_id) REFERENCES client(id),
    FOREIGN KEY(stock_name) REFERENCES stock(name)
);



