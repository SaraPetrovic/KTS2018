package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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
	private Date activeFrom;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="WeekdaySchedule", joinColumns=@JoinColumn(name="route_schedule_id"))
	@Column
	@Temporal(TemporalType.TIME)
	private Set<Date> weekday;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="SaturdaySchedule", joinColumns=@JoinColumn(name="route_schedule_id"))
	@Column
	@Temporal(TemporalType.TIME)
	private Set<Date> saturday;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="SundaySchedule", joinColumns=@JoinColumn(name="route_schedule_id"))
	@Column
	@Temporal(TemporalType.TIME)
	private Set<Date> sunday;
	@OneToOne(fetch = FetchType.EAGER)
	private Line line;
	@Column
	private boolean active;			// logical delete
	
	public RouteSchedule() {
		
	}


	public RouteSchedule(Long id, Date activeFrom, Set<Date> weekday, Set<Date> saturday, Set<Date> sunday,
			boolean active) {
		super();
		this.id = id;
		this.activeFrom = activeFrom;
		this.weekday = weekday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.active = active;
	}



	public RouteSchedule(Long id, Date activeFrom, Set<Date> weekday, Set<Date> saturday, Set<Date> sunday,
			Line line, boolean active) {
		super();
		this.id = id;
		this.activeFrom = activeFrom;
		this.weekday = weekday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.line = line;
		this.active = active;
	}


	public Long getId() {
		return id;
	}


	public Date getactiveFrom() {
		return activeFrom;
	}

	public void setactiveFrom(Date activeFrom) {
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}
	
	
}
