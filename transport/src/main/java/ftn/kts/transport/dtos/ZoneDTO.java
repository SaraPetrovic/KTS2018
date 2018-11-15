package ftn.kts.transport.dtos;

import java.util.Set;

import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;

public class ZoneDTO {

	private Long id;
	private String name;
	private Set<Station> stations;
	
	public ZoneDTO() {	}
	
	public ZoneDTO(Long id, String name, Set<Station> stations) {
		this.id = id;
		this.name = name;
		this.stations = stations;
	}
	
	public ZoneDTO(Zone zone) {
		this(zone.getId(), zone.getName(), zone.getStations());
	}
	
	public Set<Station> getStations() {
		return stations;
	}

	public void setStations(Set<Station> stations) {
		this.stations = stations;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
