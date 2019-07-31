create table if not exists locations
(
    id     SERIAL       NOT NULL PRIMARY KEY,
    name   varchar(250) NOT NULL,
    places int8         NOT NULL
);

create table if not exists users
(
    id       SERIAL       NOT NULL PRIMARY KEY,
    name     varchar(250) NOT NULL,
    email    varchar(250) NOT NULL,
    password varchar(250) NOT NULL,
    role     varchar(250) NOT NULL
);

create table if not exists reports
(
    id          SERIAL       NOT NULL PRIMARY KEY,
    theme       varchar(250) NOT NULL,
    place       int8 references locations,
    reporter    int8 references users,
    startTime   timestamp NOT NULL,
    endTime     timestamp NOT NULL,
    description varchar(500) NOT NULL
);