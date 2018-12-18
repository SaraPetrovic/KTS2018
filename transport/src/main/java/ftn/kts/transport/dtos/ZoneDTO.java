package ftn.kts.transport.dtos;

import java.util.HashSet;
import java.util.Set;

import ftn.kts.transport.model.Station;
import ftn.kts.transport.model.Zone;

public class ZoneDTO {

	private Long id;
	private String name;
	private Set<StationDTO> stations;
	private Long subZoneId;
	
	public ZoneDTO() {	}
	
	public ZoneDTO(Long id, String name, Set<StationDTO> stations, Long subZoneId) {
		this.id = id;
		this.name = name;
		this.stations = stations;
		this.subZoneId = subZoneId;
	}
	
	public ZoneDTO(Zone zone) {
		this.id = zone.getId();
		this.name = zone.getName();
		this.stations = new HashSet<StationDTO>();
		for(Station s : zone.getStations()) {
			StationDTO dto = new StationDTO(s);
			this.stations.add(dto);
		}
		if(zone.getSubZone() != null) {
			this.subZoneId = zone.getSubZone().getId();
		}else {
			this.subZoneId = (long) 0;
		}
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

	public Long getSubZoneId() {
		return subZoneId;
	}

	public void setSubZone(Long id) {
		this.subZoneId = id;
	}
	
}
