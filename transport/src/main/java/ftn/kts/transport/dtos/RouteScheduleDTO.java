package ftn.kts.transport.dtos;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;



public class RouteScheduleDTO {

	private static final String TZ = "Europe/Belgrade";
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy.")
	private Date activeFrom;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = TZ)
	private Set<Date> weekday;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = TZ)
	private Set<Date> saturday;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = TZ)
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
