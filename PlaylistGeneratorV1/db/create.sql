use play_list;

create table users
(
    id    int auto_increment primary key,
    username   varchar(50) not null,
    password   varchar(500) not null,
    email      varchar(50) not null,
    is_admin   boolean     not null
);