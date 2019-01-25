package ftn.kts.transport.model;

import javax.persistence.*;

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
