package ftn.kts.transport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ftn.kts.transport.enums.VehicleType;

@Entity
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private VehicleType vehicleType;
	@Column(nullable = false, unique = true)
	private String vehicleName;
	
	public Vehicle() {
		
	}

	public Vehicle(Long id, VehicleType vehicleType, String vehicleName) {
		super();
		this.id = id;
		this.vehicleType = vehicleType;
		this.vehicleName = vehicleName;
	}

	public Vehicle(VehicleType vehicleType, String vehicleName) {
		super();
		this.vehicleType = vehicleType;
		this.vehicleName = vehicleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	
	
	
	
}
