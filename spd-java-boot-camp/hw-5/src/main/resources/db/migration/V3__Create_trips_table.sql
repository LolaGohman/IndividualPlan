create table trips
(
    id       serial primary key,
    driver   varchar(50) references drivers (name),
    duration integer not null,
    distance double precision not null,
    discount double precision
);