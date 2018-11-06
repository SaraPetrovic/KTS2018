package ftn.kts.transport.model;

import javax.persistence.*;
import java.util.Set;

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
	
	public Station() {
		
	}

	public Station(String address, String name, Set<Line> lines) {
		super();
		this.address = address;
		this.name = name;
		this.lines = lines;
	}

	public Station(Long id, String address, String name, Set<Line> lines) {
		super();
		this.id = id;
		this.address = address;
		this.name = name;
		this.lines = lines;
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
