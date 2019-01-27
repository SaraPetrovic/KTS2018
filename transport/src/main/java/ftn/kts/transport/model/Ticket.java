package ftn.kts.transport.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ftn.kts.transport.enums.TicketActivationType;
import ftn.kts.transport.enums.TicketTypeTemporal;
import ftn.kts.transport.enums.VehicleType;



@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ticket_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Ticket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private double price;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User user;
	@Enumerated
	private VehicleType transportType;
	@Enumerated
	private TicketTypeTemporal ticketTemporal;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	@Column
	private TicketActivationType active;	//atribut za aktivaciju karte od strane user-a

	
	public Ticket() {
		
	}

	public Ticket(Long id, Date endTime, TicketActivationType active, TicketTypeTemporal type) {
		super();
		this.id = id;
		this.endTime = endTime;
		this.active = active;
		this.ticketTemporal = type;
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

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

	public TicketActivationType getActive() {
		return active;
	}

	public void setActive(TicketActivationType active) {
		this.active = active;
	}

	public VehicleType getTransportType() {
		return transportType;
	}

	public void setTransportType(VehicleType transportType) {
		this.transportType = transportType;
	}

	public TicketTypeTemporal getTicketTemporal() {
		return ticketTemporal;
	}

	public void setTicketTemporal(TicketTypeTemporal ticketTemporal) {
		this.ticketTemporal = ticketTemporal;
	}
	
	
	
	
}
