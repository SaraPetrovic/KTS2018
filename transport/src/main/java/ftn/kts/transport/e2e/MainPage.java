package ftn.kts.transport.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private WebDriver driver;

    @FindBy(css = "#header-login-btn")
    private WebElement loginBtn;

    @FindBy(css = "#header-lines-btn")
    private WebElement linesBtn;

    @FindBy(css = "#header-administration-btn")
    private WebElement administrationBtn;

    public MainPage(WebDriver driver){this.driver = driver;}

    public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(loginBtn));
    }

    public void ensureAdminBtnIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(administrationBtn));
    }

    public WebElement getAdministrationBtn() {
        return administrationBtn;
    }

    public WebElement getLinesBtn() {
        return linesBtn;
    }

    public WebElement getLoginBtn() {
        return loginBtn;
    }
}
