package ftn.kts.transport.services;

public interface UserService  {

    void addUser(String username, String password, String first_name, String last_name);
    String login(String usename, String password) throws Exception;
}
