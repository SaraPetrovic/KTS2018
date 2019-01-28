package ftn.kts.transport.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;

    @FindBy(css = ".login-btn")
    private WebElement loginBtn;

    @FindBy(css = "input[formcontrolname='username']")
    private WebElement userNameInput;

    @FindBy(css = "input[formcontrolname='password']")
    private WebElement passwordInput;

    @FindBy(css = "#error-message")
    private WebElement messageBox;

    public LoginPage(WebDriver driver){this.driver = driver;}

    public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(loginBtn));
    }

    public void ensureMessageBoxIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(messageBox));
    }

    public String getMessage(){
        return this.messageBox.getText();
    }

    public WebElement getMessageBox() {
        return messageBox;
    }

    public WebElement getLoginBtn() {
        return loginBtn;
    }

    public WebElement getUserNameInput() {
        return userNameInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public void setUserNameInput(String value) {
        WebElement el = getUserNameInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setPasswordInput(String value){
        WebElement el = getPasswordInput();
        el.clear();
        el.sendKeys(value);
    }

    public boolean isButtonEnabled() {
        try {
            (new WebDriverWait(driver, 3)).until(ExpectedConditions.elementToBeClickable(loginBtn));
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
