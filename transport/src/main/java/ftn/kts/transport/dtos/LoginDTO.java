package ftn.kts.transport.dtos;

import ftn.kts.transport.model.Role;

public class LoginDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String token;
    private Role role;

    public LoginDTO() {
    }
    

    public LoginDTO(String username, String firstName, String lastName, String password, String token, Role role) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.token = token;
		this.role = role;
	}


	public LoginDTO(String username, String firstName, String lastName, String password, String token) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.token = token;
    }
	

    public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
