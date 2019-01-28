package ftn.kts.transport.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

public class ZonePage {
	
private WebDriver driver;
	
	@FindBy(xpath = "//a[contains(text(),'Administration')]")
	private WebElement administrationTab;
	
	@FindBy(xpath = "//a[@routerlink='/administration/zones']")
	private WebElement zonesTab;
	
	@FindBy(xpath = "//span[contains(text(),'Save')]")
	private WebElement saveButton;
	
	@FindBy(xpath = "//span[contains(text(),'Cancel')]")
	private WebElement cancelButton;
	
	@FindBy(xpath = "//tbody//tr[1]//td[3]//div[1]//button[2]//span[2]")
	private WebElement deleteButton;
	
	@FindBy(xpath = "//tbody//tr[1]//td[3]//div[1]//button[1]//span[2]")
	private WebElement editButton;

	@FindBy(xpath = "//input[@placeholder='Name']")
	private WebElement name;
	
	@FindBy(xpath = "//select[@class='form-control ng-untouched ng-pristine ng-valid']")
	private WebElement select;
	
	public ZonePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public Select getSelect() {
        return new Select(select);
    }
	
	public void setSelect(String value) {
        Select el = getSelect();
        el.selectByVisibleText(value);
    }

    public void setName(String value) {
        WebElement el = getName();
        el.clear();
        el.sendKeys(value);
    }
	
	public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(administrationTab));
    }
	
	public int getSizeOfZones() {
		return driver.findElements(By.cssSelector("div:nth-child(1) div.table-responsive:nth-child(2) table.table.table-striped tbody:nth-child(2) > tr:nth-child(1)")).size();
	}

	public WebElement getName() {
		return name;
	}

	public WebElement getAdministrationTab() {
		return administrationTab;
	}

	public WebElement getZonesTab() {
		return zonesTab;
	}

	public WebElement getSaveButton() {
		return saveButton;
	}

	public WebElement getCancelButton() {
		return cancelButton;
	}

	public WebElement getDeleteButton() {
		return deleteButton;
	}

	public WebElement getEditButton() {
		return editButton;
	}

}
