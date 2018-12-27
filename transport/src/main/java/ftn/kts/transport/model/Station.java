package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="STATIONS")
public class Station implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String name;
	@OneToMany(mappedBy = "station")
	private Set<LineAndStation> lineSet;
	@Column
	private boolean active;

	

	public Station() {
		
	}


	public Station(Long id, String address, String name, Set<LineAndStation> lineSet, boolean active) {
		super();
		this.id = id;
		this.address = address;
		this.name = name;
		this.lineSet = lineSet;
		this.active = active;
	}
	
	public Station(String address, String name, HashSet<LineAndStation> lineSet, boolean active) {
		super();
		this.address = address;
		this.name = name;
		this.lineSet = lineSet;
		this.active = active;
	}
	
	public Station(Long id, String address, String name, boolean active) {
		super();
		this.address = address;
		this.name = name;
		this.active = active;
	}
	
	public Station(String address, String name, boolean active) {
		super();
		this.address = address;
		this.name = name;
		this.active = active;
	}
	
	@JsonManagedReference
	public Set<LineAndStation> getLineSet() {
		return lineSet;
	}

	public void setLineSet(Set<LineAndStation> lineSet) {
		this.lineSet = lineSet;
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


}
