package ftn.kts.transport.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ftn.kts.transport.e2e.LoginPage;
import ftn.kts.transport.e2e.MainPage;
import ftn.kts.transport.e2e.RegistrationPage;

public class RegistrationTest {

	private WebDriver browser;
	
	private MainPage mainPage;
	private RegistrationPage registrationPage;
	private LoginPage loginPage;
	
	@Before
    public void setUpSelenium(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\jolee\\Desktop\\chromedriver.exe");

        this.browser = new ChromeDriver();
        this.browser.manage().window().maximize();
        this.browser.navigate().to("http://localhost:4200");

        mainPage = PageFactory.initElements(browser, MainPage.class);
        registrationPage = PageFactory.initElements(browser, RegistrationPage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
	}
	
	@Test
	public void loginTest() {
		mainPage.ensureIsDisplayed();
		mainPage.getLoginBtn().click();
		
        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
        loginPage.ensureIsDisplayed();
        
        assertTrue(loginPage.isRegisterButtonEnabled());
        
        loginPage.getRegisterBtn().click();
        
        assertEquals("http://localhost:4200/registration", browser.getCurrentUrl());
        registrationPage.ensureIsDisplayed();
        
        registrationPage.setUsernameInput("a");
        registrationPage.setPasswordInput("123456789");
        registrationPage.setPasswordRepeatInput("123456789");
        registrationPage.setFirstNameInput("Milica");
        registrationPage.setLastNameInput("Milicic");
        registrationPage.setEmailInput("milica@gmail.com");
        
        assertTrue(registrationPage.isButtonEnabled());
        registrationPage.getRegisterBtn().click();
        assertEquals("http://localhost:4200/registration", browser.getCurrentUrl());

        
        registrationPage.setUsernameInput("Korisnik Novi");
        registrationPage.setPasswordInput("123456789");
        registrationPage.setPasswordRepeatInput("123456789");
        registrationPage.setFirstNameInput("Milica");
        registrationPage.setLastNameInput("Milicic");
        registrationPage.setEmailInput("milica@gmail.com");
        
        assertTrue(registrationPage.isButtonEnabled());
        registrationPage.getRegisterBtn().click();
        
        try {
        	Thread.sleep(5000);
		} catch (Exception e) {
		}
        
        assertEquals("http://localhost:4200/", browser.getCurrentUrl());
        

	}
	
    @After
    public void closeBrowser(){
        browser.quit();
    }
}
