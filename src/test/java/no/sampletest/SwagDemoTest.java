package no.sampletest;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Selenium Test cases for Swag Labs Demo page
 * @author Rashmi
 * @version 1.0
 */
public class SwagDemoTest {

	private WebDriver driver;
	private static final String URL = "https://www.saucedemo.com/";

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	/*
	 * Test Case Scenario 1:
	 * Login using the following credentials and check (verify with assert) that an
	 * error message has been displayed. Use the credentials listed in the next
	 * bullet point. • Credentials: Username -> locked_out_user , Password ->
	 * secret_sauce
	 */
	@Test
	public void testLockedOutUser() {
		System.out.println("######## Test Case1 ######");
		driver.get(URL);
		WebElement username = driver.findElement(By.id("user-name"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement login = driver.findElement(By.xpath("//input[@id='login-button']"));

		username.sendKeys("locked_out_user");
		password.sendKeys("secret_sauce");
		login.click();

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		WebElement loginErrorMsg = driver.findElement(By.xpath("//h3[@data-test='error']"));

		String actualErrorMsg = "Epic sadface: Sorry, this user has been locked out.";
		String expectedErrorMsg = loginErrorMsg.getText();
		assertEquals(expectedErrorMsg, actualErrorMsg);
	}

	/*
	 * Test Case Scenario 2:
	 * Login using the following credentials (Username ->standard_user , Password -> secret_sauce)
	 */
	@Test
	public void testStandardUser() {
		System.out.println("######## Test Case2 #######");
		driver.get(URL);
		WebElement username = driver.findElement(By.id("user-name"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement login = driver.findElement(By.id("login-button"));

		username.sendKeys("standard_user");
		password.sendKeys("secret_sauce");
		login.click();
		//• Sort the Items using the Price filter (Highest to Lowest)
		final Select selectBox = new Select(driver.findElement(By.xpath("//select[@class='product_sort_container']")));
		selectBox.selectByValue("hilo");
		
		//• Check that the price of the “Sauce Labs Fleece Jacket” is 49 dollars
		driver.findElement(By.xpath(
				"//div[text()='Sauce Labs Fleece Jacket']/ancestor::div[@class='inventory_item_label']/following-sibling::div[@class='pricebar']/div[contains(string(),'$49')]"));

		//• Add the “Sauce Labs Fleece Jacket” to the basket
		WebElement backPackToCartElement = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
		backPackToCartElement.click();

		//• Check that the price of the “Sauce Labs Backpack” is 29 dollars
		driver.findElement(By.xpath(
				"//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item_label']/following-sibling::div[@class='pricebar']/div[contains(string(),'$29')]"));
		
		//• Add the “Sauce Labs Backpack” add to the basket
		WebElement jacketToCartElement = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
		jacketToCartElement.click();

		WebElement clickCartElement = driver
				.findElement(By.xpath("//div[@id='shopping_cart_container']/a[contains(@class,'shopping_cart_link')]"));
		clickCartElement.click();

		WebElement verifyCartJacketElement = driver.findElement((By.xpath(
				"//div[@class='cart_list']/div[@class='cart_item']/div[@class='cart_item_label']/a/div[text()='Sauce Labs Fleece Jacket']")));

		WebElement verifyCartbackpackElement = driver.findElement((By.xpath(
				"//div[@class='cart_list']/div[@class='cart_item']/div[@class='cart_item_label']/a/div[text()='Sauce Labs Backpack']")));

		//• Verify that the “Sauce labs fleece jacket” is in the cart (Verify with assert)
		assertEquals("Sauce Labs Fleece Jacket", verifyCartJacketElement.getText());
		//• Verify that the “Sauce labs backpack” is in the cart (Verify with assert)
		assertEquals("Sauce Labs Backpack", verifyCartbackpackElement.getText());

		//• Checkout and enter in your information in the available fields.
		WebElement checkoutButtonElement = driver.findElement(By.id("checkout"));
		checkoutButtonElement.click();

		WebElement firstNameElement = driver.findElement(By.id("first-name"));
		firstNameElement.sendKeys("Rashmi");

		WebElement lastNameElement = driver.findElement(By.id("last-name"));
		lastNameElement.sendKeys("V");

		WebElement postalCodeElement = driver.findElement(By.id("postal-code"));
		postalCodeElement.sendKeys("3208");

		WebElement continueButtonElement = driver.findElement(By.id("continue"));
		continueButtonElement.click();
		
		//• Verify that the Payment information is “SauceCard #31337” (Verify with assert)
		WebElement paymentInfoElement = driver.findElement(By.xpath("//div[text()='SauceCard #31337']"));
		assertEquals("SauceCard #31337", paymentInfoElement.getText());

		//• Verify that the shipping information is “FREE PONY EXPRESS DELIVERY!” (Verify with assert)
		WebElement shippingElement = driver.findElement(By.xpath("//div[text()='FREE PONY EXPRESS DELIVERY!']"));
		assertEquals("FREE PONY EXPRESS DELIVERY!", shippingElement.getText());
		
		//• Verify that the total is $86.38 (Verify with assert)
		WebElement totalElement = driver.findElement(By.xpath("//div[@class='summary_total_label']"));
		String total = totalElement.getText();
		int index = total.indexOf('$');
		assertEquals("$86.38", total.substring(index));
		
		//• Click on the Finish button.
		WebElement finishButtonElement = driver.findElement(By.id("finish"));
		finishButtonElement.click();
		
		//• Verify Order confirmation page
		WebElement confirmOrderElement = driver.findElement(By.xpath("//h2[@class='complete-header']"));
		assertEquals("THANK YOU FOR YOUR ORDER", confirmOrderElement.getText());

		WebElement logoutMenuElement = driver.findElement(By.id("react-burger-menu-btn"));
		logoutMenuElement.click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//• logout the application
		WebElement logoutElement = driver.findElement(By.xpath("//*[@id='logout_sidebar_link']"));
		logoutElement.click();
	}

	@After
	public void tearDown() {
		System.out.println("##############################");
		driver.close();
		driver.quit();
	}
}
