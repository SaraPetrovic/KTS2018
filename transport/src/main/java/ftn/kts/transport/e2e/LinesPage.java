package ftn.kts.transport.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LinesPage {

    private WebDriver driver;

    @FindBy(css = "#novi-sad-map")
    private WebElement map;

    @FindBy(css = ".mat-card:nth-child(1)")
    private WebElement firstLine;


    public LinesPage(WebDriver driver){this.driver = driver;}

    public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(map));
    }

    public List<WebElement> getSelectedPaths() {
        return this.driver.findElements(By.cssSelector(".street[style='fill: red;']"));
    }

    public WebElement getFirstLine() {
        return firstLine;
    }
}
