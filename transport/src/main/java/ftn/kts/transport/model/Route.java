package ftn.kts.transport.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ROUTES")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Route implements Ticketable {

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
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Vehicle vehicle;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Line line;
	
	public Route() {
		
	}

	public Route(String name, Date startTime, Date endTime, Vehicle vehicle, Line line) {
		super();
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.vehicle = vehicle;
		this.line = line;
	}

	public Route(Long id, String name, Date startTime, Date endTime, Vehicle vehicle, Line line) {
		super();
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.vehicle = vehicle;
		this.line = line;
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
	
	
}
