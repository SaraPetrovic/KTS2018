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

import ftn.kts.transport.enums.DocumentVerification;
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
	private Role role;
	@Enumerated
	private UserTypeDemographic userTypeDemo;
	@Column
	private String document;
	@Enumerated
	private DocumentVerification documentVerified;
	@OneToMany(mappedBy = "user")
	private Set<Ticket> tickets;
	
    public User() {
    	
    }


	public User(String username, String password, String firstName, String lastName, Role role, UserTypeDemographic userTypeDemo, String document, DocumentVerification documentVerified, Set<Ticket> tickets) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.userTypeDemo = userTypeDemo;
		this.document = document;
		this.documentVerified = documentVerified;
		this.tickets = tickets;
	}

	public User(Long id, String username, String password, Set<Ticket> tickets) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.tickets = tickets;
	}


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User(String username, String password, String firstName, String lastName) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(Long id, String username, String password, String firstName, String lastName) {
		super();
		this.id = id;
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
    	roles.add(this.role);
		return roles;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public DocumentVerification getDocumentVerified() {
		return documentVerified;
	}

	public void setDocumentVerified(DocumentVerification documentVerified) {
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
    
}
