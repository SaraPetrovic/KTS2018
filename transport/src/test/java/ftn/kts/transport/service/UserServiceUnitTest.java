package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.model.User;
import ftn.kts.transport.repositories.UserRepository;
import ftn.kts.transport.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class UserServiceUnitTest {

	@Autowired
	private UserService userService;
	@MockBean
	private UserRepository userRepository;
	
	@Before
	public void setUp() {
		User user = new User("sarapetrovic", "123456789", "Sara", "Petrovic");
		Mockito.when(userRepository.findByUsername("sarapetrovic")).thenReturn(user);
		Mockito.when(userRepository.findByUsername("sarapetrov")).thenReturn(null);
		Mockito.when(userRepository.findByUsername("sara")).thenReturn(null);
		Mockito.when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findById(Long.valueOf(2))).thenThrow(DAOException.class);
		
	}
	
	@Test(expected=DAOException.class)
	public void addUserTestNotOK() {
		User user1 = new User("Petar55", "123456789", "Petar", "Peric");
		User user2 = new User("Ika", "123456789", "Irena", "Peric");
		List<User> users = new ArrayList<User>();
		users.add(user2);
		users.add(user1);
		
		Mockito.when(userRepository.findAll()).thenReturn(users);
		
		userService.addUser("Petar55", "123456789", "Petar", "Peric");
	}
	
	@Test
	public void addUserTestOK() {
		User user1 = new User("Petar55", "123456789", "Petar", "Peric");
		User user2 = new User("Ika", "123456789", "Irena", "Peric");
		List<User> users = new ArrayList<User>();
		users.add(user2);
		users.add(user1);
		
		Mockito.when(userRepository.findAll()).thenReturn(users);
		
		userService.addUser("Petar", "123456789", "Petar", "Petrovic");
		
	}
	
	@Test
	public void loginTestOK() {
		User user = userService.login("sarapetrovic", "123456789");
		
		assertNotNull(user);
	}

	@Test(expected=DAOException.class)
	public void loginTestInvalidUsername() {
		
		userService.login("sara", "123456789");
	}
	
	@Test(expected=DAOException.class)
	public void loginTestInvalidPassword() {
		User user = userService.login("sarapetrovic", "12345678");
		
		assertNotNull(user);
		assertNotEquals("12345678", user.getPassword());
	}
	
	@Test
	public void findByIdTestOK() {
		User user = userService.findById(Long.valueOf(1));
		
		assertNotNull(user);
		assertEquals("Sara", user.getFirstName());
	}
	
	@Test(expected=DAOException.class)
	public void findByIdTestNotOK() {
		userService.findById(Long.valueOf(2));
	}
	
	@Test(expected=DAOException.class)
	public void findByUsernameTestNotFound() {
		userService.findByUsername("sarapetrov");
	}
	
	@Test()
	public void findByUsernameTestOK() {
		User user = userService.findByUsername("sarapetrovic");
		
		assertNotNull(user);
		assertEquals("Petrovic", user.getLastName());
		assertEquals("123456789", user.getPassword());
	}
}
