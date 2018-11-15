package ftn.kts.transport.controllers;

import ftn.kts.transport.dtos.UserDTO;
import ftn.kts.transport.model.User;
import ftn.kts.transport.security.JwtGenerator;
import ftn.kts.transport.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping( path = "/user" ,consumes = {"application/json"} )
    public ResponseEntity addUser(@RequestBody UserDTO userDTO){

        try{
            userService.addUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstName(), userDTO.getLastName());

        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping( path = "/login", consumes = {"application/json"} )
    public ResponseEntity loginUser(@RequestBody UserDTO userDTO){

        try {
            User user = userService.login(userDTO.getUsername(), userDTO.getPassword());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtGenerator.generate(user));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/rest/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin(){
        return "Hello World";
    }

    @GetMapping(path = "/rest/user")
    @PreAuthorize("hasRole('CLIENT')")
    public String helloClient(){return "Hello World Client";}
}
