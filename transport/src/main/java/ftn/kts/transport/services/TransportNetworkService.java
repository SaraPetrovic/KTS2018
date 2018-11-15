package ftn.kts.transport.services;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.dtos.RouteDTO;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.Route;

public interface TransportNetworkService {

	Line addLine(LineDTO line);
	Line updateLine(LineDTO line, long id);
	Line deleteLine(LineDTO line);
	
	Route addRoute(RouteDTO route);
	Route updateRoute(RouteDTO route, long id);
	Route deleteRoute(RouteDTO route);
	
}
