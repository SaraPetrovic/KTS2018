package ftn.kts.transport.services;

import ftn.kts.transport.model.User;
import ftn.kts.transport.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    public void addUser(String username, String password, String first_name, String last_name){
        userRepository.save(new User(username, password, first_name, last_name));
    }

    public String login(String username, String password) throws Exception{
        User user = userRepository.findByUsername(username);
        if(user.getPassword().equals(password)){
            //TO DO
            return "123";
        }else{
            throw new Exception();
        }

    }
}
