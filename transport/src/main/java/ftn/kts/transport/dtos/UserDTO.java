package ftn.kts.transport.dtos;

import ftn.kts.transport.model.User;

public class UserDTO {

	private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String repeatedPassword;
    private double money;

    public UserDTO() {  }

    public UserDTO(Long id, String username, String password, String firstName, String lastName, String repeatedPassword, double money) {
        this.id = id;
    	this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.repeatedPassword = repeatedPassword;
        this.money = money;
    }
    
    public UserDTO(User user) {
    	this.id = user.getId();
    	this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.money = user.getMoneyBalance();
    }
    
    public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
    
}
