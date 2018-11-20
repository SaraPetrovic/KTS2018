package ftn.kts.transport.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import ftn.kts.transport.enums.TicketTypeDemographic;
import ftn.kts.transport.enums.TicketTypeTemporal;

@Entity
@Table(name="TICKETS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private double price;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User user;
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
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Zone zone;
	@Enumerated(EnumType.ORDINAL)
	private TicketTypeTemporal ticketTypeTemporal;
	@Enumerated(EnumType.ORDINAL)
	private TicketTypeDemographic ticketTypeDemographic;
	@Column
	private boolean active;

	
	public Ticket() {
		
	}

	public Ticket(double price, User user, Date startTime, Date endTime, Vehicle vehicle, Line line, Zone zone,
			TicketTypeTemporal ticketTypeTemporal, TicketTypeDemographic ticketTypeDemographic, boolean active) {
		super();
		this.price = price;
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
		this.vehicle = vehicle;
		this.line = line;
		this.zone = zone;
		this.ticketTypeTemporal = ticketTypeTemporal;
		this.ticketTypeDemographic = ticketTypeDemographic;
		this.active = active;
	}

	public Ticket(Long id, double price, User user, Date startTime, Date endTime, Vehicle vehicle, Line line, Zone zone,
			TicketTypeTemporal ticketTypeTemporal, TicketTypeDemographic ticketTypeDemographic, boolean active) {
		super();
		this.id = id;
		this.price = price;
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
		this.vehicle = vehicle;
		this.line = line;
		this.zone = zone;
		this.ticketTypeTemporal = ticketTypeTemporal;
		this.ticketTypeDemographic = ticketTypeDemographic;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public TicketTypeTemporal getTicketTypeTemporal() {
		return ticketTypeTemporal;
	}

	public void setTicketTypeTemporal(TicketTypeTemporal ticketTypeTemporal) {
		this.ticketTypeTemporal = ticketTypeTemporal;
	}

	public TicketTypeDemographic getTicketTypeDemographic() {
		return ticketTypeDemographic;
	}

	public void setTicketTypeDemographic(TicketTypeDemographic ticketTypeDemographic) {
		this.ticketTypeDemographic = ticketTypeDemographic;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	
	
}
