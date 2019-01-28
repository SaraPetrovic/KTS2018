package ftn.kts.transport.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UploadFilePage {

	private WebDriver driver;
	
	@FindBy(xpath = "//button[contains(text(),'Upload file')]")
	private WebElement uploadTab;
	
	@FindBy(xpath = "//input[@type='file']")
	private WebElement chooseFile;
	
	@FindBy(xpath = "//button[@type='button']")
	private WebElement buttonUpload;
	
	@FindBy(xpath = "//a[contains(text(),'Sara')]")
	private WebElement profileLink;
	
	
	public UploadFilePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(profileLink));
    }

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getUploadTab() {
		return uploadTab;
	}

	public WebElement getChooseFile() {
		return chooseFile;
	}

	public WebElement getButtonUpload() {
		return buttonUpload;
	}

	public WebElement getProfileLink() {
		return profileLink;
	}
	
	
}
