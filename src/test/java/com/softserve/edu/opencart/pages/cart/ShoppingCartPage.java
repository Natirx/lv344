package com.softserve.edu.opencart.pages.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.softserve.edu.opencart.pages.AStatusBarComponent;
import com.softserve.edu.opencart.tools.RegexUtils;

public class ShoppingCartPage extends AStatusBarComponent {

	private ProductsListCartComponent productsCartListComponent;
	private WebElement totalPrice;
	private WebElement checkoutButton;

	public ShoppingCartPage(WebDriver driver) {
		super(driver);
		initEmptyCartPageText();
	}

	//TODO УТОЧНИТИ ЯК ПРАВИЛЬНО ШУКАТИ ЕЛЕМЕНТИ
	private void initEmptyCartPageText() {
		productsCartListComponent = new ProductsListCartComponent(driver);
		totalPrice = driver.findElement(By.xpath("//td/strong[text()='Total:']/../../td[contains(text() , '')]"));
		checkoutButton = driver.findElement(By.cssSelector("a.btn.btn-primary"));
	}

	//AtomicOperation
	public ProductsListCartComponent getProductsCartListComponent() {return productsCartListComponent;}
	
	//TotalPrice
	public WebElement getTotalPrice() {return totalPrice;}
	public double getTotalPriceText() {return RegexUtils.extractFirstDouble(getTotalPrice().getText());}

	//CheckoutButton
	public WebElement getCheckoutButton() {return checkoutButton;}
	public String getCheckoutButtonText() {return getCheckoutButton().getAttribute(TAG_ATTRIBUTE_VALUE);}
	public void clickCheckouteButton() {getCheckoutButton().click();}

	// Business Logic
	
	public ShoppingCartMessagePage updateProductQuantityByPartialName(String partialProductCartName, String numOfItems) {
		getProductsCartListComponent().clickQuantityProductCartByPartialName(partialProductCartName);
		getProductsCartListComponent().clearQuantityProductCartByPartialName(partialProductCartName);
		getProductsCartListComponent().setQuantityProductCartByPartialName(partialProductCartName, numOfItems);
		getProductsCartListComponent().updateProductCartByPartialName(partialProductCartName);
        return new ShoppingCartMessagePage(driver); 
    }
	
	public void clickProductQuantityByPartialName(String partialProductName) {
		getProductsCartListComponent().clickQuantityProductCartByPartialName(partialProductName);
	}
	
	public void removeProductQuantityByPartialName(String partialProductName) {
		getProductsCartListComponent().removeProductCartByPartialName(partialProductName);
	}

 	public ProductsListCartComponent gotoProductsListCartComponent() {return new ProductsListCartComponent(driver);}
}