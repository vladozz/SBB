#drop database if exists sbb;
create database sbb;
use sbb;

create table train (
    id bigint not null auto_increment primary KEY,
	num varchar(50),
    places_qty smallint
)  engine=InnoDB;

create table path (
	id bigint not null auto_increment primary key,
	title varchar(50)
) engine = InnoDB;

create table station (
    id bigint not null auto_increment primary key,
    title varchar(50) not null,
    time_zone varchar(50)
)  engine=InnoDB;

create table path_station (
	id bigint not null auto_increment primary key,
	path_id bigint,
	station_id bigint,
	stand_time int,
    foreign key (path_id) references path (id),
	foreign key (station_id) references station (id)
)  engine=InnoDB;

create table passenger (
    id bigint not null auto_increment primary key,
    first_name varchar(50) not null,
    last_name varchar(50),
    birthdate date,
	unique key (first_name, last_name, birthdate)
)  engine=InnoDB;

create table trip (
	id bigint not null auto_increment primary KEY,
	path_id bigint,
	train_id bigint,
	foreign key (train_id) references train (id),
	foreign key (path_id) references path (id)
)   engine=InnoDB;

create table board (
    id bigint not null auto_increment primary KEY,
	trip_id bigint,
	station_id bigint,
	arrive_time timestamp,
    departure_time timestamp,
	foreign key (trip_id) references trip (id),
	foreign key (station_id) references station (id)
)  engine=InnoDB;

create table ticket (
    id bigint not null auto_increment primary KEY,
    passenger_id bigint,
    departure bigint,
	arrive bigint,
    foreign key (passenger_id) references passenger (id),
	foreign key (departure) references board (id),
    foreign key (arrive) references board (id)
)  engine=InnoDB;

create table role
(
	id int auto_increment primary key,
	title varchar(30)
) engine=InnoDB;

create table user (
	id int AUTO_INCREMENT primary KEY,
	login varchar(30),
	pswd varchar(80),
	role_id int,
	foreign key (role_id) references role (id),
	unique key (login)
) engine=InnoDB;

create table hibernate_sequences (
	sequence_next_hi_value bigint,
	sequence_name varchar(50)
) engine = InnoDB;
#delete from board where id > 0
select * from board;
#select trip_id, title from board, station where station.id = station_id
#update path_station set stand_time = 10 where id > 0
#update train set places_qty = 4 where id = 32

#use sbb;
#insert into role set title = "admin";
#select * from role;
#insert into user set login = "admin", pswd = "21232F297A57A5A743894A0E4A801FC3", role_id = 1 

use sbb;
select * from user;
insert role set id = -2, title = "admin";
update user set role_id = -2 where id = -1;
select * from user, role where role_id = role.id;