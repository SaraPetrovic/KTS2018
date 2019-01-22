package ftn.kts.transport.model;

import ftn.kts.transport.enums.VehicleType;

import javax.persistence.*;
import java.util.Date;

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
	@Column
	private boolean active;
	@Column
	private boolean free;
	@Column
	@Temporal(TemporalType.TIME)
	private Date freeFrom;

	public Vehicle(VehicleType vehicleType, String vehicleName, boolean active, boolean free, Date freeFrom) {
		this.vehicleType = vehicleType;
		this.vehicleName = vehicleName;
		this.active = active;
		this.free = free;
		this.freeFrom = freeFrom;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public Date getFreeFrom() {
		return freeFrom;
	}

	public void setFreeFrom(Date freeFrom) {
		this.freeFrom = freeFrom;
	}

	public Vehicle() {
		
	}

	public Vehicle(Long id, VehicleType vehicleType, String vehicleName) {
		super();
		this.id = id;
		this.vehicleType = vehicleType;
		this.vehicleName = vehicleName;
		this.active = true;
	}

	public Vehicle(VehicleType vehicleType, String vehicleName) {
		super();
		this.vehicleType = vehicleType;
		this.vehicleName = vehicleName;
		this.active = true;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	
}
