package com.softserve.edu.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.softserve.edu.opencart.data.Currencies;
import com.softserve.edu.opencart.data.IUser;
import com.softserve.edu.opencart.data.ProductRepository;
import com.softserve.edu.opencart.data.UserRepository;
import com.softserve.edu.opencart.pages.AccountLogoutPage;
import com.softserve.edu.opencart.pages.HomeMessagePage;
import com.softserve.edu.opencart.pages.HomePage;
import com.softserve.edu.opencart.pages.MyAccountPage;
import com.softserve.edu.opencart.pages.cart.functional.EmptyShoppingCartPage;
import com.softserve.edu.opencart.pages.cart.functional.ShoppingCartMessagePage;
import com.softserve.edu.opencart.pages.cart.functional.ShoppingCartPage;
import com.softserve.edu.opencart.tools.TestRunner;

public class YStasivTest extends TestRunner {
	

	
//=====================================================================================================
//											SmokeTestOpenCart
//=====================================================================================================
	//@Test(enabled = true)
	public void smokeTestOpenCart() {
	log.info("SmokeTestOpenCart start");
		SoftAssert softAssert = new SoftAssert();
//Precondition: Load Application
		HomePage homePage = loadApplication();
		delayExecution(1000); //ForDemonstration
		
//Steps: Add product to cart
		homePage.getProductsListComponentTitleText();
		delayExecution(1000); //ForDemonstration
		
//Check if page contains curent data
		softAssert.assertEquals(homePage.getProductsListComponentTitleText(),homePage.EXPECT_PRODUCT_LIST_TITLE);
	log.info("Element on main page was found...");
		delayExecution(1000); //ForDemonstration
		softAssert.assertEquals(homePage.getCartSum(), 0.0); softAssert.assertEquals(homePage.getCartAmount(), 0);
	log.info("Current data on page was found...");
		delayExecution(1000); //ForDemonstration
		Assert.assertTrue(homePage.gotoLogin().getLastBreadcrumbText().contains("Login"));	
	log.info("Element on enother page was found...");
		delayExecution(2000); //ForDemonstration
		softAssert.assertAll();
	}//TODO  + репортер?
//=====================================================================================================
//											CheckEmptyCartPage
//=====================================================================================================
	
	@DataProvider // (parallel = true)
	public Object[][] currenciesType() {
		// Read from ...
		return new Object[][] { 
			{ Currencies.US_DOLLAR }, 
//			{ Currencies.EURO }, 
//			{ Currencies.POUND_STERLING }, 
			};
	}

	//@Test(dataProvider = "currenciesType", enabled = true)
//CheckEmptyCartPageWithDifferentCurrencies
	public void checkEmptyCartPage(Currencies currency) {
	log.info("CheckEmptyCartPage start");
//Precondition: Load Application
		HomePage homePage = loadApplication();
		delayExecution(1000); //ForDemonstration
		
//Steps: Choose currencies and go to Shopping Cart
		homePage = homePage.chooseCurrency(currency);
	log.info("US_DOLLAR was chosen...");
		delayExecution(1000); //ForDemonstration
		
//Check if user see Empty Cart Page
		EmptyShoppingCartPage emptyShoppingCartPage = homePage.gotoEmptyShoppingCartPage();
		Assert.assertEquals(emptyShoppingCartPage.getEmptyCartText(), emptyShoppingCartPage.EXPECT_EMPTY_CART_TEXT);
	log.info("Expected message about cart is empty was displayed...");
		delayExecution(2000); //ForDemonstration
//TODO ПОМІНЯТИ НАЗАД НА ДОЛАР
	}//TODO репортер?
//=====================================================================================================
	
	
		@DataProvider//(parallel = true)
	    public Object[][] productNames() {
	        // Read from ...
	        return new Object[][] { 
	            { ProductRepository.macBook().getName()},
//	            { "iPhone" },
	            };
	    }

	//@Test(dataProvider = "productNames", enabled = true, groups = {"addItemToCart"})
	public void addItemToCart(String partialProductName) {
	log.info("AddItemToCart start with test item \"" + partialProductName + "\"");
//Precondition: Load Application
        HomePage homePage = loadApplication();
        delayExecution(1000); //ForDemonstration
        
//Steps: Add product to cart
        HomeMessagePage homeMessagePage = homePage.putToCartProductByPartialName(partialProductName);
    log.info("\"" + partialProductName + "\" was added to cart...");
        delayExecution(1000); //ForDemonstration
        
//Check if AlertMessage contains current text
        Assert.assertTrue(homeMessagePage.getAlertMessageText()
        		.contains(String.format(homeMessagePage.EXPECTED_MESSAGE_CART, partialProductName)));
    log.info("User see correct message, \"" + partialProductName + "\" was added...");
        delayExecution(1000); //ForDemonstration
        
//Go to ShoppingCart and check if goods was added
        ShoppingCartPage shoppingCartPage = homePage.gotoShoppinCartPage(); 
        Assert.assertTrue(shoppingCartPage.getProductsCartListComponent().getProductsCartNameList().contains(partialProductName));
    log.info("\"" + partialProductName + "\" displayed correctly on cart page...");
        delayExecution(2000); //ForDemonstration
    }//TODO  репортер?
	
	
	
	//@Test(dataProvider = "productNames", enabled = true)
	public void changeNumOfItemsInCart(String partialProductName) {
	log.info("ChangeNumOfItemsInCart start with test item \"" + partialProductName + "\"");
//Precondition: Load Application
        HomePage homePage = loadApplication();
        delayExecution(1000); //ForDemonstration
//Steps: Add product to cart
        HomeMessagePage homeMessagePage = homePage.putToCartProductByPartialName(partialProductName);
    log.info("\"" + partialProductName + "\" was added to cart...");
        delayExecution(1000); //ForDemonstration
//Check if AlertMessage contains current text
        Assert.assertTrue(homeMessagePage.getAlertMessageText()
        		.contains(String.format(homeMessagePage.EXPECTED_MESSAGE_CART, partialProductName)));
    log.info("User see correct message, \"" + partialProductName + "\" was added...");
//GoToShoppingCart
        ShoppingCartPage shoppingCartPage = homePage.gotoShoppinCartPage();
//Check if goods was added
        Assert.assertTrue(shoppingCartPage.getProductsCartListComponent().getProductsCartNameList().contains(partialProductName));
    log.info("\"" + partialProductName + "\" displayed correctly on cart page...");
        delayExecution(1000); //ForDemonstration
//Set quantity and Update product
        ShoppingCartMessagePage cartMessagePage = shoppingCartPage.updateProductQuantityByPartialName(partialProductName, "5");
    log.info("Count changed correctly...");
    	delayExecution(1000); //ForDemonstration
//Check if AlertMessage contains current text
        Assert.assertEquals(cartMessagePage.getAlertMessageText(), cartMessagePage.EXPECTED_UPDATE_MESSAGE_CART);
    log.info("User see correct message...");
        delayExecution(5000); //ForDemonstration
	}
	
	//@Test(dataProvider = "productNames", enabled = true)
	public void ErrorMessageChangeNumOfItemsInCart(String partialProductName) {
//Precondition: Load Application
        HomePage homePage = loadApplication();
        delayExecution(1000); //ForDemonstration
//Steps: Add product to cart
        HomeMessagePage homeMessagePage = homePage.putToCartProductByPartialName(partialProductName);
        delayExecution(1000); //ForDemonstration
//Check if AlertMessage contains current text
        Assert.assertTrue(homeMessagePage.getAlertMessageText()
        		.contains(String.format(homeMessagePage.EXPECTED_MESSAGE_CART, partialProductName)));
//GoToShoppingCart
        ShoppingCartPage shoppingCartPage = homePage.gotoShoppinCartPage();
//Check if goods was added
        Assert.assertTrue(shoppingCartPage.getProductsCartListComponent().getProductsCartNameList().contains(partialProductName));
        delayExecution(1000); //ForDemonstration
//Set quantity and Update product
        ShoppingCartMessagePage cartMessagePage = shoppingCartPage.updateProductQuantityByPartialName(partialProductName, "test");
//Check if AlertMessage contains current text
        Assert.assertEquals(cartMessagePage.getAlertMessageText(), cartMessagePage.EXPECTED_UPDATE_MESSAGE_CART);
        delayExecution(5000); //ForDemonstration
        
	}
	
	@DataProvider // (parallel = true)
	public Object[][] SomeProduct() {
		// Read from ...
		return new Object[][] { 
			{ UserRepository.get().yStasiv(), "MacBook" },
			};
	}
	//@Test(dataProvider = "SomeProduct", enabled = true)
	public void CartAfterRelogin(IUser validUser, String partialProductName) {
//Precondition: Login
		MyAccountPage myAccountPage = loadApplication().gotoLogin().successLogin(validUser);
		delayExecution(1000);
	
//back to main page
		HomePage homePage = myAccountPage.gotoHome();
		delayExecution(1000);
		
//Add product to cart 
		HomeMessagePage homeMessagePage = homePage.putToCartProductByPartialName(partialProductName);
        delayExecution(1000); //ForDemonstration

//Check if AlertMessage contains current text
        Assert.assertTrue(homeMessagePage.getAlertMessageText()
        		.contains(String.format(homeMessagePage.EXPECTED_MESSAGE_CART, partialProductName)));
        delayExecution(1000); //ForDemonstration
        
//Close message
      	homePage = homeMessagePage.closeAlertMessage();
      	delayExecution(1000);

//Go to ShoppingCart and check if goods was added
        ShoppingCartPage shoppingCartPage = homePage.gotoShoppinCartPage(); 
        Assert.assertTrue(shoppingCartPage.getProductsCartListComponent().getProductsCartNameList().contains(partialProductName));
        delayExecution(2000); //ForDemonstration

		
//Return to previous state
//Remove ProductName from cart List
        shoppingCartPage.removeProductQuantityByPartialName(partialProductName);
        shoppingCartPage.gotoEmptyShoppingCartPage().clickContinueButton();
		delayExecution(1000);

//Logout		
		AccountLogoutPage accountLogoutPage = myAccountPage.gotoLogout();
		accountLogoutPage.gotoHome();
		delayExecution(3000);
		

	}
	
	
}
