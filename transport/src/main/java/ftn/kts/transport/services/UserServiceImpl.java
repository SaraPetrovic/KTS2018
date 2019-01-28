package ftn.kts.transport.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ftn.kts.transport.enums.DocumentVerification;
import ftn.kts.transport.enums.UserTypeDemographic;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.DocumentUploadException;
import ftn.kts.transport.exception.DocumentVerificationException;
import ftn.kts.transport.model.Role;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;
import ftn.kts.transport.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	public final static String  DEFAULT_IMAGE_FOLDER = "src/main/webapp/images/";
	@Autowired
	private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    public void addUser(String username, String password, String first_name, String last_name){
    	List<User> users = findAll();
    	for(User u: users) {
    		if(u.getUsername().equals(username)) {
    			throw new DAOException("User with the same username already exists", HttpStatus.CONFLICT);
    		}
    	}
    	
    	User user = new User(username, password, first_name, last_name);

    	user.setDocumentVerified(DocumentVerification.NO_DOCUMENT);
    	user.setDocument(null);
    	user.setUserTypeDemo(UserTypeDemographic.NORMAL);
    	user.setTickets(new HashSet<Ticket>());
    	user.setRole(Role.ROLE_CLIENT);
    	userRepository.save(user);
    }

    public User login(String username, String password){
        User user = userRepository.findByUsername(username);
        if(user == null || !user.getPassword().equals(password)) {
        	throw new DAOException("Invalid username or password", HttpStatus.BAD_REQUEST);
        }
		//TO DO
		//user.setRoles(Role.ROLE_CONDUCTER);
		//userRepository.save(user);

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

	@Override
	public User findByUsername(String username) {
		User found = userRepository.findByUsername(username);
		if (found == null) {
			throw new DAOException("User [username=" + username + "] not found!", HttpStatus.NOT_FOUND);
		}
		return found;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public boolean saveDocumentImage(MultipartFile file, String token) {
		// throws AuthorizationException ako ne moze da otpakuje token dobro
		User credentials = jwtService.validate(token.substring(7));
		// throws DAOException if not found!
		User loggedUser = this.findByUsername(credentials.getUsername());
//		if (loggedUser == null) {
//			throw new AuthorizationException("You don't have permission to upload document!");
//		}
		
		String fileName = "";
		if (!file.isEmpty()) {
            try {
            	//String newName = UUID.randomUUID().toString() + ".jpg";
            	fileName = loggedUser.getUsername() + "DOCUMENT.jpg";
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(DEFAULT_IMAGE_FOLDER  + fileName )));
                stream.write(bytes);
                stream.close();
                
        		
            } catch (Exception e) {
                throw new DocumentUploadException("Something went wrong during document upload!");
            }
        }
		loggedUser.setDocument(fileName);
		loggedUser.setDocumentVerified(DocumentVerification.PENDING);
		this.save(loggedUser);
		return true;
	}

	@Override
	public List<User> findUsersByDocumentVerified(DocumentVerification documentVerified) {
		return userRepository.findByDocumentVerified(documentVerified);
	}

	@Transactional
	@Override
	public boolean verifyDocument(Long id, DocumentVerification typeVerification) {
		User toVerify = this.findById(id);
		if (toVerify.getDocument() == null || toVerify.getDocumentVerified().ordinal() == 0) {
			throw new DocumentVerificationException("User [username=" + toVerify.getUsername() + "] did not upload personal document!");
		}
		
		if (toVerify.getDocumentVerified().ordinal() == 2) {
			throw new DocumentVerificationException("Document has been rejected. Please try uploading valid personal document");
		} else if (toVerify.getDocumentVerified().ordinal() == 3) {
			throw new DocumentVerificationException("Document has already been approved!");
		}
		
		toVerify.setDocumentVerified(typeVerification);
		this.save(toVerify);
		return true;
	}
	
	@Override
	public User getUser(String token) {
		User credentials = jwtService.validate(token.substring(7));
		User ret = findByUsername(credentials.getUsername());
		return ret;
	}


}
