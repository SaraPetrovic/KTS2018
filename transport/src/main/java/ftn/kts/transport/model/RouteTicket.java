package ftn.kts.transport.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("route_ticket")
public class RouteTicket extends Ticket implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Route route;
	
	
	public RouteTicket() {
		
	}


	public RouteTicket(Long id, Route route) {
		super();
		this.route = route;
	}


	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
	
}
