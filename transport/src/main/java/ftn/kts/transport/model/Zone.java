package ftn.kts.transport.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="ZONES")
public class Zone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String name;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Station> stations;
	@OneToOne
	private Zone subZone;
	@Column
	private boolean active;
	
	public Zone() {
		
	}

	public Zone(Long id, String name, Set<Station> stations, Zone subZone, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.stations = stations;
		this.subZone = subZone;
		this.active = active;
	}

	public Zone(String name, Set<Station> stations, Zone subZone, boolean active) {
		super();
		this.name = name;
		this.stations = stations;
		this.subZone = subZone;
		this.active = active;
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

	public Set<Station> getStations() {
		return stations;
	}

	public void setStations(Set<Station> stations) {
		this.stations = stations;
	}

	public Zone getSubZone() {
		return subZone;
	}

	public void setSubZone(Zone subZone) {
		this.subZone = subZone;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	
	
}
