DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS country;

CREATE TABLE country
(
    id   int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

CREATE TABLE team
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(250) NOT NULL,
    rival      varchar(255),
    photo      int,
    country_id INT,
    foreign key (country_id) references country (id)
);
