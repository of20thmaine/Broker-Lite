DROP TRIGGER IF EXISTS old_stocks;
DROP VIEW IF EXISTS broker_credentials;
DROP VIEw IF EXISTS all_user_id;
DROP VIEW IF EXISTS popular_stock;
DROP VIEW IF EXISTS num_stock_owners;
DROP TABLE IF EXISTS stock_owned;
DROP TABLE IF EXISTS relationship;
DROP TABLE IF EXISTS broker;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS credentials;

CREATE TABLE credentials (
	id INTEGER PRIMARY KEY,
	username TEXT NOT NULL,
	password TEXT NOT NULL CHECK (password<>""),
	UNIQUE (username COLLATE NOCASE)
);

CREATE TABLE client (
    id INTEGER PRIMARY KEY,
	last_name TEXT NOT NULL,
	first_name TEXT NOT NULL,
	phone_num TEXT,
    email TEXT,
	address TEXT,
    cash REAL NOT NULL
);

CREATE TABLE stock (
	name TEXT,
	date_time TEXT,
    index_value REAL NOT NULL,
	high REAL NOT NULL,
	low REAL NOT NULL,
	PRIMARY KEY (name,date_time)
);

CREATE TABLE broker (
	id INTEGER NOT NULL PRIMARY KEY,
	last_name TEXT NOT NULL,
	first_name TEXT NOT NULL,
	phone_num TEXT,
    email TEXT,
	address TEXT,
	FOREIGN KEY (id) references credentials(id) ON DELETE CASCADE
);

CREATE TABLE relationship (
    broker_id INTEGER NOT NULL,
    client_id INTEGER NOT NULL,
    FOREIGN KEY (broker_id) REFERENCES broker(id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);

CREATE TABLE stock_owned (
    client_id INTEGER NOT NULL,
    stock_name TEXT NOT NULL,
    shares INTEGER NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id) on DELETE CASCADE,
    FOREIGN KEY (stock_name) REFERENCES stock(name) ON DELETE CASCADE
);


CREATE VIEW all_user_id AS
select b.id from (broker as b)
union all
select c.id from (client as c);

CREATE VIEW broker_credentials as
select broker.id,username,password
from broker natural join credentials;

CREATE VIEW num_stock_owners as
select s.stock_name,count(*) as total
from stock_owned as s
group by(s.stock_name);

CREATE VIEW popular_stock as
select stock_name, max(total)
from (
	select s.stock_name,count(*) as total
	from stock_owned as s
	group by(s.stock_name));

CREATE TRIGGER delete_old_stocks
AFTER INSERT ON stock
FOR EACH ROW
BEGIN
	DELETE 
	FROM stock
	where date_time in (select s.date_time
		from (stock as s)
		where(julianday('now')-julianday(s.date_time)) > 30);
END;
