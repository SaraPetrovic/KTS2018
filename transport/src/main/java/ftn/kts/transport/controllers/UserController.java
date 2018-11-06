package ftn.kts.transport.controllers;

import ftn.kts.transport.dtos.UserDTO;
import ftn.kts.transport.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping( consumes = {"application/json"} )
    public ResponseEntity addUser(@RequestBody UserDTO userDTO){

        try{
            userService.addUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstName(), userDTO.getLastName());

        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping( path = "/login", consumes = {"application/json"} )
    public ResponseEntity loginUser(@RequestBody UserDTO userDTO){
        String token;
        try{
            token = userService.login(userDTO.getUsername(), userDTO.getPassword());

        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
    }
}
