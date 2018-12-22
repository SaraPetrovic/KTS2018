insert into zones(active, name, sub_zone_id) values (true, 'gradska', 2);
insert into zones(active, name, sub_zone_id) values (true, 'gradska', 2);

insert into stations(active, address, name) values (true, 'Jevrejska', 'Prva');
insert into stations(active, address, name) values (true, 'Jevrejska', 'Druga');

insert into zones_stations(zone_id, stations_id) values (1,1);
insert into zones_stations(zone_id, stations_id) values (1,2);