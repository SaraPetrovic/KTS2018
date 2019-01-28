package ftn.kts.transport.e2e.tests;

import ftn.kts.transport.e2e.ConductorPage;
import ftn.kts.transport.e2e.LinesPage;
import ftn.kts.transport.e2e.LoginPage;
import ftn.kts.transport.e2e.MainPage;
import org.h2.store.Page;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

public class LinesTest {

    private WebDriver browser;

    private MainPage mainPage;

    private LinesPage linesPage;

    @Before
    public void setUpSelenium(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Balenko\\Desktop\\chromedriver.exe");

        this.browser = new ChromeDriver();
        this.browser.manage().window().maximize();
        this.browser.navigate().to("http://localhost:4200");

        mainPage = PageFactory.initElements(browser, MainPage.class);
        linesPage = PageFactory.initElements(browser, LinesPage.class);
    }

    @Test
    public void test(){
        this.mainPage.getLinesBtn().click();

        Assert.assertEquals("http://localhost:4200/lines", browser.getCurrentUrl());

        this.linesPage.ensureIsDisplayed();

        this.linesPage.getFirstLine().click();

        Assert.assertEquals(18,this.linesPage.getSelectedPaths().size());
    }


    @After
    public void closeSelenium() throws InterruptedException
    {
        Thread.sleep(5000);
        browser.quit();
    }
}
