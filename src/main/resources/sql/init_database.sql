
create table if not exists locations
(
    id          SERIAL       NOT NULL PRIMARY KEY,
    name        varchar(250) NOT NULL,
    places      int8 NOT NULL
);

create table if not exists reports
(
    id          SERIAL       NOT NULL PRIMARY KEY,
    theme       varchar(250) NOT NULL,
    place       int8 references locations,
    reporter    varchar(100) NOT NULL,
    description varchar(500) NOT NULL
);