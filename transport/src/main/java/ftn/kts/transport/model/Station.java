package ftn.kts.transport.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="STATIONS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Station {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String name;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Line> lines;
	@Column
	private boolean active;
	
	public Station() {
		
	}
	
	public Station(String address, String name, Set<Line> lines, boolean active) {
		super();
		this.address = address;
		this.name = name;
		this.lines = lines;
		this.active = active;
	}
	
	public Station(Long id, String address, String name, Set<Line> lines, boolean active) {
		super();
		this.id = id;
		this.address = address;
		this.name = name;
		this.lines = lines;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Line> getLines() {
		return lines;
	}

	public void setLines(Set<Line> lines) {
		this.lines = lines;
	}

	


}
