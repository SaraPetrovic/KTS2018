package ftn.kts.transport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ftn.kts.transport.dtos.UserDTO;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.User;
import ftn.kts.transport.services.JwtService;
import ftn.kts.transport.services.JwtServiceImpl;
import ftn.kts.transport.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class UserControllerIntegrationTest {


	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private UserService userService;
	
	 @Autowired
	 private JwtService jwtService;
	
	@Test
	public void addUserTestOK() {
		int size = userService.findAll().size();
		
		UserDTO dtoUser = new UserDTO("Sara", "12345678", "Sara", "Petrovic");
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.postForEntity("/user/register", dtoUser, Void.class);
		
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
		assertEquals(size + 1, userService.findAll().size());
	}
	
	@Test
	public void addUserTestExistsUsername() {
		int size = userService.findAll().size();
		
		UserDTO dtoUser = new UserDTO("user3", "12345678", "Sara", "Petrovic");
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.postForEntity("/user/register", dtoUser, Void.class);
		
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		assertEquals(size, userService.findAll().size());
	}
	
	@Test
	public void addUserTestBadRequest() {
		int size = userService.findAll().size();
		
		UserDTO dtoUser = new UserDTO("Sara", "", "Sara", "Petrovic");
		
		ResponseEntity<Void> responseEntity = 
				restTemplate.postForEntity("/user/register", dtoUser, Void.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(size, userService.findAll().size());
	}
	
	@Test
	public void addUserTestInvalidPass() {
		int size = userService.findAll().size();
		
		UserDTO dtoUser = new UserDTO("Sara", "12345", "Sara", "Petrovic");
		
		ResponseEntity<Void> responseEntity =
				restTemplate.postForEntity("/user/register", dtoUser, Void.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(size, userService.findAll().size());
	}
	
	@Test
	public void loginTestOK() {
		UserDTO dtoUser = new UserDTO("user3", "12345678", "Sara", "Petrovic");
		
		ResponseEntity<Object> responseEntity =
				restTemplate.postForEntity("/user/login", dtoUser, Object.class);
		
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());	
	}
	
	@Test(expected=RestClientException.class)
	public void loginTestBadRequestPassword() {
		UserDTO dtoUser = new UserDTO("user3", "123", "Sara", "Petrovic");
		
		restTemplate.postForEntity("/user/login", dtoUser, Object.class);
		
	}
	
	@Test(expected=RestClientException.class)
	public void loginTestBadRequestUsername() {
		UserDTO dtoUser = new UserDTO("user", "12345678", "Sara", "Petrovic");
		
		restTemplate.postForEntity("/user/login", dtoUser, Object.class);
	}
	
	@Test
	public void acceptDocumentTestOK() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);

		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);

		ResponseEntity<Void> responseEntity =
				restTemplate.exchange("/rest/user/" + Long.valueOf(9) + "/accept", HttpMethod.PUT,
						entity, Void.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Transactional
	@Rollback
	@Test
	public void acceptDocumentTestConflict() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);

		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);

		ResponseEntity<Void> responseEntity =
				restTemplate.exchange("/rest/user/" + Long.valueOf(8) + "/accept", HttpMethod.PUT,
						entity, Void.class);
		
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
	}
	
	@Transactional
	@Rollback
	@Test
	public void declineDocumentTestOK() {

		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);

		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);

		ResponseEntity<Void> responseEntity =
				restTemplate.exchange("/rest/user/" + Long.valueOf(10) + "/decline", HttpMethod.PUT,
						entity, Void.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Transactional
	@Rollback
	@Test
	public void declineDocumentTestConflict() {

		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);

		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);

		ResponseEntity<Void> responseEntity =
				restTemplate.exchange("/rest/user/" + Long.valueOf(8) + "/decline", HttpMethod.PUT,
						entity, Void.class);
		
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateTestInvalidToken() {
		
		String token = "eyJhiOiJIUzUxMiJ9.eyJzdWIiOiJzYXJhMTIzIiwianRpIjoiMTIzNDU2NzgiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.GYth_fjbs7f7GoWJR-OiY7S_Qaz_xSBnGJbfo0b1egjEn-_JU001BcFdVG7hRO1xkhoCw7xZmMGKIbeUQPVV0A";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(new UserDTO(Long.valueOf(2), "sara123", "123456789", "Sara", "Petrovic"), headers);

		ResponseEntity<UserDTO> responseEntity =
				restTemplate.exchange("/rest/user", HttpMethod.PUT,
						entity, UserDTO.class);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateTestOK() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYXJhMTIzIiwianRpIjoiMTIzNDU2NzgiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.GYth_fjbs7f7GoWJR-OiY7S_Qaz_xSBnGJbfo0b1egjEn-_JU001BcFdVG7hRO1xkhoCw7xZmMGKIbeUQPVV0A";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(new UserDTO(Long.valueOf(2), "sara123", "123456789", "Sara", "Petrovic"), headers);

		ResponseEntity<UserDTO> responseEntity =
				restTemplate.exchange("/rest/user", HttpMethod.PUT,
						entity, UserDTO.class);
		
		UserDTO rez = responseEntity.getBody();
		
		assertEquals("sara123", rez.getUsername());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	
	@Test
	public void updateTestBadPassword() {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYXJhMTIzIiwianRpIjoiMTIzNDU2NzgiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.GYth_fjbs7f7GoWJR-OiY7S_Qaz_xSBnGJbfo0b1egjEn-_JU001BcFdVG7hRO1xkhoCw7xZmMGKIbeUQPVV0A";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);
		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(new UserDTO(Long.valueOf(2), "sara123", "56789", "Sara", "Petrovic"), headers);

		ResponseEntity<UserDTO> responseEntity =
				restTemplate.exchange("/rest/user", HttpMethod.PUT,
						entity, UserDTO.class);
		
		UserDTO rez = responseEntity.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

	}

	@Test
	public void getUsersForVerificationTest() {

		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImp0aSI6ImFkbWluYWRtaW4iLCJyb2xlIjoiUk9MRV9BRE1JTiJ9.ImjRRRG8261xMw9Tf74FjURd2vxpWxJF4ALnMDmftCezVWWs-rwG-eOXA0cvHKv-neHbGycSFs0dj1h0up1g9w";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "Spring's RestTemplate" );
		headers.add("Authorization", "Bearer "+ token);

		HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);

		ResponseEntity<User[]> responseEntity =
				restTemplate.exchange("/rest/user/verify", HttpMethod.GET,
						entity, User[].class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
