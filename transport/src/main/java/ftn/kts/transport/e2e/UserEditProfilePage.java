package ftn.kts.transport.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

	
	public UserEditProfilePage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getFirstName() {
		return firstName;
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
	
	
	
}
