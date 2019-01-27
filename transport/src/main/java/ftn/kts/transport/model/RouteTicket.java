package ftn.kts.transport.model;

import java.util.Date;

import javax.persistence.*;

import ftn.kts.transport.enums.TicketActivationType;
import ftn.kts.transport.enums.TicketTypeTemporal;

@Entity
@DiscriminatorValue("route_ticket")
public class RouteTicket extends Ticket {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
    private Route route;

	public RouteTicket() {
		super();
	}
	
    public RouteTicket(Long id, Date date, TicketActivationType active, TicketTypeTemporal type, Route route) {
        super(id, date, active, type);
        this.route = route;
    }

    public RouteTicket(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
