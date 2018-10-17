package com.softserve.edu.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends AUnloggedRighMenuComponent{

	private WebElement emailField;
	private WebElement passwordField;

	public LoginPage(WebDriver driver) {
		super(driver);
		initLoginComponent();
	}

	private void initLoginComponent() {
		emailField = driver.findElement(By.id("input-email"));
		passwordField = driver.findElement(By.id("input-password"));
	}
	
	// PageObject Atomic Operation

	// emailField
	public WebElement getEmailField() {
        return emailField;
    }

	public String getEmailFieldText() {
        return getEmailField().getAttribute(TAG_ATTRIBUTE_VALUE);
    }
	
	public void setEmailField(String text) {
		getEmailField().sendKeys(text);
    }
	 
	public void clearEmailField() {
		getEmailField().clear();
    }
	 
	public void clickEmailField() {
		getEmailField().click();
    }

	// passwordField
	public WebElement getPasswordField() {
        return passwordField;
    }

	public String getPasswordFieldText() {
        return getPasswordField().getAttribute(TAG_ATTRIBUTE_VALUE);
    }
	
	public void setPasswordField(String text) {
		getPasswordField().sendKeys(text);
    }
	 
	public void clearPasswordField() {
		getPasswordField().clear();
    }
	 
	public void clickPasswordField() {
		getPasswordField().click();
    }

	// Business Logic

}
