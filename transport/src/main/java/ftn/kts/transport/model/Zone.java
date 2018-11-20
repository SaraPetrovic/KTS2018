package ftn.kts.transport.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="ZONES")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Zone implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String name;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Station> stations;
	@Column
	private boolean active;
	
	public Zone() {
		
	}

	public Zone(String name, Set<Station> stations, boolean active) {
		super();
		this.name = name;
		this.stations = stations;
		this.active = active;
	}

	public Zone(Long id, String name, Set<Station> stations, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.stations = stations;
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
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
	
	
	
	
}
