package ftn.kts.transport.e2e.tests;

import ftn.kts.transport.e2e.AdminPage;
import ftn.kts.transport.e2e.ConductorPage;
import ftn.kts.transport.e2e.LoginPage;
import ftn.kts.transport.e2e.MainPage;
import org.h2.store.Page;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class AdministrationLineTest {


    private WebDriver browser;

    private MainPage mainPage;
    private LoginPage loginPage;
    private AdminPage adminPage;

    @Before
    public void setUpSelenium(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Balenko\\Desktop\\chromedriver.exe");

        this.browser = new ChromeDriver();
        this.browser.manage().window().maximize();
        this.browser.navigate().to("http://localhost:4200");

        mainPage = PageFactory.initElements(browser, MainPage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        adminPage = PageFactory.initElements(browser, AdminPage.class);
    }

    @Test
    public void test(){
        this.mainPage.getLoginBtn().click();
        this.loginPage.ensureIsDisplayed();
        this.loginPage.setUserNameInput("admin");
        this.loginPage.setPasswordInput("admin");
        this.loginPage.getLoginBtn().click();

        this.mainPage.ensureAdminBtnIsDisplayed();
        this.mainPage.getAdministrationBtn().click();

        this.adminPage.ensureIsDisplayed();

        Assert.assertEquals("http://localhost:4200/administration/lines", browser.getCurrentUrl());

        this.adminPage.ensureIsDisplayed();
        this.adminPage.ensureFormIsDisplayed();

        this.adminPage.setTransportTypeInput(1);
        this.adminPage.setLineNameInput("Nova Linija");
        this.adminPage.setLineDescriptionInput("Njen opis");

        this.adminPage.getAddButton().click();
    }


    @After
    public void closeSelenium() throws InterruptedException
    {
        browser.quit();
    }
}
