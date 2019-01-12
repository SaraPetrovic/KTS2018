insert into kts_lines(id, active, name, transport_type) values (1, 1, '7A', 0)
insert into kts_lines(id, active, name, transport_type) values (2, 1, '4A', 0)

insert into stations(id, active, address, name) values (1, 1, 'Bulevar Oslobodjenja 14', 'Bul. Oslobodjenja - Lutrija')
insert into stations(id, active, address, name) values (2, 1, 'Bulevar Oslobodjenja 50', 'Aleksandar zgrada')

insert into line_and_station(line_id, station_id, station_order) values (1, 1, 1)
insert into line_and_station(line_id, station_id, station_order) values (1, 2, 2)

insert into zones(id, active, name, sub_zone_id) values (1, true, 'gradska', NULL)
insert into zones(id, active, name, sub_zone_id) values (2, true, 'prigradska', 1)

INSERT INTO users(id, document_verified, first_name, last_name, username, password, roles, user_type_demo) VALUES (1, FALSE, 'Jovan', 'Lakovic', 'user1', '1234', 1, 1)
INSERT INTO users(id, first_name, last_name, username, password, roles) VALUES (2, 'Marko', 'Markovic', 'admin', 'admin', 0)
--insert into zones_stations(zone_id, stations_id) values (1,1)
--insert into zones_stations(zone_id, stations_id) values (1,2)

insert into users(id, document, document_verified, first_name, last_name, password, roles, user_type_demo, username) values (3, null, false, 'Sara', 'Petrovic', '123', 1, null, 'Sara')