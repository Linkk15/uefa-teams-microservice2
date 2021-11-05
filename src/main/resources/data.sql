insert into country (name)
values ('España');
insert into country (name)
values ('Polonia');
insert into country (name)
values ('Alemania');
insert into country (name)
values ('Inglaterra');
insert into country (name)
values ('Dinamarca');
insert into country (name)
values ('Francia');

---------------------------------------------------------

insert into rival (name)
values ('Osasuna');
insert into rival (name)
values ('New Castle');
insert into rival (name)
values ('Oviedo');
insert into rival (name)
values ('Deportivo de la Coruña');
insert into rival (name)
values ('Sevilla FC');
insert into rival (name)
values ('Betis');
insert into rival (name)
values ('Friburg');
insert into rival (name)
values ('Chelsea');
insert into rival (name)
values ('Las Palmas');

---------------------------------------------------------

insert into team (name, country_id, rival_id, photo)
values ('Borussia', 3, null, null);
insert into team (name, country_id, rival_id, photo)
values ('Rayo Vallecano', 1, null, null);
insert into team (name, country_id, rival_id, photo)
values ('Getafe', 1, null, null);
insert into team (name, country_id, rival_id, photo)
values ('Liverpool', 4, null, null);
insert into team (name, country_id, rival_id, photo)
values ('Manchester United', 4, null, 44);
insert into team (name, country_id, rival_id, photo)
values ('Real Madrid', 1, 1, null);
insert into team (name, country_id, rival_id, photo)
values ('Barcelona', 1, 6, null);
insert into team (name, country_id, rival_id, photo)
values ('Bayern', 3, 1, null);
insert into team (name, country_id, rival_id, photo)
values ('Atletico Madrid', 1, 6, null);
insert into team (name, country_id, rival_id, photo)
values ('Manchester City', 5, 5, null);

---------------------------------------------------------

insert into uefa (date, team_id)
values ('20210225', 6);
insert into uefa (date, team_id)
values ('20121013', 6);