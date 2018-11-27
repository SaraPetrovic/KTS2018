package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="KTS_LINES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Line implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@OneToMany(mappedBy = "line")
	private Set<LineAndStation> stationSet;
	@Column
	private boolean active;

	
	
	public Line() {
		this.active = true;
	}

	public Line(Long id, String name, Set<LineAndStation> stationSet, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.stationSet = stationSet;
		this.active = active;
	}


	public Set<LineAndStation> getStationSet() {
		return stationSet;
	}

	public void setStationSet(Set<LineAndStation> stationSet) {
		this.stationSet = stationSet;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void addStation(Station station) { 
		LineAndStation las = new LineAndStation();
		las.addStation(this, station, getStationSet().size());
	}
	
	
	
	
}
