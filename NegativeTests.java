package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {
	@Parameters({ "username", "password", "expectedMessage", "browser" })
	@Test(priority = 1, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username, String password, String expectedErrorMessage,String browser)
			throws InterruptedException {
		System.out.println("starting incorrectUsernameTest");

		/* Create driver */
		System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
		WebDriver driver=new FirefoxDriver();
		
		/* Maximize the browser window */
		driver.manage().window().maximize();

		/* open test page */
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("page is open");
		/* Enter username */
		WebElement username1 = driver.findElement(By.id("username"));
		username1.sendKeys(username);

		/* enter password */
		WebElement password1 = driver.findElement(By.id("password"));
		password1.sendKeys(password);

		/* Press login button */
		WebElement login = driver.findElement(By.xpath("//*[@id=\"login\"]/button"));
		login.click();

		Thread.sleep(8000);

		/* Verifications */
		WebElement errorMessage = driver.findElement(By.cssSelector("#flash"));
		String actualMessage = errorMessage.getText();
		Assert.assertTrue(actualMessage.contains(expectedErrorMessage), "       Error messages are different      ");

		/* Close browser */
		driver.quit();
	}
	/*
	 * @Test(priority=2,groups = { "smokeTests" }) public void
	 * incorrectPasswordTest() {
	 * System.out.println("starting incorrectPasswordTest");
	 * 
	 * /*Create driver System.setProperty("webdriver.chrome.driver",
	 * "src\\main\\resources\\chromedriver.exe"); WebDriver driver=new
	 * ChromeDriver();
	 * 
	 * Maximize the browser window driver.manage().window().maximize();
	 * 
	 * open test page String url="http://the-internet.herokuapp.com/login";
	 * driver.get(url); System.out.println("page is open");
	 * 
	 * Enter username WebElement username2=driver.findElement(By.id("username"));
	 * username.sendKeys("tomsmith");
	 * 
	 * enter password WebElement password2=driver.findElement(By.id("password"));
	 * password2.sendKeys("incorrect password");
	 * 
	 * Press login button WebElement
	 * login=driver.findElement(By.xpath("//*[@id=\"login\"]/button"));
	 * login.click();
	 * 
	 * Verifications WebElement errorMessage= driver.findElement(By.id("flash"));
	 * String expectedMessage="You logged into a secure area!"; String
	 * actualMessage=errorMessage.getText();
	 * Assert.assertTrue(actualMessage.contains(expectedMessage),
	 * "       Error messages are different      ");
	 * 
	 * Close browser driver.quit(); }
	 */
}
