package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Route implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	@ManyToOne(fetch = FetchType.LAZY)
	private Line line;
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehicle vehicle;
	@Column
	private boolean active;
	
	public Route() {
		
	}
	

	public Route(Long id, Date startTime, Line line, Vehicle vehicle) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.line = line;
		this.vehicle = vehicle;
	}

	public Route(Long id, Date startTime, Line line, Vehicle vehicle, boolean active) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.line = line;
		this.vehicle = vehicle;
		this.active = active;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Line getLine() {
		return line;
	}


	public void setLine(Line line) {
		this.line = line;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	
}
