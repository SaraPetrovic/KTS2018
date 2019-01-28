insert into users(id, first_name, last_name, username, password, role, user_type_demo) values (1, 'Marko', 'Markovic', 'admin', 'adminadmin', 0, 0)
insert into users(id, first_name, last_name, username, password, role, user_type_demo) values (2, 'Jovan', 'Lakovic', 'user1', '12345678', 1, 1)
insert into users(id, first_name, last_name, username, password, role, user_type_demo) values (3, 'Marko', 'Balenovic', 'user2', '12345678', 1, 1)
insert into users(id, first_name, last_name, username, password, role, user_type_demo) values (4, 'Sara', 'Petrovic', 'user3', '12345678', 1, 1)
insert into users(id, first_name, last_name, username, password, role, user_type_demo) values (5, 'Nikola', 'Nikic', 'user4', '12345678', 1, 2)
insert into users(id, first_name, last_name, username, password, role, user_type_demo) values (6, 'Sara', 'Petrovic', 'sara123', '12345678', 1, 1)
insert into users(id, first_name, last_name, username, password, role) values (7, 'Conducter', 'Conducter', 'conductor', '123', 2)
insert into users(id, first_name, last_name, username, password, role, user_type_demo, document, document_verified) values (8, 'Marko', 'Markovic', 'admin123', 'adminadmin', 0, 0, null, 2)
insert into users(id, first_name, last_name, username, password, role, user_type_demo, document, document_verified) values (9, 'Milan', 'Markovic', 'client', 'client', 1, 0, 'dssd', 1)
insert into users(id, first_name, last_name, username, password, role, user_type_demo, document, document_verified) values (10, 'Mila', 'Manic', 'client2', 'client', 1, 0, 'dssd', 1)
INSERT INTO users(id, first_name, last_name, username, password, role) VALUES (11, 'Marko', 'Balenovic', 'administrator', 'administrator', 0) 



insert into kts_lines(id, active, name, transport_type, duration) values (1, true, '7A', 0, 1000)
insert into kts_lines(id, active, name, transport_type, duration) values (2, true, '4A', 0, 1000)
insert into kts_lines(id, active, name, transport_type, duration) values (3, true, '76A', 0, 1000)
insert into kts_lines(id, active, name, transport_type, duration) values (4, true, '64A', 0, 1000)

insert into stations(id, address, name, active, x, y) values (1, 'Bul. Oslobodjenja 1', 'Gl. stanica', true, 0, 0)
insert into stations(id, address, name, active, x, y) values (2, 'Bul. Oslobodjenja 20', 'Bul. Oslobodjenja - Kralja Petra I', true, 0, 0)
insert into stations(id, address, name, active, x, y) values (4, 'Jevrejska 2', 'Jevrejska - Bul. Oslobodjenja', true, 0, 0)
insert into stations(id, address, name, active, x, y) values (8, 'Bul. Mihaila Pupina 50', 'Bul. Mihaila Pupina - Pothodnik', true, 0, 0)
insert into stations(id, address, name, active, x, y) values (3, 'Bul. Oslobodjenja 56', 'Bul. Oslobodjenja - Futoska', true, 0, 0)
insert into stations(id, address, name, active, x, y) values (5, 'Bul. Oslobodjenja 130', 'Bul. Oslobodjenja - Bul. Cara Lazara', true, 0, 0)
insert into stations(id, address, name, active, x, y) values (6, 'Narodnog fronta 20', 'Narodnog Fronta - Sekspirova', true, 0, 0)
insert into stations(id, address, name, active, x, y) values (7, 'Maksima Gorkog 2', 'Maksima Gorkog - Bul. Oslobodjenja', true, 0, 0)
insert into stations(id, address, name, active, x, y) values (9, 'Bukovacki put', 'Bukovac - Vinogradi', true, 0, 0)
insert into stations(id, address, name, active, x, y) values (10, 'Vuka Karadzica 4', 'Ledinci - Vuka Karadzica 4', true, 0, 0)

insert into zones(id, name, sub_zone_id, active) values (1, 'Zona I', null, true)
insert into zones(id, name, sub_zone_id, active) values (2, 'Zona II', 1, true)
insert into zones(id, name, sub_zone_id, active) values (3, 'Zona III', 2, true)
insert into zones(id, name, sub_zone_id, active) values (4, 'Zona IV', 3, true)
insert into zones(id, name, sub_zone_id, active) values (5, 'Zona V', 4, true)
insert into zones(id, name, sub_zone_id, active) values (6, 'Zona VI', 5, true)
insert into zones(id, name, sub_zone_id, active) values (7, 'Zona VII', 6, false)

insert into street_path(line_id, street_path) values (1, "Sentandrejski Put 2")
insert into street_path(line_id, street_path) values (1, "Sentandrejski Put 1")
insert into street_path(line_id, street_path) values (1, "Kisacka 2")
insert into street_path(line_id, street_path) values (1, "Kisacka 4")
insert into street_path(line_id, street_path) values (1, "Kisacka 3")
insert into street_path(line_id, street_path) values (1, "Kisacka 1")
insert into street_path(line_id, street_path) values (1, "Jovana Subotica 1")
insert into street_path(line_id, street_path) values (1, "Jovana Subotica 2")
insert into street_path(line_id, street_path) values (1, "Jovana Subotica 3")
insert into street_path(line_id, street_path) values (1, "Bulevar Mihajla Pupina 1")
insert into street_path(line_id, street_path) values (1, "Zarka Zrenjanina 1")
insert into street_path(line_id, street_path) values (1, "Strazilovska 2")
insert into street_path(line_id, street_path) values (1, "Fruskogorska 1")
insert into street_path(line_id, street_path) values (1, "Bulevar Cara Lazara 5")
insert into street_path(line_id, street_path) values (1, "Bulevar Cara Lazara 6")
insert into street_path(line_id, street_path) values (1, "Strazilovska 1")
insert into street_path(line_id, street_path) values (1, "Fruskogorska 3")
insert into street_path(line_id, street_path) values (1, "Fruskogorska 2")



insert into street_path(line_id, street_path) values (2, "Bulevar Jovana Ducica 3")
insert into street_path(line_id, street_path) values (2, "Bulevar Jovana Ducica 2")
insert into street_path(line_id, street_path) values (2, "Bulevar Jovana Ducica 1")
insert into street_path(line_id, street_path) values (2, "Bulevar Slobodana Jovanovica 1")
insert into street_path(line_id, street_path) values (2, "Futoski Put 1")
insert into street_path(line_id, street_path) values (2, "Futoska 4")
insert into street_path(line_id, street_path) values (2, "Futoska 3")
insert into street_path(line_id, street_path) values (2, "Futoska 2")
insert into street_path(line_id, street_path) values (2, "Futoska 1")
insert into street_path(line_id, street_path) values (2, "Jevrejska 1")
insert into street_path(line_id, street_path) values (2, "Jovana Subotica 3")


insert into street_path(line_id, street_path) values (3, "Oblacica Rada 1")
insert into street_path(line_id, street_path) values (3, "Milenka Grcica 1")
insert into street_path(line_id, street_path) values (3, "Milenka Grcica 2")
insert into street_path(line_id, street_path) values (3, "Kornelija Stankovica 1")
insert into street_path(line_id, street_path) values (3, "Rumenacka 1")
insert into street_path(line_id, street_path) values (3, "Rumenacka 2")
insert into street_path(line_id, street_path) values (3, "Bulevar Kralja Petra 1")
insert into street_path(line_id, street_path) values (3, "Bulevar Kralja Petra 2")
insert into street_path(line_id, street_path) values (3, "Brace Jovandic 1")
insert into street_path(line_id, street_path) values (3, "Jovana Subotica 2")
insert into street_path(line_id, street_path) values (3, "Jovana Subotica 3")
insert into street_path(line_id, street_path) values (3, "Bulevar Mihajla Pupina 1")
insert into street_path(line_id, street_path) values (3, "Bulevar Mihajla Pupina 2")
insert into street_path(line_id, street_path) values (3, "Most Duga")
insert into street_path(line_id, street_path) values (3, "Preradoviceva 1")
insert into street_path(line_id, street_path) values (3, "Preradoviceva 2")
insert into street_path(line_id, street_path) values (3, "Preradoviceva 3")


insert into street_path(line_id, street_path) values (4, "Narodnog Fronta 1")
insert into street_path(line_id, street_path) values (4, "Narodnog Fronta 2")
insert into street_path(line_id, street_path) values (4, "Strazilovska 1")
insert into street_path(line_id, street_path) values (4, "Bulevar Cara Lazara 6")
insert into street_path(line_id, street_path) values (4, "Bulevar Cara Lazara 5")
insert into street_path(line_id, street_path) values (4, "Fruskogorska 1")
insert into street_path(line_id, street_path) values (4, "Strazilovska 2")
insert into street_path(line_id, street_path) values (4, "Zarka Zrenjanina 1")
insert into street_path(line_id, street_path) values (4, "Bulevar Mihajla Pupina 1")
insert into street_path(line_id, street_path) values (4, "Jevrejska 1")
insert into street_path(line_id, street_path) values (4, "Bulevar Oslobodjenja 3")
insert into street_path(line_id, street_path) values (4, "Bulevar Oslobodjenja 2")
insert into street_path(line_id, street_path) values (4, "Bulevar Oslobodjenja 1")


insert into zones_stations(zone_id, stations_id) values (1, 1)
insert into zones_stations(zone_id, stations_id) values (1, 2)
insert into zones_stations(zone_id, stations_id) values (1, 3)
insert into zones_stations(zone_id, stations_id) values (1, 4)
insert into zones_stations(zone_id, stations_id) values (1, 5)
insert into zones_stations(zone_id, stations_id) values (1, 6)
insert into zones_stations(zone_id, stations_id) values (1, 7)
insert into zones_stations(zone_id, stations_id) values (1, 8)
insert into zones_stations(zone_id, stations_id) values (2, 10)
insert into zones_stations(zone_id, stations_id) values (3, 9)


insert into line_and_station(line_id, station_id, station_order) values (1, 1, 1)
insert into line_and_station(line_id, station_id, station_order) values (1, 2, 2)
insert into line_and_station(line_id, station_id, station_order) values (1, 3, 3)
insert into line_and_station(line_id, station_id, station_order) values (1, 5, 4)
insert into line_and_station(line_id, station_id, station_order) values (1, 7, 5)
insert into line_and_station(line_id, station_id, station_order) values (2, 1, 1)
insert into line_and_station(line_id, station_id, station_order) values (2, 2, 2)
insert into line_and_station(line_id, station_id, station_order) values (2, 4, 3)
insert into line_and_station(line_id, station_id, station_order) values (2, 8, 4)
insert into line_and_station(line_id, station_id, station_order) values (3, 1, 1)
insert into line_and_station(line_id, station_id, station_order) values (3, 2, 2)
insert into line_and_station(line_id, station_id, station_order) values (3, 3, 3)
insert into line_and_station(line_id, station_id, station_order) values (3, 5, 4)
insert into line_and_station(line_id, station_id, station_order) values (3, 10, 5)
insert into line_and_station(line_id, station_id, station_order) values (4, 1, 1)
insert into line_and_station(line_id, station_id, station_order) values (4, 2, 2)
insert into line_and_station(line_id, station_id, station_order) values (4, 3, 3)
insert into line_and_station(line_id, station_id, station_order) values (4, 7, 4)
insert into line_and_station(line_id, station_id, station_order) values (4, 9, 5)

insert into route_schedule(id, active, active_from, line_id) values (1, true, '2019-02-01 00:00:00', 1)
insert into weekday_schedule(route_schedule_id, weekday) values (1, '07:00:00')
insert into weekday_schedule(route_schedule_id, weekday) values (1, '13:00:00')
insert into weekday_schedule(route_schedule_id, weekday) values (1, '20:00:00')
insert into saturday_schedule(route_schedule_id, saturday) values (1, '11:40:00')
insert into saturday_schedule(route_schedule_id, saturday) values (1, '16:40:00')
insert into sunday_schedule(route_schedule_id, sunday) values (1, '10:00:00')

insert into route_schedule(id, active, active_from, line_id) values (2, true, '2019-02-01 00:00:00', 2)
insert into weekday_schedule(route_schedule_id, weekday) values (2, '08:00:00')
insert into weekday_schedule(route_schedule_id, weekday) values (2, '14:00:00')
insert into weekday_schedule(route_schedule_id, weekday) values (2, '21:30:00')
insert into saturday_schedule(route_schedule_id, saturday) values (2, '13:40:00')
insert into saturday_schedule(route_schedule_id, saturday) values (2, '18:40:00')
insert into sunday_schedule(route_schedule_id, sunday) values (2, '19:00:00')

insert into route_schedule(id, active, active_from, line_id) values (3, true, '2019-02-01 00:00:00', 3)
insert into weekday_schedule(route_schedule_id, weekday) values (3, '06:15:00')
insert into weekday_schedule(route_schedule_id, weekday) values (3, '19:30:00')
insert into saturday_schedule(route_schedule_id, saturday) values (3, '12:00:00')
insert into sunday_schedule(route_schedule_id, sunday) values (3, '12:00:00')


insert into route_schedule(id, active, active_from, line_id) values (4, true, '2019-02-01 00:00:00', 4)
insert into weekday_schedule(route_schedule_id, weekday) values (4, '12:00:00')
insert into saturday_schedule(route_schedule_id, saturday) values (4, '13:00:00')
insert into sunday_schedule(route_schedule_id, sunday) values (4, '17:00:00')

insert into price_list(id, active, start_date_time, end_date_time, line_discount, student_discount, senior_discount, monthly_coeffitient, yearly_coeffitient) values (1, true, '2019-02-06 19:00:00', '2019-02-06 20:00:00', 0.5, 0.8, 0.7, 17, 170)
insert into price_list(id, active, start_date_time, end_date_time, line_discount, student_discount, senior_discount, monthly_coeffitient, yearly_coeffitient) values (2, false, null, null, 0.55, 0.85, 0.75, 15, 150)

insert into one_time_prices(price_list_id, price, zone_id) values (1, 100.00, 1)
insert into one_time_prices(price_list_id, price, zone_id) values (1, 200.00, 2)
insert into one_time_prices(price_list_id, price, zone_id) values (1, 300.00, 3)
insert into one_time_prices(price_list_id, price, zone_id) values (2, 110.00, 1)
insert into one_time_prices(price_list_id, price, zone_id) values (2, 210.00, 2)
insert into one_time_prices(price_list_id, price, zone_id) values (2, 320.00, 3)


insert into vehicles(id, active, vehicle_name, vehicle_type, free, free_from) values (1, true, 'vozilo broj 1', 0, true, '2019-01-01 00:00:00')
insert into vehicles(id, active, vehicle_name, vehicle_type, free, free_from) values (2, true, 'vozilo broj 2', 0, true, '2019-01-01 00:00:00')

insert into kts_route(id, date, line_id, vehicle_id, active) values(1, '2019-02-01 00:00:00', 1, 1, true)


insert into ticket(id, active, start_time, end_time, price, ticket_type, ticket_temporal, transport_type, user_id, line_id, zone_id) values (1, false, null, null, 80, 'zone_ticket', 0, 0, 2, null, 1)
