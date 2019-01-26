package ftn.kts.transport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.dtos.UserDTO;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class UserControllerIntegrationTest {


	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void addUserTestOK() {
		int size = userService.findAll().size();
		
		UserDTO dtoUser = new UserDTO("Sara", "12345678", "Sara", "Petrovic");
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.postForEntity("/user", dtoUser, Void.class);
		
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
		assertEquals(size + 1, userService.findAll().size());
	}
	
	@Test
	public void addUserTestExistsUsername() {
		int size = userService.findAll().size();
		
		UserDTO dtoUser = new UserDTO("user3", "12345678", "Sara", "Petrovic");
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.postForEntity("/user", dtoUser, Void.class);
		
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		assertEquals(size, userService.findAll().size());
	}
	
	@Test
	public void addUserTestBadRequest() {
		int size = userService.findAll().size();
		
		UserDTO dtoUser = new UserDTO("Sara", "", "Sara", "Petrovic");
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.postForEntity("/user", dtoUser, Void.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(size, userService.findAll().size());
	}
	
	@Test
	public void addUserTestInvalidPass() {
		int size = userService.findAll().size();
		
		UserDTO dtoUser = new UserDTO("Sara", "12345", "Sara", "Petrovic");
		
		ResponseEntity<Void> responseEntity =
				restTemplate.postForEntity("/user", dtoUser, Void.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(size, userService.findAll().size());
	}
	
	
	@Test
	public void updateTestNotFoundUser() {
		
		ResponseEntity<UserDTO> responseEntity =
	            restTemplate.exchange("/user", HttpMethod.PUT, 
	            		new HttpEntity<UserDTO>(new UserDTO(Long.valueOf(13), "Sara", "12345", "Sara", "Petrovic")),
	            		UserDTO.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateTestOK() {
		
		ResponseEntity<UserDTO> responseEntity =
	            restTemplate.exchange("/user", HttpMethod.PUT, 
	            		new HttpEntity<UserDTO>(new UserDTO(Long.valueOf(2), "SaraPetrovic", "123456789", "Sara", "Petrovic")),
	            		UserDTO.class);
		
		UserDTO rez = responseEntity.getBody();
		
		assertEquals("Sara", rez.getFirstName());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
}
