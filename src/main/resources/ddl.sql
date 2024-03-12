create table users
(
    id          serial primary key,
    first_name  varchar(128),
    second_name varchar(128),
    age         integer
)