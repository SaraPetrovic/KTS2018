package ftn.kts.transport.model;

import java.io.Serializable;

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



	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	
	
}
