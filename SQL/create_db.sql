drop database sbb;
create database sbb;
use sbb;
create table train (
    id int unsigned not null primary KEY,
    place_qty smallint unsigned not null
)  engine=InnoDB;

create table station (
    id int unsigned auto_increment primary key,
    title char(50) not null,
    time_zone tinyint
)  engine=InnoDB;

create table passenger (
    id int unsigned auto_increment primary key,
    fname char(30) not null,
    lname char(30),
    birthday date not null
)  engine=InnoDB;

create table board (
    id int unsigned not null primary KEY,
    train_id int unsigned not null,
    foreign key (train_id)
        references train (id),
    station_id int unsigned not null,
    foreign key (station_id)
        references station (id),
    dep_time datetime
)  engine=InnoDB;

create table ticket (
id int unsigned not null primary KEY,
passenger_id int unsigned not null,
foreign key (passenger_id)
        references passenger (id),
board_id int unsigned not null,
foreign key (board_id)
        references board (id),
dep_date date not null
) engine=InnoDB;





