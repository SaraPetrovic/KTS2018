package ftn.kts.transport.services;

import ftn.kts.transport.model.Route;

import java.util.List;

public interface RouteService {

    List<Route> getRoutes();
    Route getRoute(Long id);
    void addRoute(Route route);
}
