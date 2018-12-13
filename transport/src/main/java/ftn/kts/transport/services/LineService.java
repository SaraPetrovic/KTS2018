package ftn.kts.transport.services;

import ftn.kts.exception.DAOException;
import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.model.Line;

public interface LineService {

	Line addLine(LineDTO line) throws DAOException;
	Line addStationsToLine(long id, LineDTO lineDTO) throws DAOException;
	Line updateLine(LineDTO line, long id) throws DAOException;
	Line deleteLine(LineDTO line) throws DAOException;
	
//	Route addRoute(RouteDTO route);
//	Route updateRoute(RouteDTO route, long id);
//	Route deleteRoute(RouteDTO route);
	
}
