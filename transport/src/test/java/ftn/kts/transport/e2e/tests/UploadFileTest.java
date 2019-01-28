package ftn.kts.transport.e2e.tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.transport.e2e.LoginPage;
import ftn.kts.transport.e2e.MainPage;
import ftn.kts.transport.e2e.UploadFilePage;
import ftn.kts.transport.e2e.UserTicketsPage;

public class UploadFileTest {

 private WebDriver browser;
	 
	 private MainPage mainPage;
	 private LoginPage loginPage;
	 private UploadFilePage uploadPage;
	
	@Before
	 public void setUpSelenium(){
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sara\\Desktop\\FTN\\KTS\\course_files_export\\Kolokvijum2\\E2Ekolokvijum\\E2Ekolokvijum\\chromedriver.exe");
	
	    this.browser = new ChromeDriver();
	    this.browser.manage().window().maximize();
	    this.browser.navigate().to("http://localhost:4200");
	
	    mainPage = PageFactory.initElements(browser, MainPage.class);
	    loginPage = PageFactory.initElements(browser, LoginPage.class);
	    uploadPage = PageFactory.initElements(browser, UploadFilePage.class);
	 }
	
	@Test
	public void test() throws InterruptedException {
		mainPage.getLoginBtn().click();
		
		loginPage.setUserNameInput("Sara");
		loginPage.setPasswordInput("123");
 
		loginPage.getLoginBtn().click();
		
		Thread.sleep(1000);
		
		uploadPage.ensureIsDisplayed();
 
		uploadPage.getProfileLink().click();
		assertEquals("http://localhost:4200/profile", browser.getCurrentUrl());
		
		Thread.sleep(1000);
		
		uploadPage.getUploadTab().click();
		assertEquals("http://localhost:4200/profile/upload", browser.getCurrentUrl());
		
		Thread.sleep(1000);
		
		uploadPage.getChooseFile().click();
		
		Thread.sleep(2000);
	}
	
	@After
	 public void closeSelenium() {
       browser.quit();
	 }
}
