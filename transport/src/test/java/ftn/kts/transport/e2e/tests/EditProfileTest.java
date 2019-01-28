package ftn.kts.transport.e2e.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.transport.e2e.LoginPage;
import ftn.kts.transport.e2e.MainPage;
import ftn.kts.transport.e2e.UserEditProfilePage;

public class EditProfileTest {

	 private WebDriver browser;
	 
	 private MainPage mainPage;
	 private LoginPage loginPage;
	 private UserEditProfilePage editProfilePage;
	 
	 @Before
	 public void setUpSelenium(){
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sara\\Desktop\\FTN\\KTS\\course_files_export\\Kolokvijum2\\E2Ekolokvijum\\E2Ekolokvijum\\chromedriver.exe");
	
	    this.browser = new ChromeDriver();
	    this.browser.manage().window().maximize();
	    this.browser.navigate().to("http://localhost:4200");
	
	    mainPage = PageFactory.initElements(browser, MainPage.class);
	    loginPage = PageFactory.initElements(browser, LoginPage.class);
	    editProfilePage = PageFactory.initElements(browser, UserEditProfilePage.class);
	 }
	 
	 @Test
	 public void test() {
		 mainPage.getLoginBtn().click();
		 
		 loginPage.setUserNameInput("Sara");
	     loginPage.setPasswordInput("123");
	     
	     loginPage.getLoginBtn().click();
	     
	     editProfilePage.ensureIsDisplayed();
	     
	     editProfilePage.getProfileLink().click();
	     
	 }
	 
	 @After
	 public void closeSelenium() {
        browser.quit();
	 }
}
