package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ftn.kts.transport.enums.VehicleType;

@Entity
@Table(name="KTS_LINES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Line implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String name;
	@Enumerated
	private VehicleType transportType;
	@OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
	private Set<LineAndStation> stationSet;
	@ElementCollection
	@CollectionTable(name="StreetPath", joinColumns=@JoinColumn(name="line_id"))
	@Column
	private Set<String> streetPath;
	@Column
	private boolean active;

	
	
	public Line() {
		this.active = true;
		this.stationSet = new HashSet<LineAndStation>();
		this.streetPath = new HashSet<String>();
	}

	public Line(Long id, String name, Set<LineAndStation> stationSet, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.stationSet = stationSet;
		this.active = active;
	}


	public Line(Long id, String name, VehicleType transportType, Set<LineAndStation> stationSet, Set<String> streetPath,
			boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.transportType = transportType;
		this.stationSet = stationSet;
		this.streetPath = streetPath;
		this.active = active;
	}

	@JsonManagedReference
	public Set<LineAndStation> getStationSet() {
		return stationSet;
	}
	
	@JsonIgnore
	public HashMap<Integer, Station> getStationAndOrder() {
		HashMap<Integer, Station> temp = new HashMap<Integer, Station>();
		for (LineAndStation ls : stationSet) {
			temp.put(ls.getStationOrder(), ls.getStation());
		};
		
		return temp;
	}

	public void setStationSet(Set<LineAndStation> stationSet) {
		this.stationSet = stationSet;
	}

	public VehicleType getTransportType() {
		return transportType;
	}

	public void setTransportType(VehicleType transportType) {
		this.transportType = transportType;
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
	
	public void addStation(Station station, int order) { 
		LineAndStation las = new LineAndStation();
		las.addStation(this, station, order);
		this.stationSet.add(las);
		
	}

	public Set<String> getStreetPath() {
		return streetPath;
	}

	public void setStreetPath(Set<String> streetPath) {
		this.streetPath = streetPath;
	}
	
	
	
	
	
	
}
