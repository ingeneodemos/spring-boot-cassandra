CREATE KEYSPACE grupoprimario WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

create table grupoprimario.usuario(id bigint primary key,nombre text, edad int);

insert into grupoprimario.usuario (id, nombre, edad) values (1, 'daniel1', 1);
insert into grupoprimario.usuario (id, nombre, edad) values (2, 'daniel2', 2);
insert into grupoprimario.usuario (id, nombre, edad) values (3, 'daniel3', 3);
insert into grupoprimario.usuario (id, nombre, edad) values (4, 'daniel4', 4);
insert into grupoprimario.usuario (id, nombre, edad) values (5, 'daniel5', 5);
insert into grupoprimario.usuario (id, nombre, edad) values (6, 'daniel6', 6);
insert into grupoprimario.usuario (id, nombre, edad) values (7, 'daniel7', 7);
insert into grupoprimario.usuario (id, nombre, edad) values (8, 'daniel8', 8);
insert into grupoprimario.usuario (id, nombre, edad) values (9, 'daniel9', 9);
insert into grupoprimario.usuario (id, nombre, edad) values (10, 'daniel10', 10);
insert into grupoprimario.usuario (id, nombre, edad) values (11, 'daniel11', 11);
insert into grupoprimario.usuario (id, nombre, edad) values (12, 'daniel12', 12);

select * from grupoprimario.usuario;