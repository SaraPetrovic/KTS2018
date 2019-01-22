package ftn.kts.transport.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.RouteSchedule;
import ftn.kts.transport.repositories.LineRepository;
import ftn.kts.transport.repositories.RouteScheduleRepository;

@Service
public class RouteScheduleServiceImpl implements RouteScheduleService {

	@Autowired
	private RouteScheduleRepository scheduleRepository;
	
	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private LineService lineService;
	
	@Override
	public RouteSchedule findScheduleById(Long id) {
		RouteSchedule schedule = scheduleRepository.findById(id).orElseThrow(() ->
								new DAOException("Schedule [id=" + id + "] cannot be found!", HttpStatus.NOT_FOUND));
		return schedule;
	}
	

	@Override
	public List<RouteSchedule> getScheduleByLine(long id) {
		Line l = lineRepository.findById(id).orElseThrow(() -> 
								new DAOException("Line [id=" + id + "] cannot be found!", HttpStatus.NOT_FOUND));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -32);
		Date earlierDate = c.getTime();
		
		List<RouteSchedule> availableSchedules = scheduleRepository.findByActiveFromGreaterThanAndLine(earlierDate, l);

		return availableSchedules;
	}



	@Override
	public RouteSchedule addSchedule(RouteSchedule schedule, long lineId) {
		Line l = lineService.findById(lineId);
		schedule.setLine(l);
		return scheduleRepository.save(schedule);
	}

	@Override
	public RouteSchedule updateSchedule(RouteSchedule updatedSchedule, long lineId, long scheduleId) {
		RouteSchedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> 
								new DAOException("Schedule [id=" + scheduleId + "] for line [id=" + lineId + 
												 "] cannot be found!", HttpStatus.CONFLICT));
		//Line l = findById(lineId);
		//schedule.setLine(l);
		schedule.setactiveFrom(updatedSchedule.getactiveFrom());
		schedule.setWeekday(updatedSchedule.getWeekday());
		schedule.setSaturday(updatedSchedule.getSaturday());
		schedule.setSunday(updatedSchedule.getSunday());
		return scheduleRepository.save(schedule);
	}

	@Override
	public boolean deleteSchedule(long id) {
		RouteSchedule schedule = findScheduleById(id);
		
		schedule.setActive(false);
		scheduleRepository.save(schedule);
		return true;
	}

	
}
