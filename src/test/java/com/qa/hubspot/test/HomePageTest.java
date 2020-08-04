package com.qa.hubspot.test;
//got it
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.pages.HomePage;
import com.qa.hubspot.pages.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
    
    @Epic("Epic - 102 : create HomePage features")
    @Feature("US - 502 : Create test for HomePage on HubSpot")
public class HomePageTest {
	
	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	HomePage homePage;
	Credentials userCred;
	
	@BeforeTest
	public void setUp() throws InterruptedException {
		basePage = new BasePage();
		prop = basePage.init_properites();
		String browserName = prop.getProperty("browser");
		driver = basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
		homePage = loginPage.doLogin(userCred);
		Thread.sleep(5000);
	}
	
	@Test(priority=1, description = "verify homepage title")
	@Description("Verify Home Page title")
	@Severity(SeverityLevel.NORMAL)
	public void verifyHomePageTitle() {
		String title = homePage.getHomePageTitle();
		System.out.println("home page title is: "+ title);
		Assert.assertEquals(title, AppConstants.HOME_PAGE_TITLE);
	}
	
	@Test(priority=2, description="verify homepage header")
	@Description("Verify HomePage Header")
	@Severity(SeverityLevel.NORMAL)
	public void verifyHomePageHeaderTest() {
		String header = homePage.getHomePageHeader();
		System.out.println("home page header is: "+ header);
		Assert.assertEquals(header, AppConstants.HOME_PAGE_HEADER);
	}
	
	@Test(priority=3, description="verify logged account")
	@Description("Verify Logged User Account")
	@Severity(SeverityLevel.CRITICAL)
	public void verifyLoggedInUserTest() {
		String accountName = homePage.getLoggedInUserAccount();
		System.out.println("logged in user account name is: "+ accountName);
		Assert.assertEquals(accountName, prop.getProperty("accountname"));
	}
	
	@AfterTest
	public void tearDown() {
//		driver.quit();
	}
}
