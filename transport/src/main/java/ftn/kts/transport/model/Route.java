package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="ROUTES")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Route implements Ticketable, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehicle vehicle;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Line line;
	@Column
	private boolean active;
	
	public Route() {
		this.active = true;
	}

	public Route(String name, Date startTime, Date endTime, Vehicle vehicle, Line line) {
		super();
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.vehicle = vehicle;
		this.line = line;
		this.active = true;
	}

	public Route(Long id, String name, Date startTime, Date endTime, Vehicle vehicle, Line line) {
		super();
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.vehicle = vehicle;
		this.line = line;
		this.active = true;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
