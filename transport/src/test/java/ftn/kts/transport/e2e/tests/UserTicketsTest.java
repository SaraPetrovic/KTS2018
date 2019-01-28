package ftn.kts.transport.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.transport.e2e.LoginPage;
import ftn.kts.transport.e2e.MainPage;
import ftn.kts.transport.e2e.UserTicketsPage;

public class UserTicketsTest {

	 private WebDriver browser;
	 
	 private MainPage mainPage;
	 private LoginPage loginPage;
	 private UserTicketsPage ticketsPage;
	
	@Before
	 public void setUpSelenium(){
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sara\\Desktop\\FTN\\KTS\\course_files_export\\Kolokvijum2\\E2Ekolokvijum\\E2Ekolokvijum\\chromedriver.exe");
	
	    this.browser = new ChromeDriver();
	    this.browser.manage().window().maximize();
	    this.browser.navigate().to("http://localhost:4200");
	
	    mainPage = PageFactory.initElements(browser, MainPage.class);
	    loginPage = PageFactory.initElements(browser, LoginPage.class);
	    ticketsPage = PageFactory.initElements(browser, UserTicketsPage.class);
	 }
	
	@Test
	public void test() throws InterruptedException {
		mainPage.getLoginBtn().click();
		
		loginPage.setUserNameInput("Sara");
		loginPage.setPasswordInput("123");
 
		loginPage.getLoginBtn().click();
		
		ticketsPage.ensureIsDisplayed();
 
		ticketsPage.getProfileLink().click();
		assertEquals("http://localhost:4200/profile", browser.getCurrentUrl());
		
		Thread.sleep(1000);
		
		ticketsPage.getTicketsTab().click();
		assertEquals("http://localhost:4200/profile/tickets", browser.getCurrentUrl());
	    
		Thread.sleep(3000);
		
		if(ticketsPage.isButtonEnabled()) {
			ticketsPage.getButtonAcceptTicket().click();
			
			Thread.sleep(2000);
			
			assertFalse(ticketsPage.isButtonEnabled());
		}
	
		assertFalse(ticketsPage.isButtonEnabled());
	}
	
	 @After
	 public void closeSelenium() {
        browser.quit();
	 }
}
