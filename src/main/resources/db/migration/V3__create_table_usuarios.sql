create table usuarios
(
    id       serial primary key not null,
    nome     varchar,
    email    varchar,
    password varchar,
    visivel  boolean            not null
);