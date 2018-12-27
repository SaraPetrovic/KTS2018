package ftn.kts.transport.services;

import ftn.kts.transport.model.User;

public interface UserService  {

    void addUser(String username, String password, String first_name, String last_name);
    User login(String usename, String password) throws Exception;
	User save(User user);
	User findById(Long id);
}
