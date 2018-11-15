package ftn.kts.transport.dtos;

import java.util.Set;

import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Station;

public class StationDTO {
	
	private Long id;
	private String address;
	private String name;
	private Set<Line> lines;
	
	public StationDTO() {
		
	}
	
	public StationDTO(Long id, String address, String name, Set<Line> lines) {
		this.id = id;
		this.address = address;
		this.name = name;
		this.lines = lines;
	}
	
	public StationDTO(Station station) {
		this(station.getId(), station.getAddress(), station.getName(), station.getLines());
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Line> getLines() {
		return lines;
	}
	public void setLines(Set<Line> lines) {
		this.lines = lines;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
