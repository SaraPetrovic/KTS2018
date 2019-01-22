package ftn.kts.transport.services;

import java.util.List;

import ftn.kts.transport.model.RouteSchedule;

public interface RouteScheduleService {

	RouteSchedule findScheduleById(Long id);
	List<RouteSchedule> getScheduleByLine(long id);
	RouteSchedule addSchedule(RouteSchedule schedule, long lineId);
	RouteSchedule updateSchedule(RouteSchedule updatedSchedule, long lineId, long scheduleId);
	boolean deleteSchedule(long id);

}
