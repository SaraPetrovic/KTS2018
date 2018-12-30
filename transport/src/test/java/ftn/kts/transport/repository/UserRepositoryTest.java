package ftn.kts.transport.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.transport.model.User;
import ftn.kts.transport.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setUp() {
		User user = new User("sarapetrovic", "123456789", "Sara", "Petrovic");
		entityManager.persist(user);
		entityManager.flush();
	}
	
	@Test
	public void findByUsernameTestOK() {
		User user = userRepository.findByUsername("sarapetrovic");
		
		assertNotNull(user);
		assertEquals("123456789", user.getPassword());
	}
	
	@Test
	public void findByUsernameTestNotOK() {
		User user = userRepository.findByUsername("sara");
		
		assertNull(user);
	}
}
