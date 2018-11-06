package ftn.kts.transport.model;

import ftn.kts.transport.enums.VehicleType;

import javax.persistence.*;

@Entity
@Table(name="VEHICLES")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private VehicleType vehicleType;
	@Column
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
