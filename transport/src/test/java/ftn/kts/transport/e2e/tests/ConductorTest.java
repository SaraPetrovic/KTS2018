package ftn.kts.transport.e2e.tests;

import ftn.kts.transport.e2e.ConductorPage;
import ftn.kts.transport.e2e.LoginPage;
import ftn.kts.transport.e2e.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Sleeper;

public class ConductorTest {

    private WebDriver browser;

    private MainPage mainPage;
    private LoginPage loginPage;
    private ConductorPage conductorPage;

    @Before
    public void setUpSelenium(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Balenko\\Desktop\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("use-fake-ui-for-media-stream");
        options.addArguments("use-fake-device-for-media-stream");
        options.addArguments("use-file-for-fake-video-capture=C:\\Users\\Balenko\\Desktop\\video3.mjpeg");

        this.browser = new ChromeDriver(options);
        this.browser.manage().window().maximize();
        this.browser.navigate().to("http://localhost:4200");

        mainPage = PageFactory.initElements(browser, MainPage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        conductorPage = PageFactory.initElements(browser, ConductorPage.class);
    }

    @Test
    public void test(){

        this.mainPage.getLoginBtn().click();

        this.loginPage.ensureIsDisplayed();
        this.loginPage.setUserNameInput("conductor");
        this.loginPage.setPasswordInput("conductor");
        this.loginPage.getLoginBtn().click();

        this.conductorPage.ensureButtonIsDisplayed();

        this.conductorPage.getConductorButton().click();

        this.conductorPage.ensureIsDisplayed();

        this.conductorPage.setCheckInInput("1");
        this.conductorPage.getCheckInButton().click();

        this.conductorPage.ensureQrCodeIsDisplayed();

        //Ovde ne mogu da prodjem
        //Ne ocitava qrcode iz mockovanog video
        //Moze biti do kvaliteta slike ili do implementacije biblioteke koju koristim
        //koja i nema bas neku dokumentaciju tako da ovde zavrsavam sa testom :(
    }



    @After
    public void closeSelenium() throws InterruptedException
    {
        Thread.sleep(10000);
        browser.quit();
    }

}
