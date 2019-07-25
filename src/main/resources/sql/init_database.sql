
create table if not exists reports (
    id SERIAL NOT NULL PRIMARY KEY,
    theme varchar (250) NOT NULL,
    place varchar (100) NOT NULL,
    reporter varchar (100) NOT NULL,
    description varchar (500) NOT NULL
);