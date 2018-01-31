drop table if exists stock;
drop table if exists client;
drop table if exists broker;
drop table if exists credentials;
drop table if exists people;



create table people
	(ID = char(5),
	last_name = varchar(20),
	first_name = varchar(20),
	primary key (ID)
	) ENGINE = INNODB;

create table broker
	(ID = char(5),
	last_name = varchar(20),
	first_name = varchar(20),
	phone_num = char(10),
	address = varchar(255),
	foreign key (ID, last_name,first_name) references people(ID, last_name, first_name)
	) ENGINE = INNODB;
	
create table credentials
	(ID = char(5),
	username = varchar(15),
	password = varchar(20)
	primary key (username),
	foreign key (ID) references people(ID)
	) ENGINE = INNODB;

create table client
	(ID = char(5),
	last_name = varchar(20),
	first_name = varchar(20),
	phone_num = char(10),
	address = varchar(255),
	broker_id = char(5),
	foreign key (ID, last_name, first_name) references people(ID),
	foreign key (broker_id) references broker(ID)
	) ENGINE = INNODB;

create table stock
	(client_id = char(5),
	name = varchar(20),
	opening_price = numeric(10.2),
	closing_price = numeric(10.2),
	state = varchar(10),
	primary key (name),
	foreign key(client_id) references client(ID)
	) ENGINE = INNODB;
	