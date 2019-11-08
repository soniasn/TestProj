package com.automation.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.base.TestBase;

public class DressesPage extends TestBase {

	@FindAll(@FindBy(xpath = "//div[@id='center_column']/ul/li"))
	List<WebElement> dressesList;

	@FindBy(xpath = "//a[@title='View my shopping cart']")
	WebElement shoppingCartLink;

	JavascriptExecutor js;
	Actions actions;

	public DressesPage() {
		// Initializing the DressesPage Object:
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		actions = new Actions(driver);
	}

	public String getDressesPageTitle() {
		return driver.getTitle();
	}
	
	public List<WebElement> getDressesList() {
		waitForDressesVisibility();
		return dressesList;
	}

	
	public void addElementsTocart() {
		waitForDressesVisibility();
		
		// Iterate through the Dresses list
		if (dressesList != null && dressesList.size() > 0) {
			System.out.println("DressesPage - Length of dressesList = " + dressesList.size());
			System.out.println("-----------------------------------------------------");
			for (int i = 0; i < dressesList.size(); i++) {
				int itemNum = i + 1;
				
				// Retrieve each Dress Item from the Dresses List
				WebElement dressElement = (WebElement) dressesList.get(i);
				scrollToElement(dressElement);
				pauseForTimeInSeconds(2);
				
				// Mouse hover on Dress item
				actions.moveToElement(dressElement).perform();
				System.out.println("Done Mouse hover on Dress Item# " + itemNum);
				
				// Get the Add Cart Link for the Dress Item
				WebElement dressAddCartLink = getAddCartLinkForIndex(itemNum);
				pauseForTimeInSeconds(3);
				dressAddCartLink.click();			
				System.out.println("Clicked 'Add to Cart' for Dress Item# " + itemNum);
				pauseForTimeInSeconds(3);
				
				// close the pop-up
				closePopUpWindow();
				System.out.println("Closed the pop-up for Dress Item# " + itemNum);
				pauseForTimeInSeconds(5);
				System.out.println("-----------------------------------------------------");
			}
		}
	}

	public WebElement getAddCartLinkForIndex(int index) {
		WebElement dressAddCartLink = driver
				.findElement(By.xpath("//div[@id='center_column']/ul/li[" + index + "]//a[@title='Add to cart']"));
		return dressAddCartLink;
	}
	
	public void closePopUpWindow() {
		WebElement popUpClose = driver.findElement(By.xpath("//span[@title='Close window']"));
		pauseForTimeInSeconds(3);
		popUpClose.click();
	}

	public ShoppingCartPage clickOnShoppingCartLink() {
		scrollToElement(shoppingCartLink);
		shoppingCartLink.click();
		return new ShoppingCartPage();
	}

	public void pauseForTimeInSeconds(int pauseTime) {
		driver.manage().timeouts().implicitlyWait(pauseTime, TimeUnit.SECONDS);
	}
	
	public void scrollToElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView();", element);
	}
	
	public void waitForDressesVisibility() {
		WebDriverWait driverWait = new WebDriverWait(driver, 120);
		driverWait.until(ExpectedConditions.visibilityOfAllElements(dressesList));
	}
}
