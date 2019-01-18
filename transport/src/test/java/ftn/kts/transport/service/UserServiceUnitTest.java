package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
		Mockito.when(userRepository.findByUsername("sara")).thenReturn(null);
		Mockito.when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findById(Long.valueOf(2))).thenThrow(DAOException.class);
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
}
