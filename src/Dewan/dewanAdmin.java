package Dewan;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class dewanAdmin {

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
	

	@BeforeTest
	public void beforeLogin() {
		driver.get("http://localhost:4444/login.aspx");
		driver.manage().window().maximize();
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
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			WebElement confirmLoginButton = driver.findElement(By.xpath("/html/body/div/div/div[10]/button[1]"));
			confirmLoginButton.click();
		} catch (Exception e) {
			// the user will login normally

		}

	}

	@Test()
	public void manipulateDivisionTest() throws InterruptedException {

		// path to create new division and fill mandatories

		WebElement orgStructureButton = driver.findElement(By.xpath("//*[@id=\"ctl00_li5\"]/a/span"));
		orgStructureButton.click();

		WebElement divisionButton = driver.findElement(By.xpath("//*[@id=\"ctl00_Division\"]"));
		divisionButton.click();

		WebElement addNewDivision = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ImgSave\"]"));
		addNewDivision.click();

		WebElement companySelection = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_DDL_Company\"]"));
		companySelection.click();

		WebElement selectProcess = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_DDL_Company\"]/option[2]"));
		selectProcess.click();
		Thread.sleep(2000);

		// Assuming the user you want to select has the username "desired_user" (manager
		// of division)
		String desiredUsername = "Ohoud AlJaafari";

		// Enter search criteria if applicable
		WebElement searchInput = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_DDLDIVManager\"]"));
		searchInput.click();

//        // Perform search or wait for dynamic loading to complete
//        
		// Wait for the user list to load and get all the user elements

		// Find the desired user element by iterating through the list
		WebElement desiredUserElement = null;
		for (WebElement userElement : driver.findElements(By.tagName("option"))) {
			if (userElement.getText().contains(desiredUsername)) {
				desiredUserElement = userElement;
				break;
			}
		}

		// Perform the desired action with the user element, e.g., click or interact
		// with it
		if (desiredUserElement != null) {
			desiredUserElement.click();
		}

		// mandatory
		String enName = "Asmar division Test " + myRand.nextInt(100);
		WebElement divisionEnName = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxNameEN\"]"));
		divisionEnName.sendKeys(enName);

		// mandatory
		String arName = "اسمر" + myRand.nextInt(100);
		WebElement divisionArName = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxNameAR\"]"));
		divisionArName.sendKeys(arName);

		System.out.println("The division english name is " + enName);
		System.out.println("The division arabic name is " + arName);

		WebElement activeButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_cbxIsShow\"]"));
		activeButton.click();
		// final save
		WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ImgSave\"]"));
		saveButton.click();
		Thread.sleep(2000);
		// assertion to check that division created successfully

		WebElement divisonPage = driver.findElement(By.xpath("//*[@id=\"ctl00_WF_SiteMapPath\"]/span[5]/a"));
		divisonPage.click();

		WebElement divisionEnNameField = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxNameEN\"]"));
		divisionEnNameField.sendKeys(enName);

		WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ImgSearch\"]"));
		searchButton.click();
		Thread.sleep(2000);
		WebElement getActualDivName = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVViewData\"]/tbody/tr[2]/td[3]"));
		String actualDivName = getActualDivName.getText();
		String expectedDivName = enName;
		myAssertion.assertEquals(actualDivName, expectedDivName);
		

		// path to update the added division

		WebElement updateButton = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVViewData_ctl02_HyperLink3\"]/img"));
		updateButton.click();
		WebElement updateEnName = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxNameEN\"]"));

		String updatedName = "update " + enName;
		updateEnName.clear();
		updateEnName.sendKeys(updatedName);

		WebElement updateButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ImgUpdate\"]"));
		updateButton1.click();

		Thread.sleep(2000);

		WebElement divisonPage1 = driver.findElement(By.xpath("//*[@id=\"ctl00_WF_SiteMapPath\"]/span[5]/a"));
		divisonPage1.click();

		WebElement divisionEnNameField1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxNameEN\"]"));
		divisionEnNameField1.sendKeys(enName);

		WebElement searchButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ImgSearch\"]"));
		searchButton1.click();

		Thread.sleep(2000);

		WebElement getActualDivNameUpdated = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVViewData\"]/tbody/tr[2]/td[3]"));
		String actualDivNameUpdated = getActualDivNameUpdated.getText();
		String expectedDivNameUpdated = updatedName;
		myAssertion.assertEquals(actualDivNameUpdated, expectedDivNameUpdated);
		
		// start create new dept
		WebElement orgStructureButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_li5\"]/a/span"));
		orgStructureButton1.click();

		WebElement deptButton = driver.findElement(By.xpath("//*[@id=\"ctl00_Department\"]"));
		deptButton.click();

		WebElement addNewDivision1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ImgSave\"]"));
		addNewDivision1.click();

		WebElement companySelection1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ddlCompany\"]"));
		companySelection1.click();

		WebElement selectProcess1 = driver
				.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ddlCompany\"]/option[2]"));
		selectProcess1.click();
		Thread.sleep(2000);
		
		// Enter search criteria if applicable
				WebElement searchInput1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ddldivision\"]"));
				searchInput1.click();
				
				String desiredDivision = updatedName;
				
//		        // Perform search or wait for dynamic loading to complete
//		        
				// Wait for the user list to load and get all the user elements

				// Find the desired user element by iterating through the list
				WebElement desiredDivisionElement = null;
				for (WebElement divisionElement : driver.findElements(By.tagName("option"))) {
					if (divisionElement.getText().contains(desiredDivision)) {
						desiredDivisionElement = divisionElement;
						break;
					}
				}

				// Perform the desired action with the user element, e.g., click or interact
				// with it
				if (desiredDivisionElement != null) {
					desiredDivisionElement.click();
				}

				// mandatory
				String enName1 = "Asmar dept Test " + myRand.nextInt(100);
				WebElement deptEnName1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxNameEN\"]"));
				deptEnName1.sendKeys(enName1);

				// mandatory
				String arName1 = "اسمر" + myRand.nextInt(100);
				WebElement deptArName = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxNameAR\"]"));
				deptArName.sendKeys(arName1);

				System.out.println("The dept english name is " + enName1);
				System.out.println("The dept arabic name is " + arName1);

				WebElement activeButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_cbxIsShow\"]"));
				activeButton1.click();
				// final save
				WebElement saveButton1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ImgSave\"]"));
				saveButton1.click();
				Thread.sleep(2000);
				
				
				// assertion to check that division created successfully

				WebElement deptPage = driver.findElement(By.xpath("//*[@id=\"ctl00_WF_SiteMapPath\"]/span[5]/a"));
				deptPage.click();

				WebElement ddeptEnNameField = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxNameEN\"]"));
				ddeptEnNameField.sendKeys(enName1);

				WebElement searchButton2 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ImgSearch\"]"));
				searchButton2.click();
				Thread.sleep(2000);
				
				WebElement getActualDeptName = driver
						.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVViewData\"]/tbody/tr[2]/td[3]"));
				
				String actualDeptName = getActualDeptName.getText();
				String expectedDeptName = enName1;
				myAssertion.assertEquals(actualDeptName, expectedDeptName);
				

				// path to update the added division

				WebElement updateButton2 = driver
						.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVViewData_ctl02_HyperLink3\"]/img"));
				updateButton2.click();
				WebElement updateEnName1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxNameEN\"]"));

				String updatedName1 = "update " + enName1;
				updateEnName1.clear();
				updateEnName1.sendKeys(updatedName1);

				WebElement updateButton3 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ImgUpdate\"]"));
				updateButton3.click();

				Thread.sleep(2000);

				WebElement deptPage1 = driver.findElement(By.xpath("//*[@id=\"ctl00_WF_SiteMapPath\"]/span[5]/a"));
				deptPage1.click();

				WebElement deptEnNameField1 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_tbxNameEN\"]"));
				deptEnNameField1.sendKeys(updatedName1);

				WebElement searchButton3 = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ImgSearch\"]"));
				searchButton3.click();

				Thread.sleep(2000);

				WebElement getActualDeptNameUpdated = driver
						.findElement(By.xpath("//*[@id=\"ctl00_MainContent_GVViewData\"]/tbody/tr[2]/td[3]"));
				String actualDeptNameUpdated = getActualDeptNameUpdated.getText();
				String expectedDeptNameUpdated = updatedName1;
				myAssertion.assertEquals(actualDeptNameUpdated, expectedDeptNameUpdated);
				
				myAssertion.assertAll();
//		Thread.sleep(4000);
//		driver.close();

	}

}
