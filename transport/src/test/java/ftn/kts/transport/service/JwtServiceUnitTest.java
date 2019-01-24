package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import ftn.kts.transport.exception.TokenValidationException;
import ftn.kts.transport.model.User;
import ftn.kts.transport.security.JwtGenerator;
import ftn.kts.transport.security.JwtValidator;
import ftn.kts.transport.services.JwtGeneratorService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class JwtServiceUnitTest {

	@Autowired 
	private JwtGeneratorService jwtService;
	
	@MockBean
	private JwtValidator jwtValidatorMocked;
	
	@MockBean
	private JwtGenerator jwtGeneratorMocked;
	
	private String validToken, invalidToken;
	private User user = new User();
	
	@Before
	public void setUp() {
		this.validToken = "Bearer VALID Token";
		this.invalidToken = "Bearer INVALID Token";
		
		user.setId(1L);
		user.setUsername("user1");
		user.setPassword("1234");
		
		Mockito.when(jwtValidatorMocked.validate(this.validToken.substring(7))).thenReturn(user);
		Mockito.when(jwtValidatorMocked.validate(this.invalidToken.substring(7))).thenThrow(new TokenValidationException("Invalid token!"));
	
		Mockito.when(jwtGeneratorMocked.generate(user)).thenReturn(this.validToken);
	}
	
	@Test
	public void validate_PASS_Test() {
		User ret = jwtService.validate(this.validToken.substring(7));
		assertNotNull(ret);
		assertEquals(user.getId(), ret.getId());
		assertEquals(user.getUsername(), ret.getUsername());
	}
	
	@Test(expected = TokenValidationException.class)
	public void validate_InvalidToken_Test() {
		jwtService.validate(this.invalidToken.substring(7));
	}
	
	@Test
	public void generateToken_PASS_Test() {
		String token = jwtService.generate(this.user);
		assertEquals(this.validToken, token);
	}
}
