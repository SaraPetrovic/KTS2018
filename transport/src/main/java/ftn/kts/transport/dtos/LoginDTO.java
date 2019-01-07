package ftn.kts.transport.dtos;

public class LoginDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String token;

    public LoginDTO() {
    }

    public LoginDTO(String username, String firstName, String lastName, String token) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
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
