package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.automation.base.TestBase;
import com.automation.pages.DressesPage;
import com.automation.pages.HomePage;
import com.automation.pages.ShoppingCartPage;
import com.automation.util.Constants;

public class ChallengeTest extends TestBase {

	HomePage homePage;
	DressesPage dressesPage;
	ShoppingCartPage shoppingCartPage;
	
	public ChallengeTest(){
		super();
	}
	
	@BeforeTest
	public void initialize(){
		initialization();
		homePage = new HomePage();	
	}
	
	@Test(priority=1, description="Verify Home Page Title")
	public void homePageTitleTest(){
		String homePageTitle = homePage.getHomePageTitle();
		Assert.assertEquals(homePageTitle, Constants.HOME_PAGE_TITLE);
	}
	
	@Test(priority=2, description="Click Dresses Tab and Verify Dresses Page Title")
	public void dressesClickTest(){
		dressesPage = homePage.clickOnDressesTab();
		String dressesPageTitle = dressesPage.getDressesPageTitle();
		Assert.assertEquals(dressesPageTitle, Constants.DRESSES_PAGE_TITLE);
	}
	
	@Test(priority=3, description="Add Dress Items in Dresses Page to the Shopping Cart")
	public void addDressesToCart(){
		dressesPage.addElementsTocart();
	}
	
	@Test(priority=4, description="Go to Shopping Cart Page Verify its Title")
	public void shoppingCartClickTest(){
		shoppingCartPage = dressesPage.clickOnShoppingCartLink();
		String shoppingCartPageTitle = shoppingCartPage.getShoppingCartPageTitle();
		Assert.assertEquals(shoppingCartPageTitle, Constants.SHOPPING_CART_PAGE_TITLE);
	}
	
	@Test(priority=5, description="Verify the Products Total Price against the Sum of each item price in the cart")
	public void cartItemsTotalPriceTest(){
		String calculatedTotal = shoppingCartPage.addIndividualPrices();
		System.out.println("cartItemsTotalPriceTest - calculatedTotal = " + calculatedTotal);
		String productsTotal = shoppingCartPage.getProductsTotal();
		System.out.println("cartItemsTotalPriceTest - productsTotal = " + productsTotal);
		Assert.assertEquals(calculatedTotal, productsTotal);
	}
		
	@AfterTest
	public void tearDown(){
		driver.quit();
	}
}
