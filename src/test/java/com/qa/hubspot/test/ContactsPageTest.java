package com.qa.hubspot.test;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.pages.ContactsPage;
import com.qa.hubspot.pages.HomePage;
import com.qa.hubspot.pages.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;
import com.qa.hubspot.util.ExcelUtil;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;

   @Epic("Epic - 103 : create HomePage features")
   @Feature("US - 503 : Create test for HomePage on HubSpot")
public class ContactsPageTest {	
	BasePage basePage;
	Properties prop;
	WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;
	ContactsPage contactsPage;
	Credentials userCred;
 
	@BeforeTest
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.init_properites();
		String browser = prop.getProperty("browser");
		driver = basePage.init_driver(browser);
		driver.get(prop.getProperty("url"));
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
		homePage = loginPage.doLogin(userCred);
		contactsPage = homePage.goToContactsPage();
	}
	@Test(priority = 1)
	public void verifyContactsPageTitle() {
		String title = contactsPage.getContactsPageTitle();
		System.out.println("contacts page title is: " + title);
		Assert.assertEquals(title, AppConstants.CONTACT_PAGE_TITLE);
	}
	@DataProvider
	public Object[][] getContactsTestData() {
		Object[][] data = ExcelUtil.getTestData(AppConstants.CONTACT_SHEET_NAME);
		return data;
	}
	@Test(priority = 2, dataProvider = "getContactsTestData")
	@Step("Create new Contact with {0}, {1}, {2}, {3}")
	public void createContactsTest(String email, String firstName, String lastName, String jobTitle) {
		contactsPage.createNewContact(email, firstName, lastName, jobTitle);		
	}
	@AfterTest
	public void tearDown() {
//		driver.quit();
	}
}
