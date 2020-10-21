DROP TABLE IF EXISTS artists;
create table artists
(
	id int unsigned auto_increment primary key,
	name varchar(50) not null,
	type varchar(50) not null,
	nationality varchar(50) not null
);

DROP TABLE IF EXISTS songs;
create table songs
(
	id int unsigned auto_increment primary key,
	title varchar(50) not null,
	release_year int not null,
	artist_id int unsigned not null,
	constraint songs_ibfk_1
		foreign key (artist_id) references artists (id)
);

DROP FUNCTION IF EXISTS countArtistBySongType;
CREATE FUNCTION countArtistBySongType(type_name VARCHAR(50)) RETURNS INTEGER  READS SQL DATA DETERMINISTIC BEGIN
   DECLARE song_type INTEGER;
   SELECT COUNT(*) INTO song_type FROM artists WHERE type = type_name;
   RETURN song_type;
END;