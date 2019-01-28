package ftn.kts.transport.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConductorPage {

    private WebDriver driver;

    @FindBy(css = "#conductor-btn")
    private WebElement conductorButton;

    @FindBy(css = "#check-in-btn")
    private WebElement checkInButton;

    @FindBy(css = "input[formcontrolname='vehicleNumber']")
    private WebElement checkInInput;

    @FindBy(css = "video")
    private WebElement qrcode;

    public ConductorPage(WebDriver driver){this.driver = driver;}

    public void ensureButtonIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(conductorButton));
    }

    public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(checkInButton));
    }

    public void ensureQrCodeIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(qrcode));
    }

    public WebElement getConductorButton() {
        return conductorButton;
    }

    public WebElement getCheckInButton() {
        return checkInButton;
    }

    public void setCheckInInput(String value){
        WebElement el = getCheckInInput();
        el.clear();
        el.sendKeys(value);
    }


    public WebElement getCheckInInput() {
        return checkInInput;
    }
}
