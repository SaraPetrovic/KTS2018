package ftn.kts.transport.e2e.tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import ftn.kts.transport.e2e.LoginPage;
import ftn.kts.transport.e2e.MainPage;
import ftn.kts.transport.e2e.UploadFilePage;
import ftn.kts.transport.e2e.ZonePage;

public class ZoneTest {

	 private WebDriver browser;
	
	 private MainPage mainPage;
	 private LoginPage loginPage;
	 private ZonePage zonePage;
	
	@Before
	 public void setUpSelenium(){
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sara\\Desktop\\FTN\\KTS\\course_files_export\\Kolokvijum2\\E2Ekolokvijum\\E2Ekolokvijum\\chromedriver.exe");
	
	    this.browser = new ChromeDriver();
	    this.browser.manage().window().maximize();
	    this.browser.navigate().to("http://localhost:4200");
	
	    mainPage = PageFactory.initElements(browser, MainPage.class);
	    loginPage = PageFactory.initElements(browser, LoginPage.class);
	    zonePage = PageFactory.initElements(browser, ZonePage.class);
	 }
	
	@Test
	public void test() throws InterruptedException {
		mainPage.getLoginBtn().click();
		
		loginPage.setUserNameInput("admin");
		loginPage.setPasswordInput("admin");
 
		loginPage.getLoginBtn().click();
		
		Thread.sleep(1000);
		
		zonePage.ensureIsDisplayed();
		
		zonePage.getAdministrationTab().click();
		
		Thread.sleep(1000);
		
		zonePage.getZonesTab().click();
		assertEquals("http://localhost:4200/administration/zones", browser.getCurrentUrl());
		
		Thread.sleep(1000);
		
		int size = zonePage.getSizeOfZones();
		
		zonePage.setName("gradska");		
		
		Thread.sleep(2000);
		
		zonePage.getSaveButton().click();
		
		Thread.sleep(2000);
		
		this.browser.switchTo().alert().accept();
		assertEquals(size, zonePage.getSizeOfZones());
		
		Thread.sleep(2000);
		
		zonePage.setName("Petrovaradin");
		Select select = zonePage.getSelect();
        select.selectByVisibleText("gradska");
        
        Thread.sleep(2000);
        
        zonePage.getSaveButton().click();
        
        Thread.sleep(2000);
        
        assertEquals(size + 1, zonePage.getSizeOfZones());
        
        Thread.sleep(2000);
        
        zonePage.getDeleteButton().click();
        
        assertEquals(size, zonePage.getSizeOfZones());
        
        Thread.sleep(2000);
	}
	

	@After
	 public void closeSelenium() {
       browser.quit();
	 }
	
}
