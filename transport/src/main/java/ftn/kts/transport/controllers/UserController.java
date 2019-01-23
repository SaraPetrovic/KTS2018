package ftn.kts.transport.controllers;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ftn.kts.transport.dtos.LoginDTO;
import ftn.kts.transport.dtos.UserDTO;
import ftn.kts.transport.enums.DocumentVerification;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.InvalidInputDataException;
import ftn.kts.transport.model.Ticket;
import ftn.kts.transport.model.User;
import ftn.kts.transport.services.JwtGeneratorService;
import ftn.kts.transport.services.UserService;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtGeneratorService jwtService;

    @PostMapping(consumes = {"application/json"} )
    //@PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Void> addUser(@RequestBody UserDTO userDTO){
    	if(userDTO.getUsername() == "" || userDTO.getPassword() == "" || userDTO.getFirstName() == "" || userDTO.getLastName() == ""
    			|| userDTO.getUsername() == null || userDTO.getPassword() == null || userDTO.getFirstName() == null || userDTO.getLastName() == null) {
    		throw new InvalidInputDataException("You must entered required data", HttpStatus.BAD_REQUEST);
    	}
    	if(userDTO.getPassword().length() >= 8) {
    		userService.addUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstName(), userDTO.getLastName());
    	}else{
    		throw new InvalidInputDataException("Password must contain at least eight characters", HttpStatus.BAD_REQUEST);
    	}
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping( path = "/login", consumes = {"application/json"} )
    //@PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @CrossOrigin( origins = "http://localhost:4200")
    public ResponseEntity<Object> loginUser(@RequestBody UserDTO userDTO){

        try {
            User user = userService.login(userDTO.getUsername(), userDTO.getPassword());
            LoginDTO responseBody = new LoginDTO(user.getUsername(), user.getFirstName(), user.getLastName(), user.getPassword(), jwtService.generate(user));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseBody);
        }
        catch(DAOException e){
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
    
    @GetMapping(path="/tickets/{id}", produces="application/json")
    public ResponseEntity<Set<Ticket>> getTickets(@PathVariable long id){
		Set<Ticket> tickets = userService.getTickets(id);
		return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
    
    @PutMapping(consumes = {"application/json"}, produces="application/json")
    //@PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @CrossOrigin( origins = "http://localhost:4200")
    public ResponseEntity<UserDTO> update(@RequestHeader("Authorization") final String token, @RequestBody UserDTO userDto){
		System.out.println("USAAAOO!!!!");
    	User u = jwtService.validate(token.substring(7));
    	User user = userService.findByUsername(u.getUsername());
    	
    	if(userDto.getUsername() == "" || userDto.getPassword() == "" || userDto.getFirstName() == "" || userDto.getLastName() == ""
    			|| userDto.getUsername() == null || userDto.getPassword() == null || userDto.getFirstName() == null || userDto.getLastName() == null) {
    		throw new InvalidInputDataException("You must entered required data", HttpStatus.BAD_REQUEST);
    	}
    	System.out.println(userDto.getFirstName() + " AAAAAAAAAAA " + userDto.getLastName());
    	if(userDto.getPassword().length() >= 8) {
    		user.setUsername(userDto.getUsername());
    		user.setPassword(userDto.getPassword());
    		user.setFirstName(userDto.getFirstName());
    		user.setLastName(userDto.getLastName());
    	}else if(userDto.getPassword().length() < 8) {
    		throw new InvalidInputDataException("Password must contain at least eight characters", HttpStatus.BAD_REQUEST);
    	}
    	userService.save(user);
    	return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }
    
    @GetMapping(path = "/rest/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin(){
        return "Hello World";
    }

    @GetMapping(path = "/rest/user")
    @PreAuthorize("hasRole('CLIENT')")
    public String helloClient() {
    	return "Hello World Client";
    }
    
    @PostMapping(path = "/document")
    @Consumes("multipart/form-data")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Boolean> uploadDocumentImage(@RequestParam("file") MultipartFile file, 
    													@RequestHeader("Authorization") String token) {
    	boolean ret = userService.saveDocumentImage(file, token);
    	
    	return new ResponseEntity<>(ret, HttpStatus.OK);
    }
    
    @PutMapping(path = "/{id}/verify")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> verifyDocument(@PathVariable("id") long id) {
    	boolean ret = userService.verifyDocument(id);
    	return new ResponseEntity<>(ret, HttpStatus.OK);
    }
    
    @GetMapping(path = "/verify")
    @Produces("application/json")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsersForVerification() {
    	List<User> toVerify = userService.findUsersByDocumentVerified(DocumentVerification.PENDING);
    	return new ResponseEntity<>(toVerify, HttpStatus.OK);
    }
}
