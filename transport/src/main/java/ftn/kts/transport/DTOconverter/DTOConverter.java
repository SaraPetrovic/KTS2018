package ftn.kts.transport.DTOconverter;

import ftn.kts.transport.dtos.LineDTO;
import ftn.kts.transport.dtos.RouteScheduleDTO;
import ftn.kts.transport.enums.VehicleType;
import ftn.kts.transport.model.Line;
import ftn.kts.transport.model.RouteSchedule;

public interface DTOConverter {

	public static Line convertDTOtoLine(LineDTO lineDTO) {
		Line l = new Line();
		l.setName(lineDTO.getName());
		l.setTransportType(VehicleType.values()[lineDTO.getVehicleType()]);
		return l;
	}
	
	public static RouteSchedule convertDTOtoRouteSchedule(RouteScheduleDTO scheduleDTO) {
		RouteSchedule schedule = new RouteSchedule();
		schedule.setActive(true);
		schedule.setactiveFrom(scheduleDTO.getActiveFrom());
		schedule.setSaturday(scheduleDTO.getSaturday());
		schedule.setSunday(scheduleDTO.getSunday());
		schedule.setWeekday(scheduleDTO.getWeekday());
		return schedule;
	}
}
