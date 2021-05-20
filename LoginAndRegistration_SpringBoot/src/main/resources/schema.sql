DROP TABLE IF EXISTS USERS;

create table USERS
(
    ID INT auto_increment,
    NAME VARCHAR not null,
    SURNAME VARCHAR not null,
    LOGIN VARCHAR not null,
    PASSWORD VARCHAR not null,
    constraint USER_PK
        primary key (ID)
);