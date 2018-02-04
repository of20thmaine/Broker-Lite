DROP TABLE IF EXISTS identity;  
DROP TABLE IF EXISTS stock_owned;  
DROP TABLE IF EXISTS relationship;  
DROP TABLE IF EXISTS stock;  
DROP TABLE IF EXISTS client;  
DROP TABLE IF EXISTS credentials; 
DROP TABLE IF EXISTS broker; 
DROP TABLE IF EXISTS people;

CREATE TABLE people (
	id INTEGER PRIMARY KEY,
	last_name TEXT NOT NULL,
	first_name TEXT NOT NULL,
    email TEXT
);

CREATE TABLE broker
	(id INTEGER NOT NULL,
	last_name TEXT NOT NULL,
	first_name TEXT NOT NULL,
	phone_num INTEGER,
    email TEXT,
	address TEXT,
	FOREIGN KEY(id, last_name,first_name,email) REFERENCES people(id, last_name, first_name,email)
);
	
CREATE TABLE credentials (
	username TEXT PRIMARY KEY,
	password TEXT NOT NULL
);

CREATE TABLE client (
    id INTEGER NOT NULL,
	last_name TEXT NOT NULL,
	first_name TEXT NOT NULL,
	phone_num INTEGER,
    email TEXT,
	address TEXT,
    cash INTEGER NOT NULL,
	FOREIGN KEY(id, last_name, first_name) REFERENCES people(id, last_name, first_name)
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

CREATE TABLE identity (
    username TEXT NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY(username) REFERENCES credentials(username),
    FOREIGN KEY(user_id) REFERENCES people(id)
);


