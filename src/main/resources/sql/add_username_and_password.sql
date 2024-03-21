alter table users
add password varchar(255) not null,
add username varchar(255) not null;

insert into users(age, surname, name, username, password) VALUES (6, 'admin', 'admin', 'admin', '1234');