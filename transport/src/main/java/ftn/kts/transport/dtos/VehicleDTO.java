package ftn.kts.transport.dtos;

import ftn.kts.transport.model.Vehicle;

public class VehicleDTO {

private String name;
	
	public VehicleDTO() {
		
	}

	public VehicleDTO(String name) {
		super();
		this.name = name;
	}

	public VehicleDTO(Vehicle vehicle) {
		this(vehicle.getVehicleName());
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
