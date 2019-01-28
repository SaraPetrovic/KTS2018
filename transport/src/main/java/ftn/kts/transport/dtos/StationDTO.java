package ftn.kts.transport.dtos;

import java.util.Set;

import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.LineAndStation;
import ftn.kts.transport.model.Point;
import ftn.kts.transport.model.Station;

public class StationDTO {
	
	private Long id;
	private String address;
	private String name;
	private Set<LineAndStation> lines;
	private Point location;
	
	public StationDTO() {
		
	}

	public StationDTO(Long id, String address, String name, Set<LineAndStation> lines, Point location) {
		this.id = id;
		this.address = address;
		this.name = name;
		this.lines = lines;
		this.location = location;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public StationDTO(Long id, String address, String name, Set<LineAndStation> lines) {
		this.id = id;
		this.address = address;
		this.name = name;
		this.lines = lines;
	}
	
	public StationDTO(Long id, String address, String name) {
		this.id = id;
		this.address = address;
		this.name = name;
	}
	
	public StationDTO(String address, String name) {
		this.address = address;
		this.name = name;
	}
	
	public StationDTO(Station station) {
		this.id = station.getId();
		this.address = station.getAddress();
		this.name = station.getName();
		this.lines = station.getLineSet();
		this.location = new Point();
		this.location.setX(station.getX());
		this.location.setY(station.getY());
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
	public Set<LineAndStation> getLines() {
		return lines;
	}
	public void setLines(Set<LineAndStation> lines) {
		this.lines = lines;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
