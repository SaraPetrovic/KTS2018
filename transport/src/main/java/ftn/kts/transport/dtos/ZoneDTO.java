package ftn.kts.transport.dtos;

import java.util.Set;

import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;

public class ZoneDTO {

	private Long id;
	private String name;
	private Set<StationDTO> stations;
	private ZoneDTO subZone;
	
	public ZoneDTO() {	}
	
	public ZoneDTO(Long id, String name, Set<StationDTO> stations, ZoneDTO subZone) {
		this.id = id;
		this.name = name;
		this.stations = stations;
		this.subZone = subZone;
	}
	
	public ZoneDTO(Zone zone) {
		this.id = zone.getId();
		this.name = zone.getName();
		for(Station s : zone.getStations()) {
			this.stations.add(new StationDTO(s));
		}
		this.subZone = new ZoneDTO(zone.getSubZone());
	}
	
	public Set<StationDTO> getStations() {
		return stations;
	}

	public void setStations(Set<StationDTO> stations) {
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

	public ZoneDTO getSubZone() {
		return subZone;
	}

	public void setSubZone(ZoneDTO subZone) {
		this.subZone = subZone;
	}
	
}
