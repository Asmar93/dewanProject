package Dewan;

import org.testng.annotations.Test;
import java.awt.Color;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

public class bookTransaction {

	WebDriver driver = new ChromeDriver();
	SoftAssert myAssertion = new SoftAssert();
	Random myRand = new Random();

	// Get the current date
	LocalDate today = LocalDate.now();
	// Define the desired date format
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	// Format the date as per the desired format
	String formattedDate = today.format(formatter);
	String afterOneWeek = today.plusWeeks(1).format(formatter);
	Actions actions = new Actions(driver);

	@BeforeTest
	public void beforeLogin() {
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
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}
	}

	@Test(priority = 1)
	public void makeGeneralaizationTest() throws InterruptedException {

		// create new generalization
		WebElement enterProcess = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/span"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		enterProcess.click();

		WebElement createGeneralization = driver
				.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/div/div/ul/li[2]/a"));
		Thread.sleep(3000);
		createGeneralization.click();

		// fill the subject
		WebElement subjectOfBook = driver.findElement(By.xpath("//*[@id=\"tbxSubject\"]"));
		String sendToSubject = "Asmar test " + myRand.nextInt(100);
		subjectOfBook.sendKeys(sendToSubject);

		// fill the date
		WebElement dateField = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_cal_2543_dateInput\"]"));
		dateField.sendKeys(formattedDate);

		// attach the book
		WebElement attachButton = driver
				.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2544_pnlMouseClick\"]"));
		attachButton.click();

		WebElement fileInput = driver.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2544_fileUpload\"]"));
		String filePath = "C:\\Users\\mbarghuthi\\eclipse-workspace\\Dewan\\src\\20230626095446.jpg";

		fileInput.sendKeys(filePath);
		WebElement submitButton = driver.findElement(
				By.xpath("//*[@id=\"MainContent_uploadFile_2544_pnlCollapsable\"]/div/div[1]/div[2]/div[3]"));
		submitButton.click();

		// save the book
		WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnSave\"]"));
		saveButton.click();

		// to get book id
		WebElement generalizationIdField = driver.findElement(By.xpath("//*[@id=\"MainContent_txtSerial_2577\"]"));
		String GeneralizationId = generalizationIdField.getAttribute("value");

		WebElement sendingButton = driver.findElement(By.xpath("//*[@id=\"MainContent_summary1\"]"));
		sendingButton.click();

		WebElement fullSaveButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnApprove\"]"));
		fullSaveButton.click();
		Thread.sleep(5000);

		// the assertion process of generalization name
		WebElement generalizationTab = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[5]/a/span"));
		generalizationTab.click();

		WebElement generalizationNameField = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[2]"));
		String actualGeneralizationName = generalizationNameField.getText();
		String expectedGeneralizationName = sendToSubject;
		System.out.println("The expected book name is " + expectedGeneralizationName);
		System.out.println("The actual book name is " + actualGeneralizationName);

		myAssertion.assertEquals(actualGeneralizationName, expectedGeneralizationName);

		// the assertion process of generalization id

		WebElement generalizationSavedId = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[1]"));
		String actualGeneralizationId = generalizationSavedId.getText();
		String expectedGeneralizationId = GeneralizationId;
		System.out.println("The expected Generalization ID is " + expectedGeneralizationId);
		System.out.println("The actual Generalization ID is " + actualGeneralizationId);
		myAssertion.assertEquals(actualGeneralizationId, expectedGeneralizationId);

		// reflection to other user assertion

		// log out from current user
		WebElement logOutButton = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[10]/a/span"));
		logOutButton.click();

		// login to other user

		String userName = "abuzaid";
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
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}
		Thread.sleep(3000);
		WebElement unwatchedGen = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[5]/a/span"));
		unwatchedGen.click();
		WebElement generalizationNameFieldOtherUser = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[2]"));
		String actualGeneralizationNameOtherUser = generalizationNameFieldOtherUser.getText();
		String expectedGeneralizationNameOtherUser = sendToSubject;
		System.out.println("The expected book name is " + expectedGeneralizationNameOtherUser);
		System.out.println("The actual book name is " + actualGeneralizationNameOtherUser);

		myAssertion.assertEquals(actualGeneralizationNameOtherUser, expectedGeneralizationNameOtherUser);

		// the assertion process of generalization id

		WebElement generalizationSavedIdOtherUser = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[1]"));
		String actualGeneralizationIdOtherUser = generalizationSavedIdOtherUser.getText();
		String expectedGeneralizationIdOtherUser = GeneralizationId;
		System.out.println("The expected Generalization ID is " + expectedGeneralizationIdOtherUser);
		System.out.println("The actual Generalization ID is " + actualGeneralizationIdOtherUser);
		myAssertion.assertEquals(actualGeneralizationIdOtherUser, expectedGeneralizationIdOtherUser);

		myAssertion.assertAll();
		WebElement logOutButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[10]/a/span"));
		logOutButton1.click();

		// To login to main user
		String userName1 = "Ohoud.AlJaafari";
		String passWord1 = "s";
		WebElement userInput1 = driver.findElement(By.xpath("//*[@id=\"tbxName\"]"));
		WebElement passInput1 = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));
		WebElement login1 = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));

		// To fill credentials

		userInput1.sendKeys(userName1);
		passInput1.sendKeys(passWord1);
		login1.click();

		// check if message of other login exist shown and click ok
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}
//		Thread.sleep(5000);
//		driver.close();

	}

	@Test(priority = 2)
	public void outGoingTest() throws InterruptedException {

		// create new outgoing
		WebElement enterProcess = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/span"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		enterProcess.click();

		WebElement createOutGoing = driver
				.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/div/div/ul/li[3]/a"));
		Thread.sleep(1000);
		createOutGoing.click();

		// fill the subject
		WebElement subjectOfBook = driver.findElement(By.xpath("//*[@id=\"tbxSubject\"]"));
		String sendToSubject = "Asmar test generalization " + myRand.nextInt(100);
		subjectOfBook.sendKeys(sendToSubject);
		Thread.sleep(1000);
		// fill the sender randomly between 4 senders
//		WebElement selectSender = driver.findElement(By.xpath("//*[@id=\"MainContent_ddlOrg_2371\"]"));
//		selectSender.click();

		WebElement senderId = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2371\"]"));
		senderId.click();
		senderId.clear();
		senderId.sendKeys("7");
		senderId.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
//		List<By> senderLocator = Arrays.asList(By.cssSelector("#MainContent_ddlOrg_2371 > option:nth-child(6)"), // مكتب
//																													// الامين
//																													// العام
//				By.cssSelector("#MainContent_ddlOrg_2371 > option:nth-child(8)"), // وحدة العلاقات العامة و الاعلام
//				By.cssSelector("#MainContent_ddlOrg_2371 > option:nth-child(9)"), // وحدة الحكومة الالكترونية و
//																					// تكنولوجيا المعلومات
//				By.cssSelector("#MainContent_ddlOrg_2371 > option:nth-child(12)")); // مديرية الشؤون القانونية
//
//		int senderLocatorSize = senderLocator.size();
//		int randomIndex = myRand.nextInt(senderLocatorSize);
//
//		By randomselector = senderLocator.get(randomIndex);
//		WebElement randomSender = driver.findElement(randomselector);
//		randomSender.click();
//		Thread.sleep(1000);

		// select receiver randomly from all
		WebElement receiverSelector = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2375\"]"));
		receiverSelector.click();
		receiverSelector.clear();
//		int randomreceiver = myRand.nextInt(330) + 1;
		receiverSelector.sendKeys("80");
		receiverSelector.sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		// select section randomly from all
		WebElement sectionSelector = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2575\"]"));
		sectionSelector.click();
		sectionSelector.clear();
		int randomSection = myRand.nextInt(2) + 1;
		sectionSelector.sendKeys(String.valueOf(randomSection));
		sectionSelector.sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		// attach the book
		WebElement attachButton = driver
				.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2472_LinklblMessages\"]"));
		attachButton.click();

		WebElement fileInput = driver.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2472_fileUpload\"]"));
		String filePath = "C:\\Users\\mbarghuthi\\eclipse-workspace\\Dewan\\src\\20230626095446.jpg";
		fileInput.sendKeys(filePath);

		WebElement submitButton = driver.findElement(
				By.xpath("//*[@id=\"MainContent_uploadFile_2472_pnlCollapsable\"]/div/div[1]/div[2]/div[3]"));
		submitButton.click();

		// save the book
		WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnSave\"]"));
		saveButton.click();

		// the assertion process of outgoing name
		WebElement myTasksTab = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[3]/a"));
		myTasksTab.click();

		WebElement outGoingNameField = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[3]"));
		String actualOutGoingName = outGoingNameField.getText();
		String expectedOutGoingName = sendToSubject;
		System.out.println("The expected outgoing name is " + expectedOutGoingName);
		System.out.println("The actual outgoing name is " + actualOutGoingName);
		myAssertion.assertEquals(actualOutGoingName, expectedOutGoingName);

		// the assertion process of outgoing status
		WebElement outGoingStatusField = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[5]"));
		String actualOutGoingStatus = outGoingStatusField.getText();
		String expectedOutGoingStatus = "تمت العملية بنجاح";
		System.out.println("The expected outgoing status is " + expectedOutGoingStatus);
		System.out.println("The actual outgoing status is " + actualOutGoingStatus);
		myAssertion.assertEquals(actualOutGoingStatus, expectedOutGoingStatus);

		// reflection and closing assertion
		WebElement showBookButton = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00_ctl04_TaskUrl\"]"));
		showBookButton.click();
		WebElement receivedUsersButton = driver.findElement(By.xpath("//*[@id=\"MainContent_summary2\"]"));
		receivedUsersButton.click();

		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			WebElement receivedUser = driver
					.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVUserAssign_ctl00__0\"]/td[2]"));
			String getUserArName = receivedUser.getText();

			// login at admin web to get receiver user name
			driver.get("http://localhost:4444/login.aspx");

			WebElement userNameAdminInput = driver.findElement(By.xpath("//*[@id=\"tbxUserName\"]"));
			WebElement userPassAdminInput = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));

			String userName2 = "admin";
			String password2 = "s";

			userNameAdminInput.sendKeys(userName2);
			userPassAdminInput.sendKeys(password2);

			WebElement loginButton2 = driver.findElement(By.xpath("//*[@id=\"btnLogin2\"]"));
			loginButton2.click();

			// check if message of other login exist shown and click ok
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				WebElement confirmLoginButton = driver.findElement(By.xpath("/html/body/div/div/div[10]/button[1]"));
				confirmLoginButton.click();
			} catch (Exception e) {
				// the user will login normally

			}

			WebElement userButton = driver.findElement(By.xpath("//*[@id=\"ctl00_li6\"]/a"));
			userButton.click();
			WebElement userDefinitionButton = driver.findElement(By.xpath("//*[@id=\"ctl00_Users\"]"));
			userDefinitionButton.click();

			WebElement arabicName = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxFullNameAR\"]"));
			arabicName.sendKeys(getUserArName);
			arabicName.sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			WebElement getUserName = driver
					.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVViewData\"]/tbody/tr[2]/td[4]"));
			String ReceiverUserName = getUserName.getText();
			driver.get("http://localhost:3333/login");

			String userName1 = ReceiverUserName;
			String passWord1 = "s";
			WebElement userInput1 = driver.findElement(By.xpath("//*[@id=\"tbxName\"]"));
			WebElement passInput1 = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));
			WebElement login1 = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));

			// To fill credentials

			userInput1.sendKeys(userName1);
			passInput1.sendKeys(passWord1);
			login1.click();

			// check if message of other login exist shown and click ok
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
				WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
				confirmLoginButton.click();
			} catch (Exception e) {
				// the user will login normally

			}
			WebElement subjectField = driver
					.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[6]"));
			String actualSubjectName = subjectField.getText();
			String expectedSubjectName = sendToSubject;

			// assertion of reflecting on uncompleted tasks
			myAssertion.assertEquals(actualSubjectName, expectedSubjectName);

			// assertion of close the outgoing book

			WebElement showButtonOfReceiver = driver
					.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00_ctl04_TaskUrl\"]"));
			showButtonOfReceiver.click();
			WebElement sendingButton = driver.findElement(By.xpath("//*[@id=\"MainContent_summary1\"]"));
			sendingButton.click();

			WebElement seenButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnView\"]"));
			seenButton.click();
			Thread.sleep(3000);
			WebElement completedTasksButton = driver
					.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[4]/a/span"));
			completedTasksButton.click();

			WebElement subjectNameOnCompletedTasks = driver
					.findElement(By.xpath("//*[@id=\"ctl00_MainContent_CompletedTasksGrid_ctl00__0\"]/td[3]"));
			String actualCompSubjectName = subjectNameOnCompletedTasks.getText();
			String expectedCompSubjectName = sendToSubject;
			myAssertion.assertEquals(actualCompSubjectName, expectedCompSubjectName);

			WebElement logOutButton = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[10]/a/span"));
			logOutButton.click();

		} catch (Exception e) {
			String actualCheckReceiver = "No Receiver existed";
			myAssertion.assertNotEquals(actualCheckReceiver, "No Receiver existed");
			System.out.println("No Receiver existed");
		} finally {

			// To login to main user
			driver.get("http://localhost:3333/login");
			String userName1 = "Ohoud.AlJaafari";
			String passWord1 = "s";
			WebElement userInput1 = driver.findElement(By.xpath("//*[@id=\"tbxName\"]"));
			WebElement passInput1 = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));
			WebElement login1 = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));

			// To fill credentials

			userInput1.sendKeys(userName1);
			passInput1.sendKeys(passWord1);
			login1.click();

			// check if message of other login exist shown and click ok
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
				WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
				confirmLoginButton.click();
			} catch (Exception e) {
				// the user will login normally

			}
		}

		myAssertion.assertAll();

//		Thread.sleep(4000);
//		driver.close();
	}

	@Test(priority = 3)
	public void generalizationOfOutGoing() throws InterruptedException {

		// create new outgoing
		WebElement enterProcess = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/span"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		enterProcess.click();

		WebElement createOutGoing = driver
				.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/div/div/ul/li[3]/a"));
		Thread.sleep(1000);
		createOutGoing.click();

		// fill the subject
		WebElement subjectOfBook = driver.findElement(By.xpath("//*[@id=\"tbxSubject\"]"));
		String sendToSubject = "Asmar test generalize outgoing " + myRand.nextInt(100);
		subjectOfBook.sendKeys(sendToSubject);

		// fill the sender randomly between 4 senders
		WebElement selectSender = driver.findElement(By.xpath("//*[@id=\"MainContent_ddlOrg_2371\"]"));
		selectSender.click();

		List<By> senderLocator = Arrays.asList(By.cssSelector("#MainContent_ddlOrg_2371 > option:nth-child(6)"), // مكتب
																													// الامين
																													// العام
				By.cssSelector("#MainContent_ddlOrg_2371 > option:nth-child(8)"), // وحدة العلاقات العامة و الاعلام
				By.cssSelector("#MainContent_ddlOrg_2371 > option:nth-child(9)"), // وحدة الحكومة الالكترونية و
																					// تكنولوجيا المعلومات
				By.cssSelector("#MainContent_ddlOrg_2371 > option:nth-child(12)")); // مديرية الشؤون القانونية

		int senderLocatorSize = senderLocator.size();
		int randomIndex = myRand.nextInt(senderLocatorSize);

		By randomselector = senderLocator.get(randomIndex);
		WebElement randomSender = driver.findElement(randomselector);
		randomSender.click();
		Thread.sleep(2000);

		// select receiver randomly from all
		WebElement receiverSelector = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2375\"]"));
		receiverSelector.click();
		receiverSelector.clear();
//				int randomreceiver = myRand.nextInt(330) + 1;
		receiverSelector.sendKeys("80");
		receiverSelector.sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		// select section randomly from all
		WebElement sectionSelector = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2575\"]"));
		sectionSelector.click();
		sectionSelector.clear();
		int randomSection = myRand.nextInt(2) + 1;
		sectionSelector.sendKeys(String.valueOf(randomSection));
		sectionSelector.sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		// attach the book
		WebElement attachButton = driver
				.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2472_LinklblMessages\"]"));
		attachButton.click();

		WebElement fileInput = driver.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2472_fileUpload\"]"));
		String filePath = "C:\\Users\\mbarghuthi\\eclipse-workspace\\Dewan\\src\\20230626095446.jpg";
		fileInput.sendKeys(filePath);

		WebElement submitButton = driver.findElement(
				By.xpath("//*[@id=\"MainContent_uploadFile_2472_pnlCollapsable\"]/div/div[1]/div[2]/div[3]"));
		submitButton.click();

		// save the book
		WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnSave\"]"));
		saveButton.click();

		// go general
		WebElement myTasksTab = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[3]/a"));
		myTasksTab.click();
		WebElement showButton = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00_ctl04_TaskUrl\"]"));
		showButton.click();

		WebElement sendingButton = driver.findElement(By.xpath("//*[@id=\"MainContent_summary1\"]"));
		sendingButton.click();

		actions.sendKeys(Keys.PAGE_DOWN).perform();
		actions.sendKeys(Keys.PAGE_DOWN).perform();
		Thread.sleep(2000);
		WebElement generalizationButton = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnGeneralization\"]"));

		generalizationButton.click();

		Thread.sleep(2000);

		// Switch to the alert
		Alert alert = driver.switchTo().alert();
		alert.accept();
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		WebElement unwatchedGenButton = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[5]"));
		unwatchedGenButton.click();

		// assertion process
		String expectedGenelizedOutGoing = sendToSubject;
		WebElement subjectField = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[2]"));
		String actualGeneralizedOutgoing = subjectField.getText();
		myAssertion.assertEquals(actualGeneralizedOutgoing, expectedGenelizedOutGoing);
		myAssertion.assertAll();

//		Thread.sleep(4000);
//		driver.close();

	}

	@Test(priority = 4)
	public void incomingTest() throws InterruptedException {

		WebElement enterProcess = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/span"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		enterProcess.click();

		WebElement createInComing = driver
				.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/div/div/ul/li[4]/a"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		createInComing.click();

		// fill the subject
		WebElement subjectOfBook = driver.findElement(By.xpath("//*[@id=\"tbxSubject\"]"));
		String sendToSubject = "Asmar test incoming " + myRand.nextInt(100);
		subjectOfBook.sendKeys(sendToSubject);

		// fill the book id from resource
		WebElement bookIdFromResource = driver.findElement(By.xpath("//*[@id=\"MainContent_txt_2488\"]"));

		String uniqueId = "ID_" + Instant.now().toEpochMilli();

		// Find the input element and insert the unique ID

		bookIdFromResource.sendKeys(uniqueId);

		// Print the generated unique ID
		System.out.println("Generated unique ID: " + uniqueId);

		// fill the received department

		WebElement receivedDept = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2496\"]"));
		receivedDept.click();
		receivedDept.clear();
		receivedDept.sendKeys("9");
		receivedDept.sendKeys(Keys.ENTER);

		// fill the sender side

		WebElement senderSide = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2492\"]"));
		senderSide.click();
		senderSide.clear();
		senderSide.sendKeys("32");
		senderSide.sendKeys(Keys.ENTER);

		// select section randomly from all
		WebElement sectionSelector = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2576\"]"));
		sectionSelector.click();
		sectionSelector.clear();
		int randomSection = myRand.nextInt(2) + 1;
		sectionSelector.sendKeys(String.valueOf(randomSection));
		sectionSelector.sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		// initial save
		WebElement initialSaveButton = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnSaveDraft\"]/span[1]"));
		Thread.sleep(2000);
		initialSaveButton.click();

		Thread.sleep(2000);
		// attach the book
		WebElement attachButton = driver
				.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2503_LinklblMessages\"]"));
		attachButton.click();

		WebElement fileInput = driver.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2503_fileUpload\"]"));
		String filePath = "C:\\Users\\mbarghuthi\\eclipse-workspace\\Dewan\\src\\20230626095446.jpg";
		fileInput.sendKeys(filePath);

		WebElement addButton = driver.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2503_btnUpload\"]"));
		addButton.click();

		// final save
		WebElement finalSave = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnSave\"]"));
		finalSave.click();

		// Directing process
		WebElement directingButton = driver.findElement(By.xpath("//*[@id=\"MainContent_summary1\"]"));
		directingButton.click();

		// fill the date
		WebElement dateField = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_txtDate_dateInput\"]"));
		dateField.sendKeys(afterOneWeek);
		Thread.sleep(2000);
		dateField.sendKeys(Keys.ESCAPE);

		// fill the subject

		WebElement subjectOfDirectedBook = driver.findElement(By.xpath("// *[@id=\"txtNote\"]"));
		String sendToSubjectOfDirectedBook = "Asmar test incoming " + myRand.nextInt(100);
		subjectOfDirectedBook.sendKeys(sendToSubjectOfDirectedBook);

		// fill the receiver as a task
		WebElement directedDept = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ddlDivision_Input\"]"));
		String directedDeptName = "وحدة الحكومة الالكترونية و تكنولوجيا المعلومات";
		directedDept.sendKeys(directedDeptName);
		directedDept.sendKeys(Keys.TAB);
		Thread.sleep(2000);

		WebElement addDirectedButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnAddHierarchy\"]"));
		addDirectedButton.click();

		// to close the task from sender side

		Thread.sleep(2000);

		// fill the receiver as a cc
		WebElement dateField1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_txtDate_dateInput\"]"));
		dateField1.clear();
		WebElement directedDept1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ddlDivision_Input\"]"));
		String directedDeptName1 = "مديرية الشؤون القانونية";
		directedDept1.sendKeys(directedDeptName1);
		directedDept1.sendKeys(Keys.TAB);
		Thread.sleep(2000);

		WebElement ccButton = driver.findElement(
				By.xpath("//*[@id=\"MainContent_DivActionbarDetails\"]/div/fieldset/div[10]/div[1]/span[1]"));
		ccButton.click();
		WebElement addDirectedButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnAddHierarchy\"]"));
		addDirectedButton1.click();
		Thread.sleep(2000);

		WebElement directionButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnApprove\"]"));
		directionButton.click();
		Thread.sleep(2000);
		// assertion of creation and check the status should be "تحت الاجراء"

		WebElement myTasks = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[3]/a/span"));
		myTasks.click();

		WebElement subjectNameField = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[3]"));
		String actualSubjectName = subjectNameField.getText();
		String expectedSubjectName = sendToSubject;
		System.out.println("The actual incoming name is " + actualSubjectName + "***** The expected incoming name is"
				+ expectedSubjectName);
		myAssertion.assertEquals(actualSubjectName, expectedSubjectName);

		// check status
		WebElement InComingStatus = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[5]"));
		String actualInComingStatus = InComingStatus.getText();
		String expectedIncomingStatus = "تحت الاجراء";
		System.out.println("The actual incoming status is " + actualInComingStatus
				+ "***** The expected incoming status is" + expectedIncomingStatus);

		myAssertion.assertEquals(actualInComingStatus, expectedIncomingStatus);

		// check if the book is displayed on completed tasks list

		WebElement completedTasksButton = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[4]/a/span"));
		completedTasksButton.click();

		WebElement subjectNameField1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_CompletedTasksGrid_ctl00__0\"]/td[3]"));
		String actualSubjectName1 = subjectNameField1.getText();
		String expectedSubjectName1 = sendToSubject;
		myAssertion.assertEquals(actualSubjectName1, expectedSubjectName1);

		WebElement dateField2 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_CompletedTasksGrid_ctl00__0\"]/td[4]"));
		String getDate = dateField2.getText();

		String[] dateOnly = getDate.split(" ");
		String actualeDate = dateOnly[0];
		String expectedDate = formattedDate;
		System.out.println("The actual date is" + actualeDate + "The expected date is " + expectedDate);
		myAssertion.assertEquals(actualeDate, expectedDate);

		// reflection and closing assertion
		WebElement showBookButton = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_CompletedTasksGrid_ctl00_ctl04_TaskUrl\"]"));
		showBookButton.click();

		WebElement receivedUsersButton = driver.findElement(By.xpath("//*[@id=\"MainContent_summary2\"]"));
		receivedUsersButton.click();
		actions.sendKeys(Keys.PAGE_DOWN).perform();
		actions.sendKeys(Keys.PAGE_DOWN).perform();
		WebElement receivedUserTask = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVUserAssign_ctl00__1\"]/td[2]"));
		String getUserArNameTask = receivedUserTask.getText();

		WebElement receivedUserTask1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVUserAssign_ctl00__2\"]/td[2]"));
		String getUserArNameTask1 = receivedUserTask1.getText();

		WebElement expiryDateTask = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVUserAssign_ctl00__2\"]/td[6]"));
		String getExpiryDate = expiryDateTask.getText();

		// login at admin web to get receiver user name
		driver.get("http://localhost:4444/login.aspx");

		WebElement userNameAdminInput = driver.findElement(By.xpath("//*[@id=\"tbxUserName\"]"));
		WebElement userPassAdminInput = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));

		String userName2 = "admin";
		String password2 = "s";

		userNameAdminInput.sendKeys(userName2);
		userPassAdminInput.sendKeys(password2);

		WebElement loginButton2 = driver.findElement(By.xpath("//*[@id=\"btnLogin2\"]"));
		loginButton2.click();

		// check if message of other login exist shown and click ok
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			WebElement confirmLoginButton = driver.findElement(By.xpath("/html/body/div/div/div[10]/button[1]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}

		WebElement userButton = driver.findElement(By.xpath("//*[@id=\"ctl00_li6\"]/a"));
		userButton.click();
		WebElement userDefinitionButton = driver.findElement(By.xpath("//*[@id=\"ctl00_Users\"]"));
		userDefinitionButton.click();

		WebElement arabicName = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxFullNameAR\"]"));
		arabicName.sendKeys(getUserArNameTask);
		arabicName.sendKeys(Keys.ENTER);
		Thread.sleep(1000);
		WebElement getUserName = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVViewData\"]/tbody/tr[2]/td[4]"));
		String ReceiverUserName = getUserName.getText();

		WebElement arabicName1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxFullNameAR\"]"));
		arabicName1.clear();
		arabicName1.sendKeys(getUserArNameTask1);
		arabicName1.sendKeys(Keys.ENTER);
		Thread.sleep(1000);
		WebElement getUserName1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVViewData\"]/tbody/tr[2]/td[4]"));
		String ReceiverUserName1 = getUserName1.getText();

		driver.get("http://localhost:3333/login");

		String userName1 = ReceiverUserName;
		String passWord1 = "s";
		WebElement userInput1 = driver.findElement(By.xpath("//*[@id=\"tbxName\"]"));
		WebElement passInput1 = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));
		WebElement login1 = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));

		// To fill credentials

		userInput1.sendKeys(userName1);
		passInput1.sendKeys(passWord1);
		login1.click();

		// check if message of other login exist shown and click ok
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}
		// assertion of reflecting on uncompleted tasks
		WebElement subjectField = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[5]"));
		String actualSubjectName2 = subjectField.getText();
		String expectedSubjectName2 = sendToSubject;

		myAssertion.assertEquals(actualSubjectName2, expectedSubjectName2);

		// assertion of close the incoming book

		WebElement showButtonOfReceiver = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00_ctl04_TaskUrl\"]"));
		showButtonOfReceiver.click();
		WebElement sendingButton = driver.findElement(By.xpath("//*[@id=\"MainContent_summary1\"]"));
		sendingButton.click();

		WebElement notesButton = driver.findElement(By.xpath("//*[@id=\"txtNote\"]"));
		notesButton.sendKeys(sendToSubjectOfDirectedBook);
		WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnCloseProcess\"]/span[1]"));
		closeButton.click();

		// Switch to the alert
		Alert alert = driver.switchTo().alert();
		alert.accept();
		driver.switchTo().defaultContent();
		Thread.sleep(3000);

		WebElement completedTasksButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[4]"));
		completedTasksButton1.click();

		WebElement subjectNameOnCompletedTasks = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_CompletedTasksGrid_ctl00__0\"]/td[3]"));
		String actualCompSubjectName = subjectNameOnCompletedTasks.getText();
		String expectedCompSubjectName = sendToSubject;
		myAssertion.assertEquals(actualCompSubjectName, expectedCompSubjectName);

		WebElement logOutButton = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[10]/a/span"));
		logOutButton.click();

		String userName = ReceiverUserName1;
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
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}
		// assertion of reflecting on uncompleted tasks
		WebElement subjectField1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[6]"));
		String actualSubjectName3 = subjectField1.getText();
		String expectedSubjectName3 = sendToSubject;

		// convert the color to can check it
		Color color = Color.decode("#F5BABA");
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();

		// Convert RGB to RGBA
		int alpha = 1; // Set the alpha value (0.0 to 1.0)
		String expectedExpiryColor = "rgba(" + red + ", " + green + ", " + blue + ", " + alpha + ")";

		WebElement getColor = driver.findElement(By.cssSelector("#ctl00_MainContent_RadGrid1_ctl00__0"));
		String actualExpiryColor = getColor.getCssValue("background-color");

		myAssertion.assertEquals(actualExpiryColor, expectedExpiryColor);
		System.out.println("The actual expiry color is " + actualExpiryColor);
		System.out.println("The expected expiry color is " + expectedExpiryColor);
		myAssertion.assertEquals(actualSubjectName3, expectedSubjectName3);

		// assertion of close the incoming book for other user which have expire date

		WebElement showButtonOfReceiver1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00_ctl04_TaskUrl\"]"));
		showButtonOfReceiver1.click();
		WebElement sendingButton1 = driver.findElement(By.xpath("//*[@id=\"MainContent_summary1\"]"));
		sendingButton1.click();

		WebElement expiryDateTask1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_txtDate_dateInput\"]"));
		String ActualGetExpiryDate1 = expiryDateTask1.getAttribute("value");

		WebElement notesButton1 = driver.findElement(By.xpath("//*[@id=\"txtNote\"]"));
		notesButton1.sendKeys(sendToSubjectOfDirectedBook);
		WebElement closeButton1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnCloseProcess\"]/span[1]"));
		closeButton1.click();

		// Switch to the alert
		Alert alert1 = driver.switchTo().alert();
		alert1.accept();
		driver.switchTo().defaultContent();
		Thread.sleep(3000);

		WebElement completedTasksButton2 = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[4]"));
		completedTasksButton2.click();

		WebElement subjectNameOnCompletedTasks1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_CompletedTasksGrid_ctl00__0\"]/td[3]"));
		String actualCompSubjectName1 = subjectNameOnCompletedTasks1.getText();
		String expectedCompSubjectName1 = sendToSubject;
		myAssertion.assertEquals(actualCompSubjectName1, expectedCompSubjectName1);
		myAssertion.assertEquals(ActualGetExpiryDate1, getExpiryDate);
		System.out.println("the actual expiry date is " + ActualGetExpiryDate1);
		System.out.println("the expected expiry date is " + getExpiryDate);

		WebElement logOutButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[10]/a/span"));
		logOutButton1.click();

		// finally to check that the status of book changed to "تمت بنجاح"

		// To login
		String userName3 = "Ohoud.AlJaafari";
		String passWord3 = "s";
		WebElement userInput3 = driver.findElement(By.xpath("//*[@id=\"tbxName\"]"));
		WebElement passInput3 = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));
		WebElement login3 = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));

		// To fill credentials

		userInput3.sendKeys(userName3);
		passInput3.sendKeys(passWord3);
		login3.click();

		// check if message of other login exist shown and click ok
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}

		WebElement myTasks1 = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[3]/a/span"));
		myTasks1.click();

		WebElement subjectNameField2 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[3]"));
		String actualSubjectName4 = subjectNameField2.getText();
		String expectedSubjectName4 = sendToSubject;
		System.out.println("The actual incoming name is " + actualSubjectName4 + "***** The expected incoming name is"
				+ expectedSubjectName4);
		myAssertion.assertEquals(actualSubjectName4, expectedSubjectName4);

		// check status
		WebElement InComingStatus1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[5]"));
		String actualInComingStatus1 = InComingStatus1.getText();
		String expectedIncomingStatus1 = "تمت العملية بنجاح";
		System.out.println("The actual incoming status is " + actualInComingStatus1
				+ "***** The expected incoming status is" + expectedIncomingStatus1);

		myAssertion.assertEquals(actualInComingStatus1, expectedIncomingStatus1);

		myAssertion.assertAll();

	}

	@Test(priority = 5)
	public void internalCorrespondenceTest() throws InterruptedException {

		WebElement enterProcess = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/span"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		enterProcess.click();

		WebElement createCorrespondence = driver
				.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[1]/div/div/ul/li[1]/a"));
		Thread.sleep(1000);
		createCorrespondence.click();

		// fill the subject
		WebElement subjectOfBook = driver.findElement(By.xpath("//*[@id=\"tbxSubject\"]"));
		String sendToSubject = "Asmar test internal corresponding " + myRand.nextInt(100);
		subjectOfBook.sendKeys(sendToSubject);
		Thread.sleep(2000);

		// fill the creator
		WebElement creatorDept = driver.findElement(By.xpath("//*[@id=\"MainContent_txt2515\"]"));
		creatorDept.click();
		creatorDept.clear();
		creatorDept.sendKeys("9");
		creatorDept.sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		// attach the book
		WebElement attachButton = driver
				.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2522_pnlMouseClick\"]"));
		Thread.sleep(1000);
		attachButton.click();

		WebElement fileInput = driver.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2522_fileUpload\"]"));
		String filePath = "C:\\Users\\mbarghuthi\\eclipse-workspace\\Dewan\\src\\20230626095446.jpg";
		fileInput.sendKeys(filePath);

		WebElement addButton = driver.findElement(By.xpath("//*[@id=\"MainContent_uploadFile_2522_btnUpload\"]"));
		addButton.click();

		// final save
		WebElement finalSave = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnSave\"]"));
		finalSave.click();

		Thread.sleep(2000);

		// assertion on the status : should be "تمت العملية بنجاح" before directing
		WebElement myTasks = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[3]/a/span"));
		myTasks.click();

		WebElement subjectNameField = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[3]"));
		String actualSubjectName = subjectNameField.getText();
		String expectedSubjectName = sendToSubject;
		System.out.println("The actual correspondence name is " + actualSubjectName
				+ "***** The expected correspondence name is" + expectedSubjectName);
		myAssertion.assertEquals(actualSubjectName, expectedSubjectName,
				"The subject in my tasks page befor directing should be as");

		// check status
		WebElement correspondenceStatus = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[5]"));
		String actualCorrespondenceStatus = correspondenceStatus.getText();
		String expectedCorrespondenceStatus = "تمت العملية بنجاح";
		System.out.println("The actual incoming status is " + actualCorrespondenceStatus
				+ "***** The expected incoming status is" + expectedCorrespondenceStatus);

		myAssertion.assertEquals(actualCorrespondenceStatus, expectedCorrespondenceStatus,
				"The status before directing should be as");

		WebElement showButton = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00_ctl04_TaskUrl\"]"));
		showButton.click();

		WebElement directingButton = driver.findElement(By.xpath("//*[@id=\"MainContent_summary1\"]"));
		directingButton.click();
//
//		// fill the date
//		WebElement dateField = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_txtDate_dateInput\"]"));
//		dateField.sendKeys(afterOneWeek);
//		Thread.sleep(2000);
//		dateField.sendKeys(Keys.ESCAPE);
//
//		// fill the subject
//
//		WebElement subjectOfDirectedBook = driver.findElement(By.xpath("// *[@id=\"txtNote\"]"));
//		String sendToSubjectOfDirectedBook = "Asmar test incoming " + myRand.nextInt(100);
//		subjectOfDirectedBook.sendKeys(sendToSubjectOfDirectedBook);
//
		// fill the receiver as a task
		WebElement directedDept = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ddlDivision_Input\"]"));
		String directedDeptName = "وحدة الحكومة الالكترونية و تكنولوجيا المعلومات";
		directedDept.sendKeys(directedDeptName);
		directedDept.sendKeys(Keys.TAB);
		Thread.sleep(2000);

		WebElement addDirectedButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnAddHierarchy\"]"));
		addDirectedButton.click();

		// to close the task from sender side

		Thread.sleep(2000);

		// fill the receiver as a cc
//		WebElement dateField1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_txtDate_dateInput\"]"));
//		dateField1.clear();
		WebElement directedDept1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ddlDivision_Input\"]"));
		String directedDeptName1 = "وحدة العلاقات العامة و الاعلام";
		directedDept1.sendKeys(directedDeptName1);
		directedDept1.sendKeys(Keys.TAB);
		Thread.sleep(2000);

		WebElement ccButton = driver.findElement(
				By.xpath("//*[@id=\"MainContent_DivActionbarDetails\"]/div/fieldset/div[10]/div[1]/span[1]"));
		ccButton.click();
		WebElement addDirectedButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnAddHierarchy\"]"));
		addDirectedButton1.click();
		Thread.sleep(2000);

		WebElement directionButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnApprove\"]"));
		directionButton.click();
		Thread.sleep(2000);
		// assertion of creation and check the status should be "تحت الاجراء"

		WebElement myTasks1 = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[3]/a/span"));
		myTasks1.click();

		WebElement subjectNameField1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[3]"));
		String actualSubjectName1 = subjectNameField1.getText();
		String expectedSubjectName1 = sendToSubject;
		System.out.println("The actual Correspondence name is " + actualSubjectName1
				+ "***** The expected Correspondence name is" + expectedSubjectName1);
		myAssertion.assertEquals(actualSubjectName1, expectedSubjectName1,
				"The subject in my tasks page after directing should be as");

		// check status
		WebElement correspondenceStatus1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[5]"));
		String actualCorrespondenceStatus1 = correspondenceStatus1.getText();
		String expectedCorrespondenceStatus1 = "تحت الاجراء";
		System.out.println("The actual Correspondence status is " + actualCorrespondenceStatus1
				+ "***** The expected Correspondence status is" + expectedCorrespondenceStatus1);

		myAssertion.assertEquals(actualCorrespondenceStatus1, expectedCorrespondenceStatus1,
				"The status after directing should be as");
		
		
		WebElement logOutButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[10]/a/span"));
		logOutButton1.click();

		// To login
		String userName = "msubhi";
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
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}

		// assertion of reflecting on uncompleted tasks
		WebElement subjectNameField2 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[6]"));
		String actualSubjectName2 = subjectNameField2.getText();
		String expectedSubjectName2 = sendToSubject;

		myAssertion.assertEquals(actualSubjectName2, expectedSubjectName2,
				"The subject in uncompleted tasks page at the recived user (tsk user) should be as");

		// assertion of close the correspondence book

		WebElement showButtonOfReceiver1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00_ctl04_TaskUrl\"]"));
		showButtonOfReceiver1.click();

		WebElement sendingButton = driver.findElement(By.xpath("//*[@id=\"MainContent_summary1\"]"));
		sendingButton.click();
		String sendToSubjectOfDirectedBook = "Asmar correspondence test" + myRand.nextInt(100);
		WebElement notesButton = driver.findElement(By.xpath("//*[@id=\"txtNote\"]"));
		notesButton.sendKeys(sendToSubjectOfDirectedBook);
		WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_btnCloseProcess\"]/span[1]"));
		closeButton.click();

		// Switch to the alert
		Alert alert = driver.switchTo().alert();
		alert.accept();
		driver.switchTo().defaultContent();
		Thread.sleep(3000);

		WebElement completedTasksButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[4]"));
		completedTasksButton1.click();

		WebElement subjectNameOnCompletedTasks = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_CompletedTasksGrid_ctl00__0\"]/td[3]"));
		String actualCompSubjectName = subjectNameOnCompletedTasks.getText();
		String expectedCompSubjectName = sendToSubject;
		myAssertion.assertEquals(actualCompSubjectName, expectedCompSubjectName,
				"The subject in completed tasks page at the recived user should be as");

		WebElement logOutButton = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[10]/a/span"));
		logOutButton.click();

		// To login
		String userName1 = "Ohoud.AlJaafari";
		String passWord1 = "s";
		WebElement userInput1 = driver.findElement(By.xpath("//*[@id=\"tbxName\"]"));
		WebElement passInput1 = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));
		WebElement login1 = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));

		// To fill credentials

		userInput1.sendKeys(userName1);
		passInput1.sendKeys(passWord1);
		login1.click();

		// check if message of other login exist shown and click ok
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}

		// assertion on the status : should be "تمت العملية بنجاح" after close the book
		// from all users who have tasks
		WebElement myTasks2 = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[3]/a/span"));
		myTasks2.click();

		WebElement subjectNameField3 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[3]"));
		String actualSubjectName3 = subjectNameField3.getText();
		String expectedSubjectName3 = sendToSubject;
		System.out.println("The actual correspondence name is " + actualSubjectName3
				+ "***** The expected correspondence name is" + expectedSubjectName3);
		myAssertion.assertEquals(actualSubjectName3, expectedSubjectName3,
				"The subject in my tasks page after close the book from the received user (task user) side should be as");

		// check status
		WebElement correspondenceStatus2 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[5]"));
		String actualCorrespondenceStatus2 = correspondenceStatus2.getText();
		String expectedCorrespondenceStatus2 = "تمت العملية بنجاح";
		System.out.println("The actual incoming status is " + actualCorrespondenceStatus2
				+ "***** The expected incoming status is" + expectedCorrespondenceStatus2);

		myAssertion.assertEquals(actualCorrespondenceStatus2, expectedCorrespondenceStatus2,
				"The status in my tasks page after close the book from the received user (task user) side should be as");
		WebElement logOutButton2 = driver.findElement(By.xpath("//*[@id=\"ctl00_RadMenu1\"]/ul/li[10]/a/span"));
		logOutButton2.click();
		
		
		// To login
				String userName4 = "abuzaid";
				String passWord4 = "s";
				WebElement userInput4 = driver.findElement(By.xpath("//*[@id=\"tbxName\"]"));
				WebElement passInput4 = driver.findElement(By.xpath("//*[@id=\"tbxPassword\"]"));
				WebElement login4 = driver.findElement(By.xpath("//*[@id=\"btnlogin\"]"));

				// To fill credentials

				userInput4.sendKeys(userName4);
				passInput4.sendKeys(passWord4);
				login4.click();

				// check if message of other login exist shown and click ok
				try {
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
					WebElement confirmLoginButton = driver.findElement(By.xpath("//button[contains(text(),'نعم')]"));
					confirmLoginButton.click();
				} catch (Exception e) {
					// the user will login normally

				}

				// assertion of reflecting on uncompleted tasks
				WebElement subjectNameField4 = driver
						.findElement(By.xpath("//*[@id=\"ctl00_MainContent_RadGrid1_ctl00__0\"]/td[5]"));
				String actualSubjectName4 = subjectNameField4.getText();
				String expectedSubjectName4 = sendToSubject;

				myAssertion.assertEquals(actualSubjectName4, expectedSubjectName4,
						"The subject in uncompleted tasks page at the  recived user(Cc user) should be as");

		myAssertion.assertAll();
	}
}
