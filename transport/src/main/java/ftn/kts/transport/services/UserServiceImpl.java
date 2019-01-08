package ftn.kts.transport.services;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ftn.kts.transport.enums.UserTypeDemographic;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.Role;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;
import ftn.kts.transport.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
	private HttpServletRequest request;

    public void addUser(String username, String password, String first_name, String last_name){
    	User u = new User(username, password, first_name, last_name);
    	u.setMoneyBalance(Double.valueOf(0));
    	u.setDocumentVerified(false);
    	u.setUserTypeDemo(UserTypeDemographic.NORMAL);
    	u.setTickets(new HashSet<Ticket>());
    	u.setRoles(Role.ROLE_CLIENT);
    	userRepository.save(u);
    }

    public User login(String username, String password){
        User user = userRepository.findByUsername(username);
        if(user == null || !user.getPassword().equals(password)) {
        	throw new DAOException("Invalid username or password", HttpStatus.BAD_REQUEST);
        }
		//TO DO
		user.setRoles(Role.ROLE_CLIENT);
		userRepository.save(user);

		//HttpSession session = request.getSession();
		//session.setAttribute("user", user);
		return user;
    }

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new DAOException("User[id=" + id + "] not found!", HttpStatus.NOT_FOUND));
	}

	@Override
	public Set<Ticket> getTickets(Long id) {
		User user = findById(id);
		Set<Ticket> tickets = user.getTickets();
		return tickets;
	}

}
