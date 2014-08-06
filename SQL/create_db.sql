drop database if exists sbb;
create database sbb;
use sbb;
create table train (
    id int unsigned not null primary KEY,
    places_qty smallint unsigned not null
)  engine=InnoDB;

create table station (
    id int unsigned auto_increment primary key,
    title varchar(50) not null,
    time_zone tinyint
)  engine=InnoDB;

create table passenger (
    id int unsigned auto_increment primary key,
    first_name varchar(30) not null,
    last_name varchar(30),
    birthdate date not null
)  engine=InnoDB;

create table board (
    id int unsigned not null primary KEY,
    train_id int unsigned not null,
    foreign key (train_id)
        references train (id),
    station_id int unsigned not null,
    foreign key (station_id)
        references station (id),
	arrive_time datetime,
    departure_time datetime
)  engine=InnoDB;

create table ticket (
    id int unsigned not null primary KEY,
    passenger_id int unsigned not null,
    foreign key (passenger_id)
        references passenger (id),
    departure_station int unsigned not null,
    foreign key (departure_station)
        references board (id),
	arrive_station int unsigned not null,
    foreign key (arrive_station)
        references board (id),
    departure_date date not null
)  engine=InnoDB;





