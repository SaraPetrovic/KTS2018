package ftn.kts.transport.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPage {
    private WebDriver driver;

    @FindBy(css = "#administration-lines-btn")
    private WebElement linesBtn;

    @FindBy(css = "select[formcontrolname='lineTransportType']")
    private WebElement transportTypeInput;

    @FindBy(css = "input[formcontrolname='lineName']")
    private WebElement lineNameInput;

    @FindBy(css = "input[formcontrolname='lineDescription']")
    private WebElement lineDescriptionInput;

    @FindBy(css = "#add-btn")
    private WebElement addButton;

    public AdminPage(WebDriver driver){this.driver = driver;}

    public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(linesBtn));
    }

    public void ensureFormIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(transportTypeInput));
    }

    public WebElement getTransportTypeInput() {
        return transportTypeInput;
    }

    public WebElement getLineNameInput() {
        return lineNameInput;
    }

    public WebElement getLineDescriptionInput() {
        return lineDescriptionInput;
    }

    public WebElement getAddButton() {
        return addButton;
    }

    public void setTransportTypeInput(int value) {
        Select el = new Select(getTransportTypeInput());
        el.selectByIndex(value);
    }

    public void setLineNameInput(String value) {
        WebElement el = getLineNameInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setLineDescriptionInput(String value) {
        WebElement el = getLineDescriptionInput();
        el.clear();
        el.sendKeys(value);
    }
}
