package ftn.kts.transport.e2e.tests;


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

public class LoginTest {

    private WebDriver browser;

    private MainPage mainPage;
    private LoginPage loginPage;

    @Before
    public void setUpSelenium(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sara\\Desktop\\chromedriver.exe");

        this.browser = new ChromeDriver();
        this.browser.manage().window().maximize();
        this.browser.navigate().to("http://localhost:4200");

        mainPage = PageFactory.initElements(browser, MainPage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
    }

    @Test
    public void test(){
        mainPage.ensureIsDisplayed();
        mainPage.getLoginBtn().click();

        Assert.assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

        loginPage.ensureIsDisplayed();

        Assert.assertFalse(loginPage.isButtonEnabled());


        loginPage.setUserNameInput("userKogNema");
        loginPage.setPasswordInput("njegovaSifra");

        Assert.assertTrue(loginPage.isButtonEnabled());
        loginPage.getLoginBtn().click();

        loginPage.ensureMessageBoxIsDisplayed();
        Assert.assertEquals("Invalid username or password", loginPage.getMessage());


        loginPage.setUserNameInput("");
        //Assert.assertFalse(loginPage.isButtonEnabled());

        loginPage.setUserNameInput("user1");
        loginPage.setPasswordInput("");

        //Assert.assertFalse(loginPage.isButtonEnabled());

        loginPage.setPasswordInput("12345678");
        loginPage.getLoginBtn().click();

        //Assert.assertEquals("http://localhost:4200", browser.getCurrentUrl());
    }

    @After
    public void closeSelenium(){
        browser.quit();
    }
}
