DROP TABLE IF EXISTS stock_owned;  
DROP TABLE IF EXISTS relationship;  
DROP TABLE IF EXISTS broker; 
DROP TABLE IF EXISTS stock;  
DROP TABLE IF EXISTS client;  
DROP TABLE IF EXISTS credentials; 

CREATE TABLE credentials (
	broker_id INTEGER PRIMARY KEY,
	username TEXT NOT NULL,
	password TEXT NOT NULL CHECK (password<>""),
	UNIQUE (username COLLATE NOCASE)
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
	name TEXT,
	date_time INTEGER,
	opening_price REAL NOT NULL,
	current_price REAL NOT NULL,
    change REAL NOT NULL,
	PRIMARY KEY (name,date_time)
);

CREATE TABLE broker (
	id INTEGER NOT NULL,
	last_name TEXT NOT NULL,
	first_name TEXT NOT NULL,
	phone_num INTEGER,
    email TEXT,
	address TEXT,
	FOREIGN KEY (id) references credentials(broker_id)
);

CREATE TABLE relationship (
    broker_id INTEGER NOT NULL,
    client_id INTEGER NOT NULL,
    FOREIGN KEY (broker_id) REFERENCES broker(id),
    FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE stock_owned (
    client_id INTEGER NOT NULL,
    stock_name TEXT NOT NULL,
    shares INTEGER NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (stock_name) REFERENCES stock(name)
);



