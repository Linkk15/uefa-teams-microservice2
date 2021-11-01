DROP TABLE IF EXISTS uefa;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS rival;

CREATE TABLE country
(
    id   int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

CREATE TABLE rival
(
    id   int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

CREATE TABLE team
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(250) NOT NULL,
    photo      int,
    country_id INT          not null,
    rival_id   int,
    foreign key (country_id) references country (id),
    foreign key (rival_id) references rival (id)
);

CREATE TABLE uefa
(
    id      int AUTO_INCREMENT PRIMARY KEY,
    date    date NOT NULL,
    team_id int,
    foreign key (team_id) references team (id)
);
