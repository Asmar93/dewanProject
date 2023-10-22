package Dewan;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class loginPage {
	
	
	SoftAssert myAssertion = new SoftAssert();
	Random myRand = new Random();
	
	WebDriver driver;
	@BeforeTest 
	public void beforeLogin () {
		 // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
		driver.get("http://localhost:3333/login");
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void validCredentials() throws InterruptedException {
//		TC_1: ********Valid user name and Valid password*********										

		// To login

		String userName = "Ohoud.AlJaafari";
		String passWord = "Aa1";
		WebElement userInput = driver.findElement(By.xpath("//*[@id=\"tbxName\"]"));
		WebElement passInput = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));
		WebElement login = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));

		// To fill credentials

		userInput.sendKeys(userName);
		passInput.sendKeys(passWord);
		login.click();
		try {
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally
			
		}
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement userField = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[9]/a/span"));
		userField.click();
		WebElement userNameField = driver.findElement(By.xpath("//*[@id=\"MainContent_tbxUserName\"]"));
		String expectedUserNameText = userNameField.getAttribute("value");
		myAssertion.assertEquals(userName, expectedUserNameText);
		
		WebElement logoutButton = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[10]/a"));
		System.out.println("The current user is "+expectedUserNameText);
		logoutButton.click();
		// log out assertion 
		WebElement loginButtonExisting = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));
		boolean expectedResult = true;
		boolean actualResult = loginButtonExisting.isEnabled();
		myAssertion.assertEquals(actualResult, expectedResult);
		
		myAssertion.assertAll();
//		driver.close();
	}
	@Test (priority = 2)
	public void loginInValid () {
		
		// TC_2: ********invValid user name and Valid password*********	
		// To login

		String userName = "Ohoud.AlJaafari"+myRand.nextInt(100);
		String passWord = "s"+myRand.nextInt(100);
		WebElement userInput = driver.findElement(By.xpath("//*[@id=\"tbxName\"]"));
		WebElement passInput = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));
		WebElement login = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));

		// To fill credentials

		userInput.sendKeys(userName);
		passInput.sendKeys(passWord);
		login.click();
		try {
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally
			
		}
		WebElement wrongCredentialsMessage = driver.findElement(By.xpath("//*[@id=\"swal2-content\"]"));
		String expectederror = wrongCredentialsMessage.getText();
		String actualError = "يرجى التأكد من اسم المستخدم او الرقم السري";
		AssertJUnit.assertEquals(actualError, expectederror);
		myAssertion.assertAll();
		System.out.println("the error message is "+expectederror);
		
		driver.close();
	}
}
