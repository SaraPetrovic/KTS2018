package ftn.kts.transport.e2e.tests;


import ftn.kts.transport.e2e.MainPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

    private WebDriver browser;

    private MainPage mainPage;

    @Before
    public void setUpSelenium(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sara\\Desktop\\chromedriver.exe");

        this.browser = new ChromeDriver();
        this.browser.manage().window().maximize();
        this.browser.navigate().to("http://localhost:4200");
    }

    @Test
    public void test(){}
}
