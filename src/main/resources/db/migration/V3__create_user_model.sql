create table users(
    id bigint auto_increment primary key,
    username varchar(255) not null unique,
    password varchar(255) not null,
    email varchar(255) not null unique
)