package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ftn.kts.transport.enums.VehicleType;

@Entity
public class RouteSchedule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateActivated;
	@ElementCollection
	@CollectionTable(name="WeekdaySchedule", joinColumns=@JoinColumn(name="route_schedule_id"))
	@Column
	@Temporal(TemporalType.TIME)
	private Set<Date> weekday;
	@ElementCollection
	@CollectionTable(name="SaturdaySchedule", joinColumns=@JoinColumn(name="route_schedule_id"))
	@Column
	@Temporal(TemporalType.TIME)
	private Set<Date> saturday;
	@ElementCollection
	@CollectionTable(name="SundaySchedule", joinColumns=@JoinColumn(name="route_schedule_id"))
	@Column
	@Temporal(TemporalType.TIME)
	private Set<Date> sunday;
	@Enumerated
	private VehicleType typeOfTransport;
	@Column
	private boolean active;
	
	public RouteSchedule() {
		
	}


	public RouteSchedule(Long id, Date dateActivated, Set<Date> weekday, Set<Date> saturday, Set<Date> sunday,
			VehicleType typeOfTransport, boolean active) {
		super();
		this.id = id;
		this.dateActivated = dateActivated;
		this.weekday = weekday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.typeOfTransport = typeOfTransport;
		this.active = active;
	}



	public Long getId() {
		return id;
	}


	public Date getDateActivated() {
		return dateActivated;
	}

	public void setDateActivated(Date dateActivated) {
		this.dateActivated = dateActivated;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public VehicleType getTypeOfTransport() {
		return typeOfTransport;
	}

	public void setTypeOfTransport(VehicleType typeOfTransport) {
		this.typeOfTransport = typeOfTransport;
	}
	
	
	
	
	
}
