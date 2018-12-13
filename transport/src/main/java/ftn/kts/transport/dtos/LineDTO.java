package ftn.kts.transport.dtos;

import java.util.HashMap;

public class LineDTO {

	private String name;
	private HashMap<Integer, Long> stations;
	private int vehicleType;
	
	public LineDTO() {
		this.stations = new HashMap<Integer, Long>();
	}

	public LineDTO(String name) {
		super();
		this.name = name;
	}
	
	public LineDTO(String name, HashMap<Integer, Long> stations) {
		super();
		this.name = name;
		this.stations = stations;
	}
	

	public LineDTO(String name, HashMap<Integer, Long> stations, int vehicleType) {
		super();
		this.name = name;
		this.stations = stations;
		this.vehicleType = vehicleType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<Integer, Long> getStations() {
		return stations;
	}

	public void setStations(HashMap<Integer, Long> stations) {
		this.stations = stations;
	}

	public int getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	
	
	
}
