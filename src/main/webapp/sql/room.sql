create database if not exists ChatDB;

use ChatDB;

create table Room(
	id int primary key auto_increment,
    title varchar(100) not null,
	entry_limit int not null
);

create table User(
	id int primary key auto_increment,
	user_id varchar(20) not null,
    password varchar(100) not null,
    nickname varchar(50) not null
);

create table Chat(
	id int primary key auto_increment,
    room_id int,
	user_id int,
	content text not null,
    foreign key(room_id) references Room(id) on delete cascade,
    foreign key(user_id) references User(id) on delete cascade
);