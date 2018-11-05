package ftn.kts.transport.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ftn.kts.transport.enums.UserType;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private UserType userType;
	@Column
	private String document;
	@Column
	private boolean documentVerified;
	@OneToMany(mappedBy = "user")
	private Set<Ticket> tickets;
	
    public User() {
    	
    }

	public User(String username, String password, String firstName, String lastName, UserType userType, String document,
			boolean documentVerified) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.document = document;
		this.documentVerified = documentVerified;
	}
	
	public User(Long id, String username, String password, String firstName, String lastName, UserType userType,
			String document, boolean documentVerified, Set<Ticket> tickets) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.document = document;
		this.documentVerified = documentVerified;
		this.tickets = tickets;
	}

	public User(String username, String password, String firstName, String lastName, UserType userType, String document,
			boolean documentVerified, Set<Ticket> tickets) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.document = document;
		this.documentVerified = documentVerified;
		this.tickets = tickets;
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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
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

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
    
	
    
}
