drop database if exists sbb;
create database sbb;
use sbb;

create table train (
    id int primary KEY,
	num varchar(50),
    places_qty smallint
)  engine=InnoDB;

create table path (
	id int auto_increment primary key,
	title varchar(50)
) engine = InnoDB;

create table station (
    id int auto_increment primary key,
    title varchar(50) not null,
    time_zone varchar(50)
)  engine=InnoDB;

create table path_station (
	id int auto_increment primary key,
	path_id int,
	station_id int,
    foreign key (path_id) references path (id),
	foreign key (station_id) references station (id)
)  engine=InnoDB;

create table passenger (
    id int auto_increment primary key,
    first_name varchar(50) not null,
    last_name varchar(50),
    birthdate date,
	unique key (first_name, last_name, birthdate)
)  engine=InnoDB;

create table trip (
	id int AUTO_INCREMENT primary KEY,
	path_id int,
	train_id int,
	foreign key (train_id) references train (id),
	foreign key (path_id) references path (id)
)   engine=InnoDB;

create table board (
    id int AUTO_INCREMENT primary KEY,
	trip_id int,
	station_id int,
	arrive_time timestamp,
    departure_time timestamp,
	foreign key (trip_id) references trip (id),
	foreign key (station_id) references station (id)
)  engine=InnoDB;

create table ticket (
    id int AUTO_INCREMENT primary KEY,
    passenger_id int,
    departure int,
	arrive int,
    foreign key (passenger_id) references passenger (id),
	foreign key (departure) references board (id),
    foreign key (arrive) references board (id)
)  engine=InnoDB;

create table role
(
	id int auto_increment primary key,
	title varchar(10)
) engine=InnoDB;

create table user (
	id int AUTO_INCREMENT primary KEY,
	login varchar(30),
	pswd varchar(30),
	role_id int,
	foreign key (role_id) references role (id)
) engine=InnoDB;

