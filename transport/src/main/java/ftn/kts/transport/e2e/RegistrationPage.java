package ftn.kts.transport.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {

	private WebDriver driver;
	
	@FindBy(css = "input[formcontrolname='username']")
	private WebElement usernameInput;
	@FindBy(css = "input[formcontrolname='password']")
	private WebElement passwordInput;
	@FindBy(css = "input[formcontrolname='confirmPassword']")
	private WebElement passwordRepeatInput;
	@FindBy(css = "input[formcontrolname='firstName']")
	private WebElement firstNameInput;
	@FindBy(css = "input[formcontrolname='lastName']")
	private WebElement lastNameInput;
	@FindBy(css = "input[formcontrolname='email']")
	private WebElement emailInput;
	@FindBy(xpath = "//*[@id=\"wrapper\"]/form/div[7]/button")
	private WebElement registerBtn;
	
	public RegistrationPage(WebDriver webDriver) {
		this.driver = webDriver;
	}
	
    public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(registerBtn));
    }

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getUsernameInput() {
		return usernameInput;
	}

	public void setUsernameInput(String value) {
		WebElement el = getUsernameInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getPasswordInput() {
		return passwordInput;
	}

	public void setPasswordInput(String value) {
		WebElement el = getPasswordInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getPasswordRepeatInput() {
		return passwordRepeatInput;
	}

	public void setPasswordRepeatInput(String value) {
		WebElement el = getPasswordRepeatInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getFirstNameInput() {
		return firstNameInput;
	}

	public void setFirstNameInput(String value) {
		WebElement el = getFirstNameInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getLastNameInput() {
		return lastNameInput;
	}

	public void setLastNameInput(String value) {
		WebElement el = getLastNameInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getEmailInput() {
		return emailInput;
	}

	public void setEmailInput(String value) {
		WebElement el = getEmailInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getRegisterBtn() {
		return registerBtn;
	}

    public boolean isButtonEnabled() {
        try {
            (new WebDriverWait(driver, 3)).until(ExpectedConditions.elementToBeClickable(registerBtn));
            return true;
        }catch(Exception e){
            return false;
        }
    }

    
    

	
}
