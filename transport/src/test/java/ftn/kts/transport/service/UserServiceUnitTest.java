package ftn.kts.transport.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
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
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ftn.kts.transport.enums.DocumentVerification;
import ftn.kts.transport.exception.AuthorizationException;
import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.DocumentVerificationException;
import ftn.kts.transport.exception.TokenValidationException;
import ftn.kts.transport.model.User;
import ftn.kts.transport.repositories.UserRepository;
import ftn.kts.transport.services.JwtGeneratorService;
import ftn.kts.transport.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class UserServiceUnitTest {

	@Autowired
	private UserService userService;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private JwtGeneratorService jwtServiceMocked;
	@SpyBean
	private UserService userServiceSpy;		// za mockovanje metode iz servisa koji se testira
	
	private String token, invalidToken;
	private User credentials = new User();
	private User logged = new User();
	private String imageFolder = "src/main/webapp/images/";
	
	@Before
	public void setUp() {
		
		this.token = "Bearer VALID Token";
		this.invalidToken = "aaaaaaaaaaaaaaa";
		this.credentials.setUsername("user1");
		this.credentials.setPassword("1234");
		this.logged.setUsername("user1");
		this.logged.setFirstName("Sara");
		this.logged.setPassword("1234");
		this.logged.setDocument(null);
		
		User user = new User("sarapetrovic", "123456789", "Sara", "Petrovic");
		Mockito.when(userRepository.findByUsername("sarapetrovic")).thenReturn(user);
		Mockito.when(userRepository.findByUsername("sarapetrov")).thenReturn(null);
		Mockito.when(userRepository.findByUsername("sara")).thenReturn(null);
		Mockito.when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findById(Long.valueOf(2))).thenThrow(DAOException.class);
		
		Mockito.when(jwtServiceMocked.validate(token.substring(7))).thenReturn(this.credentials);
		Mockito.when(jwtServiceMocked.validate(invalidToken.substring(7))).thenThrow(new TokenValidationException("Invalid token! You don't have permission to upload document!"));
		Mockito.doReturn(this.logged).when(userServiceSpy).findByUsername("user1");
		Mockito.doReturn(this.logged).when(userServiceSpy).save(this.logged);
		Mockito.doReturn(this.logged).when(userServiceSpy).findById(1L);
		Mockito.doThrow(new DAOException("User [id=-1L] cannot be found!")).when(userServiceSpy).findById(-1L);
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
	
	@Transactional
	@Test
	public void saveDocument_PASS_Test() {
		
		boolean ret = false;
		try {
			FileInputStream inputFile = new FileInputStream(imageFolder + "document-test.jpg"); 
			MockMultipartFile mf = new MockMultipartFile("file", "document-test.jpg", "multipart/form-data", inputFile);
			ret = userService.saveDocumentImage(mf, this.token);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(ret);
		Mockito.verify(userServiceSpy, Mockito.times(1)).findByUsername("user1");
		Mockito.verify(userServiceSpy, Mockito.times(1)).save(logged);
	}
	
	@Test
	public void saveDocument_UnauthorizedAttempt_Test() {
		
		try {
			FileInputStream inputFile = new FileInputStream(imageFolder + "document-test.jpg"); 
			MockMultipartFile mf = new MockMultipartFile("file", "document-test.jpg", "multipart/form-data", inputFile);
			userService.saveDocumentImage(mf, this.invalidToken);

		} catch (Exception e) {
			assertTrue(e instanceof TokenValidationException);
		}
	}
	
	@Test
	public void saveDocument_UserDoesntExist_Test() {
		String validTokenInvalidUser = "Bearer ok format ali los user";
		User invalidCredentials = new User();
		invalidCredentials.setUsername("123userNotExist");
		
		Mockito.when(jwtServiceMocked.validate(validTokenInvalidUser)).thenReturn(invalidCredentials);
		Mockito.doThrow(new DAOException("User [username=123userNotExist] doesn't exist!")).when(userServiceSpy).findByUsername("123userNotExist");
		try {
			FileInputStream inputFile = new FileInputStream(imageFolder + "document-test.jpg"); 
			MockMultipartFile mf = new MockMultipartFile("file", "document-test.jpg", "multipart/form-data", inputFile);
			userService.saveDocumentImage(mf, this.invalidToken);

		} catch (Exception e) {
			assertTrue(e instanceof TokenValidationException);
		}
	}
	
	@Test
	public void findUsersByDocumentsVerified_PASS_Test() {
		User u1Approved = new User();
		User u2NoDocument = new User();
		User u3Pending = new User();
		User u4Rejected = new User();
		User u5Pending = new User();
		u1Approved.setDocumentVerified(DocumentVerification.APPROVED);
		u2NoDocument.setDocumentVerified(DocumentVerification.NO_DOCUMENT);
		u3Pending.setDocumentVerified(DocumentVerification.PENDING);
		u4Rejected.setDocumentVerified(DocumentVerification.REJECTED);
		u5Pending.setDocumentVerified(DocumentVerification.PENDING);
		
		List<User> approved = new ArrayList<User>();
		approved.add(u1Approved);
		List<User> noDocument = new ArrayList<User>();
		noDocument.add(u2NoDocument);
		List<User> pending = new ArrayList<User>();
		pending.add(u5Pending);
		pending.add(u3Pending);
		List<User> rejected = new ArrayList<User>();
		rejected.add(u4Rejected);
		
		Mockito.when(userService.findUsersByDocumentVerified(DocumentVerification.APPROVED)).thenReturn(approved);
		Mockito.when(userService.findUsersByDocumentVerified(DocumentVerification.REJECTED)).thenReturn(rejected);
		Mockito.when(userService.findUsersByDocumentVerified(DocumentVerification.NO_DOCUMENT)).thenReturn(noDocument);
		Mockito.when(userService.findUsersByDocumentVerified(DocumentVerification.PENDING)).thenReturn(pending);
		
		List<User> found = new ArrayList<User>();
		found = userService.findUsersByDocumentVerified(DocumentVerification.APPROVED);
		assertEquals(1, found.size());
		assertEquals(DocumentVerification.APPROVED.ordinal(), found.get(0).getDocumentVerified().ordinal());
		
		found = userService.findUsersByDocumentVerified(DocumentVerification.REJECTED);
		assertEquals(1, found.size());
		assertEquals(DocumentVerification.REJECTED.ordinal(), found.get(0).getDocumentVerified().ordinal());
		
		found = userService.findUsersByDocumentVerified(DocumentVerification.NO_DOCUMENT);
		assertEquals(1, found.size());
		assertEquals(DocumentVerification.NO_DOCUMENT.ordinal(), found.get(0).getDocumentVerified().ordinal());
		
		found = userService.findUsersByDocumentVerified(DocumentVerification.PENDING);
		assertEquals(2, found.size());
		assertEquals(DocumentVerification.PENDING.ordinal(), found.get(0).getDocumentVerified().ordinal());
		assertEquals(DocumentVerification.PENDING.ordinal(), found.get(1).getDocumentVerified().ordinal());
	}
	
	@Transactional
	@Test
	public void verifyDocument_PASS_Test() {
		this.logged.setDocument("user_document.jpg");
		this.logged.setDocumentVerified(DocumentVerification.PENDING);
		
		boolean ret = userService.verifyDocument(1L);
		assertTrue(ret);
		Mockito.verify(userServiceSpy, Mockito.times(1)).findById(1L);
	}
	
	@Transactional
	@Test(expected = DocumentVerificationException.class)
	public void verifyDocument_DocumentIsNull_Test() {
		this.logged.setDocument(null);
		this.logged.setDocumentVerified(DocumentVerification.PENDING);
		
		userService.verifyDocument(1L);
		Mockito.verify(userServiceSpy, Mockito.times(1)).findById(1L);
	}
	
	@Transactional
	@Test(expected = DocumentVerificationException.class)
	public void verifyDocument_DocumenVerificationEnumIsZero_Test() {
		this.logged.setDocument("user_document.jpg");
		this.logged.setDocumentVerified(DocumentVerification.NO_DOCUMENT);
		
		userService.verifyDocument(1L);
		Mockito.verify(userServiceSpy, Mockito.times(1)).findById(1L);
	}
	
	@Transactional
	@Test(expected = DAOException.class)
	public void verifyDocument_UserNotFound_Test() {
		userService.verifyDocument(-1L);
		Mockito.verify(userServiceSpy, Mockito.times(1)).findById(1L);
	}
	
	@Transactional
	@Test(expected = DocumentVerificationException.class)
	public void verifyDocument_DocumentAlreadyRejected_Test() {
		this.logged.setDocument("user_document.jpg");
		this.logged.setDocumentVerified(DocumentVerification.REJECTED);
		
		userService.verifyDocument(1L);
		Mockito.verify(userServiceSpy, Mockito.times(1)).findById(1L);
	}
	
	@Transactional
	@Test(expected = DocumentVerificationException.class)
	public void verifyDocument_DocumentAlreadyApproved() {
		this.logged.setDocument("user_document.jpg");
		this.logged.setDocumentVerified(DocumentVerification.APPROVED);
		
		userService.verifyDocument(1L);
		Mockito.verify(userServiceSpy, Mockito.times(1)).findById(1L);
	}
}
