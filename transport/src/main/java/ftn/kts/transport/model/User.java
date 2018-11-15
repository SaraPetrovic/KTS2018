package ftn.kts.transport.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ftn.kts.transport.enums.UserType;

@Entity
@Table(
        name = "USERS"

)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable {

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
	@Column
	private String document;
	@Column
	private boolean documentVerified;
	@OneToMany(mappedBy = "user")
	private Set<Ticket> tickets;
	
    public User() {
    	
    }

    public User(String username, String password, String firstName, String lastName){
    	this.username = username;
    	this.password = password;
    	this.firstName = firstName;
    	this.lastName = lastName;
	}

	public User(String username, String password, String firstName, String lastName, Role roles, String document,
			boolean documentVerified) {
		this(username, password, firstName, lastName);
		this.roles = roles;
		this.document = document;
		this.documentVerified = documentVerified;
	}
	
	public User(Long id, String username, String password, String firstName, String lastName, Role roles,
			String document, boolean documentVerified, Set<Ticket> tickets) {
		this(username, password, firstName, lastName);
    	this.id = id;
		this.roles = roles;
		this.document = document;
		this.documentVerified = documentVerified;
		this.tickets = tickets;
	}

	public User(String username, String password, String firstName, String lastName, Role roles, String document,
			boolean documentVerified, Set<Ticket> tickets) {
		this(username, password, firstName, lastName);
		this.roles = roles;
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

	public Set<Ticket> getTickets() { return tickets; }

	public void setTickets(Set<Ticket> tickets) { this.tickets = tickets; }
    
	
    
}
