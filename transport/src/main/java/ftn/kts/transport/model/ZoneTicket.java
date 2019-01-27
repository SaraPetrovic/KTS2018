package ftn.kts.transport.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ftn.kts.transport.enums.TicketActivationType;
import ftn.kts.transport.enums.TicketTypeTemporal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("zone_ticket")
public class ZoneTicket extends Ticket implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Zone zone;
	
	
	public ZoneTicket() {
		
	}


	public ZoneTicket(Long id, Zone zone) {
		super();
		this.zone = zone;
	}

	public ZoneTicket(Long id, Date date, TicketActivationType active, TicketTypeTemporal type, Zone zone) {
		super(id, date, active, type);
		this.zone = zone;
	}

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	
	
}
