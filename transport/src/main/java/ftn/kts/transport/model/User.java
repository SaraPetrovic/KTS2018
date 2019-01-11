package ftn.kts.transport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ftn.kts.transport.enums.UserTypeDemographic;

@Entity
@Table(name = "USERS")

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String username;
	@Column
	private String password;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private Role roles;
	@Enumerated
	private UserTypeDemographic userTypeDemo;
	@Column
	private String document;
	@Column
	private boolean documentVerified;
	@OneToMany(mappedBy = "user")
	private Set<Ticket> tickets;
	@Column
	private double moneyBalance;
	
    public User() {
    	
    }

	public User(Long id, String username, String password, String firstName, String lastName, Role roles,
			UserTypeDemographic userTypeDemo, String document, boolean documentVerified, Set<Ticket> tickets, double moneyBalance) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
		this.userTypeDemo = userTypeDemo;
		this.document = document;
		this.documentVerified = documentVerified;
		this.tickets = tickets;
		this.moneyBalance = moneyBalance;
	}
	

	public User(String username, String password, String firstName, String lastName) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Role> getRoles() {
    	List<Role> roles = new ArrayList<>();
    	roles.add(this.roles);
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public boolean isDocumentVerified() {
		return documentVerified;
	}

	public void setDocumentVerified(boolean documentVerified) {
		this.documentVerified = documentVerified;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	@JsonBackReference
	public Set<Ticket> getTickets() { return tickets; }

	public void setTickets(Set<Ticket> tickets) { this.tickets = tickets; }

	public UserTypeDemographic getUserTypeDemo() {
		return userTypeDemo;
	}

	public void setUserTypeDemo(UserTypeDemographic userTypeDemo) {
		this.userTypeDemo = userTypeDemo;
	}

	public double getMoneyBalance() {
		return moneyBalance;
	}

	public void setMoneyBalance(double moneyBalance) {
		this.moneyBalance = moneyBalance;
	}
    
}
