package ftn.kts.transport.services;

import java.util.List;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.RouteSchedule;

public interface LineService {

	Line addLine(Line line) throws DAOException;
	Line addStationsToLine(long id, LineDTO lineDTO) throws DAOException;
	Line updateLine(LineDTO line, long id) throws DAOException;
	Line deleteLine(long id) throws DAOException;
	List<RouteSchedule> getScheduleByLine(long id);
	RouteSchedule addSchedule(RouteSchedule schedule, long lineId);
	
	
	
}
