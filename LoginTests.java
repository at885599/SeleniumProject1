package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

	private WebDriver driver;
	@Parameters({ "browser" })
	@BeforeTest(alwaysRun=true)
	private void setUp(String browser) {
		/* Create driver */
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe"); 
			driver=new ChromeDriver();
			break;
			
		case "firefox":
			System.setProperty("webdriver.gecko.driver","src/main/resources/geckodriver.exe");
			driver=new FirefoxDriver();
			break;
		
		default:
			System.out.println("Starting test in default Chrome browser due to non availability of selected browser");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe"); 
			driver=new ChromeDriver();
			break;
		}

		/* maximize browser window */
		driver.manage().window().maximize();
	}

	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })
	public void loginTest() {

		System.out.println("login test started");

		/* Open test page */
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is open");

		/* enter username */
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");

		/* enter password */
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("SuperSecretPassword!");

		/* click login button */
		WebElement login = driver.findElement(By.xpath("//*[@id=\"login\"]/button"));
		login.click();

		/* Verifications: */

		/* new URL */
		String expectedURL = "http://the-internet.herokuapp.com/secure";
		String actualURL = driver.getCurrentUrl();
		Assert.assertEquals(actualURL, expectedURL, "NOt the same URL");

		/* logout button is visible */
		WebElement logout = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logout.isDisplayed(), "Logout button is not visible");

		/* successful login message */
		WebElement susscessMessage = driver.findElement(By.cssSelector("#flash"));
		String expectedMessage = "You logged into a secure area!";
		String actualMessage = susscessMessage.getText();
		// Assert.assertEquals(susscessMessage, expectedMessage, "Login message is
		// different \n ");
		Assert.assertTrue(actualMessage.contains(expectedMessage), "Login message is different \n ");

		
	}

	
	

	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 1, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username, String password, String expectedErrorMessage)
			throws InterruptedException {
		System.out.println("starting incorrectUsernameTest");

		/* Create driver */
		System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();

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

		
	}
	@AfterMethod(alwaysRun=true)
	private void tearDown() {
		/* Close browser */
		driver.quit();
	}
}
