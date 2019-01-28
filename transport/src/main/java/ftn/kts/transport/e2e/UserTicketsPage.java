package ftn.kts.transport.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserTicketsPage {

	private WebDriver driver;
	
	@FindBy(xpath = "//button[contains(text(),'Tickets')]")
	private WebElement ticketsTab;
	
	@FindBy(xpath = "//button[@class='btn btn-primary']")
	private WebElement buttonAcceptTicket;
	
	@FindBy(xpath = "//a[contains(text(),'Sara')]")
	private WebElement profileLink;
	
	
	public UserTicketsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(profileLink));
    }
	
	public WebElement getTicketsTab(){
		return ticketsTab;
	}
	
	public WebElement getProfileLink(){
		return profileLink;
	}
	
	public WebElement getButtonAcceptTicket(){
		return buttonAcceptTicket;
	}
	
	public int getSizeOfTicketCards() {
		return driver.findElements(By.cssSelector("div.tabcontent app-user-tickets:nth-child(2) div:nth-child(1) div:nth-child(1) > div.card")).size();
	}
	
	public boolean isButtonEnabled() {
        try {
            (new WebDriverWait(driver, 4)).until(ExpectedConditions.elementToBeClickable(buttonAcceptTicket));
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
