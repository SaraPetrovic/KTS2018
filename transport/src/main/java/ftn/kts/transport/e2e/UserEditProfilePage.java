package ftn.kts.transport.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserEditProfilePage {
	
	private WebDriver driver;
	
	@FindBy(xpath = "//input[@placeholder='First Name']")
	private WebElement firstName;
	
	@FindBy(xpath = "//input[@placeholder='Last Name']")
	private WebElement lastName;
	
	@FindBy(xpath = "//input[@placeholder='Username']")
	private WebElement username;
	
	@FindBy(xpath = "//input[@placeholder='Password']")
	private WebElement pass;
	
	@FindBy(xpath = "//input[@placeholder='Repeated Password']")
	private WebElement repeatedPass;
	
	@FindBy(xpath = "//span[contains(text(),'Save')]")
	private WebElement saveButton;

	@FindBy(xpath = "//a[contains(text(),'Sara')]")
	private WebElement profileLink;
	
	@FindBy(xpath = "//button[@id='defaultOpen']")
	private WebElement changeProfileTab;
	
	@FindBy(xpath = "//label[contains(text(),'Repeated password is wrong')]")
	private WebElement repeatedPassWrong;
	
	@FindBy(xpath = "//div[contains(text(),'Password must be at least 8 characters long.')]")
	private WebElement passwordLengthMessage;
	
	@FindBy(xpath = "//div[@class='container']//div[6]//div[1]//div[1]//div[1]//div[1]")
	private WebElement repeatedPassLengthMessage;
	
	public UserEditProfilePage(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(profileLink));
    }
	
	public WebElement getPasswordLengthMessage(){
		return passwordLengthMessage;
	}
	
	public WebElement getRepeatedPassLengthMessage(){
		return repeatedPassLengthMessage;
	}
	
	public WebElement getFirstName() {
		return firstName;
	}

	public WebElement getRepeatedPassWrong() {
		return repeatedPassWrong;
	}
	
	public WebElement getChangeProfileTab() {
		return changeProfileTab;
	}
	
	public WebElement getProfileLink() {
		return profileLink;
	}
	
	public void setFirstName(String value) {
		WebElement el = getFirstName();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getLastName() {
		return lastName;
	}

	public void setLastName(String value) {
		WebElement el = getLastName();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getUsername() {
		return username;
	}

	public void setUsername(String value) {
		WebElement el = getUsername();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getPass() {
		return pass;
	}

	public void setPass(String value) {
		WebElement el = getPass();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getRepeatedPass() {
		return repeatedPass;
	}

	public void setRepeatedPass(String value) {
		WebElement el = getRepeatedPass();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getSaveButton() {
		return saveButton;
	}
	
	public boolean isButtonEnabled() {
        try {
            (new WebDriverWait(driver, 4)).until(ExpectedConditions.elementToBeClickable(saveButton));
            return true;
        }catch(Exception e){
            return false;
        }
    }
	
	
}
