create table trips_passengers
(
    trip      integer not null,
    passenger varchar(50) not null,
    primary key (trip, passenger),
    foreign key (trip) references trips(id),
    foreign key (passenger) references passengers (name)
);