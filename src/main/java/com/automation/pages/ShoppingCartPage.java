package com.automation.pages;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.base.TestBase;

public class ShoppingCartPage extends TestBase {

	@FindAll(@FindBy(xpath = "//table[@id='cart_summary']/tbody//td[@data-title='Total']/span"))
	List<WebElement> cartItemsList;

	@FindBy(id = "total_product")
	WebElement itemsTotal;
	
	JavascriptExecutor js;

	public ShoppingCartPage() {
		// Initializing the ShoppingCartPage Object:
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
	}
	
	public String getShoppingCartPageTitle(){
		return driver.getTitle();
	}
	
	public List<WebElement> getcartItemsList() {
		waitForDressesVisibility();
		return cartItemsList;
	}
	
	public String addIndividualPrices() {
		waitForDressesVisibility();
		BigDecimal totalAmount = BigDecimal.valueOf(0.00);
		if(cartItemsList != null) {
			System.out.println("ShoppingCartPage - Length of cartItemsList = " + cartItemsList.size());
			System.out.println("-----------------------------------------------------");
			//Iterate through cart items list
			int count = 1;
			for (Iterator<WebElement> iterator = cartItemsList.iterator(); iterator.hasNext();) {
				WebElement cartItem = (WebElement) iterator.next();
				scrollToElement(cartItem);
				String dollarAmount = cartItem.getText();
				System.out.println("ShoppingCartPage - Dollar Amount of item# " + count + " is : " + dollarAmount);
				BigDecimal convertedAmount = convertDollarStringAmount(dollarAmount.trim());
				System.out.println("ShoppingCartPage - Converted  Dollar Amount is : $" + convertedAmount);
				totalAmount = totalAmount.add(convertedAmount);
				System.out.println("ShoppingCartPage - Total Amount so far is : $" + totalAmount);
				System.out.println("-----------------------------------------------------");
				count ++;
			}
		}
		return "$"+totalAmount.doubleValue();
	}
	
	public String getProductsTotal(){
		return itemsTotal == null ? "$0" : itemsTotal.getText() ;
	}
	
	public BigDecimal convertDollarStringAmount(String dollarAmount) {
		if (dollarAmount == null) 
			return BigDecimal.valueOf(0.00);
		if (dollarAmount.length() <= 1)
			return BigDecimal.valueOf(0.00);
		
		return BigDecimal.valueOf(Double.parseDouble(dollarAmount.substring(1).trim()));
	}

	
	public void scrollToElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView();", element);
	}
	
	public void waitForDressesVisibility() {
		WebDriverWait driverWait = new WebDriverWait(driver, 120);
		driverWait.until(ExpectedConditions.visibilityOfAllElements(cartItemsList));
	}
}
