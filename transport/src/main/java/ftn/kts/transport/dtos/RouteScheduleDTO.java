package ftn.kts.transport.dtos;

import java.util.Date;
import java.util.Set;

public class RouteScheduleDTO {

	private Date activeFrom;
	private Set<Date> weekday;
	private Set<Date> saturday;
	private Set<Date> sunday;
	
	public RouteScheduleDTO() {
		
	}

	public RouteScheduleDTO(Date activeFrom, Set<Date> weekday, Set<Date> saturday, Set<Date> sunday) {
		super();
		this.activeFrom = activeFrom;
		this.weekday = weekday;
		this.saturday = saturday;
		this.sunday = sunday;
	}

	public Date getActiveFrom() {
		return activeFrom;
	}

	public void setActiveFrom(Date activeFrom) {
		this.activeFrom = activeFrom;
	}

	public Set<Date> getWeekday() {
		return weekday;
	}

	public void setWeekday(Set<Date> weekday) {
		this.weekday = weekday;
	}

	public Set<Date> getSaturday() {
		return saturday;
	}

	public void setSaturday(Set<Date> saturday) {
		this.saturday = saturday;
	}

	public Set<Date> getSunday() {
		return sunday;
	}

	public void setSunday(Set<Date> sunday) {
		this.sunday = sunday;
	}	
	
}
