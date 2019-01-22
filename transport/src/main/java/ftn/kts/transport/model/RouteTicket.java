package ftn.kts.transport.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("route_ticket")
public class RouteTicket extends Ticket {

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
