drop table if exists relationship;
drop table if exists identity;
drop table if exists stock_owned;
drop table if exists stock;
drop table if exists client;
drop table if exists broker;
drop table if exists credentials;
drop table if exists people;



create table people
	(ID         char(5),
	last_name   varchar(20),
	first_name  varchar(20),
    email       varchar(255),
	primary key (ID)
	) ENGINE = INNODB;

create table broker
	(ID         char(5),
	last_name   varchar(20),
	first_name  varchar(20),
    email       varchar(255),
	phone_num   char(10),
	address     varchar(255),
    primary key (ID),
	foreign key (ID) references people(ID)
	) ENGINE = INNODB;
	
create table credentials
	(username    varchar(15),
	password    varchar(20),
	primary key (username, password)
	) ENGINE = INNODB;

create table client
	(ID         char(5),
	last_name   varchar(20),
	first_name  varchar(20),
    email       varchar(255),
	phone_num   char(10),
	address     varchar(255),
    value       numeric(10.2),
    primary key (ID),
	foreign key (ID) references people(ID)
	) ENGINE = INNODB;

create table stock
	(name            varchar(20),
	opening_price   numeric(10.2),
	closing_price   numeric(10.2),
    change          numeric(10.2),
	state           varchar(10),
	primary key (name)
	) ENGINE = INNODB;

create table relationship
    (broker_id      char(5),
    client_id       char(5),
    primary key (broker_id,client_id),
    foreign key (broker_id) references broker(ID),
    foreign key (client_id) references client(ID)
    ) ENGINE = INNODB;

create table stock_owned
    (client_id      char(5),
    stock_name      varchar(20),
    shares_amt      numeric(10),
    primary key (client_id, stock_name),
    foreign key (client_id) references client(ID),
    foreign key (stock_name) references stock(name)
    ) ENGINE = INNODB;

create table identity
    (username       varchar(15),
    user_id      char(5),
    primary key (username, user_id),
    foreign key (username) references credentials(username),
    foreign key (user_id) references people(ID)
    ) ENGINE = INNODB;
