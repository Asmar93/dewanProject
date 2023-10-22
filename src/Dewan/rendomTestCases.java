package Dewan;

import java.awt.event.InvocationEvent;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class rendomTestCases {

	WebDriver driver;
	SoftAssert myAssertion = new SoftAssert();
	Random myRand = new Random();

	// Get the current date
	LocalDate today = LocalDate.now();
	// Define the desired date format
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	// Format the date as per the desired format
	String formattedDate = today.format(formatter);
	String afterOneWeek = today.plusWeeks(1).format(formatter);
	
	
	private int invocationCount = 0;
	String uniqueId = "ID_" + Instant.now().toEpochMilli();
	@BeforeTest
	public void beforeLogin() {
		
		 // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
		driver.get("http://localhost:3333/login");
		driver.manage().window().maximize();

		// changing language
//		WebElement langButton = driver.findElement(By.xpath("//*[@id=\"Imglang\"]"));
//		langButton.click();

		// To login
		String userName = "Ohoud.AlJaafari";
		String passWord = "s";
		WebElement userInput = driver.findElement(By.xpath("//*[@id=\"tbxName\"]"));
		WebElement passInput = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));
		WebElement login = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));

		// To fill credentials

		userInput.sendKeys(userName);
		passInput.sendKeys(passWord);
		login.click();

		// check if message of other login exist shown and click ok
		try {
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}
	}
	
//	@Test(priority = 1,invocationCount = 2)
//	public void incomingTestDup() throws InterruptedException {
//		invocationCount++;
//		
//        
//		WebElement enterProcess = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/span"));
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//		enterProcess.click();
//
//		WebElement createInComing = driver
//				.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/div/div/ul/li[4]/a"));
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//		createInComing.click();
//
//		// fill the subject
//		WebElement subjectOfBook = driver.findElement(By.xpath("//*[@id=\"tbxSubject\"]"));
//		String sendToSubject = "Asmar test incoming duplicate";
//		subjectOfBook.sendKeys(sendToSubject);
//
//		// fill the book id from resource
//		WebElement bookIdFromResource = driver.findElement(By.xpath("//*[@id=\"MainContent_txt_2488\"]"));
//
//		
//		if (invocationCount==1) {
//		
//		bookIdFromResource.sendKeys(uniqueId);
//		
//		}
//		
//		
//
//		// Find the input element and insert the unique ID
//		if (invocationCount==2) {
//			bookIdFromResource.sendKeys(uniqueId);
//		
//		}
//		
//		// Print the generated unique ID
////		System.out.println("Generated unique ID: " + uniqueId);
//
//		// fill the received department
//
//		WebElement receivedDept = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2496\"]"));
//		receivedDept.click();
//		receivedDept.clear();
//		receivedDept.sendKeys("9");
//		receivedDept.sendKeys(Keys.ENTER);
//
//		// fill the sender side
//
//		WebElement senderSide = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2492\"]"));
//		senderSide.click();
//		senderSide.clear();
//		senderSide.sendKeys("32");
//		senderSide.sendKeys(Keys.ENTER);
//
//		// select section randomly from all
//		WebElement sectionSelector = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2576\"]"));
//		sectionSelector.click();
//		sectionSelector.clear();
//		int randomSection = myRand.nextInt(2) + 1;
//		sectionSelector.sendKeys(String.valueOf(randomSection));
//		sectionSelector.sendKeys(Keys.ENTER);
//		Thread.sleep(1000);
//
//		// initial save
//		WebElement initialSaveButton = driver
//				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnSaveDraft\"]/span[1]"));
//		Thread.sleep(1000);
//		initialSaveButton.click();
//		Thread.sleep(1000);
//		
//		try {
//
//		WebElement duplicateError = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_notification_simpleContentDiv\"]"));
//		boolean actualError = duplicateError.isDisplayed();
//		boolean expectedError = true;
//		myAssertion.assertEquals(actualError, expectedError);
//		}
//		
//		catch (Exception e) {
//			// TODO: handle exception
//		
//		
//		// attach the book
//		WebElement attachButton = driver
//				.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2503_LinklblMessages\"]"));
//		Thread.sleep(1000);
//		attachButton.click();
//
//		WebElement fileInput = driver.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2503_fileUpload\"]"));
//		String filePath = "C:\\Users\\mbarghuthi\\eclipse-workspace\\Dewan\\src\\20230626095446.jpg";
//		fileInput.sendKeys(filePath);
//
//		WebElement addButton = driver.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2503_btnUpload\"]"));
//		addButton.click();
//
//		// final save
//		WebElement finalSave = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnSave\"]"));
//		finalSave.click();
//		
//		if (invocationCount==2) {
//			WebElement uncompletedTasks = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[2]"));
//			uncompletedTasks.click();
//			
//			// get the name of last 2 books
//			
//			WebElement getFirstBookName = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[5]"));
//			WebElement get2ndBookName = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__1\"]/td[5]"));
//			
//			String firstBookName= getFirstBookName.getText();
//			String secondBookName = get2ndBookName.getText();
//			myAssertion.assertNotEquals(firstBookName, secondBookName);
//			System.out.println("first Book Name is "+firstBookName);
//			System.out.println("second BookName is "+secondBookName);
//			
//			
//		}
//		
//		}
//		myAssertion.assertAll();
//		
//	}
	
	@Test ()
	public void tsksFollowsTest () throws InterruptedException {
		WebElement enterProcess = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/span"));
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		enterProcess.click();

		WebElement createCorrespondence = driver
				.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/div/div/ul/li[1]/a"));
		Thread.sleep(1000);
		createCorrespondence.click();

		// fill the subject
		WebElement subjectOfBook = driver.findElement(By.xpath("//*[@id=\"tbxSubject\"]"));
		String sendToSubject = "Asmar test internal corresponding with follow_up " + myRand.nextInt(100);
		subjectOfBook.sendKeys(sendToSubject);
		Thread.sleep(2000);

		// fill the creator
		WebElement creatorDept = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2515\"]"));
		creatorDept.click();
		creatorDept.clear();
		creatorDept.sendKeys("10");
		creatorDept.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		
		// final save
		WebElement finalSave = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnSave\"]"));
		finalSave.click();

		Thread.sleep(2000);
	}
}
