package ftn.kts.transport.services;

import java.util.List;
import java.util.Set;

import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Route;
import ftn.kts.transport.model.Vehicle;

public interface RouteService {

    List<Route> getRoutes();
    Route getRoute(Long id);
    void addRoute(Route route);
    Set<Route> findByLine(Line l);
    Route getRouteByVehicle(Vehicle vehicle);
}
