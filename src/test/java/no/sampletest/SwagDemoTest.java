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


public class SwagDemoTest {

	private WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test
	public void testLoginFailed() {
		System.out.println("######## Test Case1 ######");
		driver.get("https://www.saucedemo.com/");
		WebElement username = driver.findElement(By.id("user-name"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement login = driver.findElement(By.xpath("//input[@id='login-button']"));

		username.sendKeys("locked_out_user");
		username.sendKeys("standard_user");
		password.sendKeys("secret_sauce");
		login.click();

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		WebElement loginErrorMsg = driver.findElement(By.xpath("//h3[@data-test='error']"));
		System.out.println(loginErrorMsg.getText());

		String actualErrorMsg = "Epic sadface: Username and password do not match any user in this service";
		String expectedErrorMsg = loginErrorMsg.getText();
		assertEquals(expectedErrorMsg, actualErrorMsg);
	}

	@Test
	public void testLoginSuccess() {
		System.out.println("######## Test Case2 #######");
		driver.get("https://www.saucedemo.com/");
		WebElement username = driver.findElement(By.id("user-name"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement login = driver.findElement(By.id("login-button"));

		username.sendKeys("standard_user");
		password.sendKeys("secret_sauce");
		login.click();

		final Select selectBox = new Select(driver.findElement(By.xpath("//select[@class='product_sort_container']")));
		selectBox.selectByValue("hilo");

		WebElement actualCostForFleeceJacketElement = driver.findElement(By.xpath(
				"//div[text()='Sauce Labs Fleece Jacket']/ancestor::div[@class='inventory_item_label']/following-sibling::div[@class='pricebar']/div[@class='inventory_item_price']"));
		String actualCostForFleeceJacket = actualCostForFleeceJacketElement.getText();
		System.out.println(actualCostForFleeceJacket);

		WebElement actualCostForBackpackElement = driver.findElement(By.xpath(
				"//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item_label']/following-sibling::div[@class='pricebar']/div[@class='inventory_item_price']"));
		String actualCostForBackpack = actualCostForBackpackElement.getText();
		System.out.println(actualCostForBackpack);

		WebElement jacketToCartElement = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));

		jacketToCartElement.click();

		WebElement backPackToCartElement = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));

		backPackToCartElement.click();

		WebElement clickCartElement = driver
				.findElement(By.xpath("//div[@id='shopping_cart_container']/a[contains(@class,'shopping_cart_link')]"));
		clickCartElement.click();

		WebElement verifyCartJacket = driver.findElement((By.xpath(
				"//div[@class='cart_list']/div[@class='cart_item']/div[@class='cart_item_label']/a/div[text()='Sauce Labs Fleece Jacket']")));
		System.out.println(verifyCartJacket.getText());

		String product1 = "Sauce Labs Fleece Jacket";
		String product2 = "Sauce Labs Backpack";

		WebElement verifyCartbackpack = driver.findElement((By.xpath(
				"//div[@class='cart_list']/div[@class='cart_item']/div[@class='cart_item_label']/a/div[text()='Sauce Labs Backpack']")));
		System.out.println(verifyCartbackpack.getText());

		assertEquals(product1, verifyCartJacket.getText());
		assertEquals(product2, verifyCartbackpack.getText());

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

		WebElement paymentInfoElement = driver.findElement(By.xpath("//div[text()='SauceCard #31337']"));
		assertEquals("SauceCard #31337", paymentInfoElement.getText());

		WebElement shippingElement = driver.findElement(By.xpath("//div[text()='FREE PONY EXPRESS DELIVERY!']"));
		assertEquals("FREE PONY EXPRESS DELIVERY!", shippingElement.getText());
		
		//xpath with substring function
//			WebElement totalElement = driver.findElement(By.xpath("substring-after(//div[@class='summary_total_label'],':')"));
		WebElement totalElement = driver.findElement(By.xpath("//div[@class='summary_total_label']"));
		String total = totalElement.getText();
		int index = total.indexOf('$');
		assertEquals("$86.38", total.substring(index));

		WebElement finishButtonElement = driver.findElement(By.id("finish"));
		finishButtonElement.click();

		WebElement confirmOrderElement = driver.findElement(By.xpath("//h2[@class='complete-header']"));
		assertEquals("THANK YOU FOR YOUR ORDER", confirmOrderElement.getText());

		WebElement logoutMenuElement = driver.findElement(By.id("react-burger-menu-btn"));
		logoutMenuElement.click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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
