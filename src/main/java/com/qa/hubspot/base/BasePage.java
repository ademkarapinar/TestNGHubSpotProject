package com.qa.hubspot.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {
	
//	WebDriver driver;
	Properties prop;
	public static boolean highlightElement;
	public OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public static synchronized WebDriver getDriver(){
		return tlDriver.get();
	}
	
	public WebDriver init_driver(String browserName) {
		
		highlightElement = prop.getProperty("highlight").equals("yes") ? true : false;
		System.out.println("Browser name is: "+ browserName);
		optionsManager = new OptionsManager(prop);
		
		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
	//		driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			
//			if(prop.getProperty("headless").equals("yes")) {
//				ChromeOptions co = new ChromeOptions();
//				co.addArguments("--headless");
//				driver = new ChromeDriver(co);
//			}else {
//				driver = new ChromeDriver();
//			}		
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			
//			if(prop.getProperty("headless").equals("yes")) {
//				FirefoxOptions fo = new FirefoxOptions();
//				fo.addArguments("--headless");
//				driver = new FirefoxDriver(fo);
//			}else {
//				driver = new FirefoxDriver();
//			}
			
		}
		else if(browserName.equalsIgnoreCase("safari")) {
			WebDriverManager.getInstance(SafariDriver.class).setup();
//			driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
		}
		else {
			System.out.println("Browser name "+ browserName + " is not found");
		}	
//		driver.manage().deleteAllCookies();
//		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();

		
		//driver.get("");	
//		return driver;
		return getDriver();
		
	}
	
	public Properties init_properites() {
		
		prop = new Properties();
		String path = null;
		String env = null;
		//String path = "./src/main/java/com/qa/hubspot/config/config.properties";	
		try {
			env = System.getProperty("env");
			if(env.equals("qa")) {
				path = "./src/main/java/com/qa/hubspot/config/config.qa.properties";
			}else if(env.equals("stg")) {
				path = "./src/main/java/com/qa/hubspot/config/config.stg.properties";
			}
		} catch (Exception e) {
			path = "./src/main/java/com/qa/hubspot/config/config.properties";
		}	
		try {
			FileInputStream ip = new FileInputStream(path);
			prop.load(ip);
		}catch (FileNotFoundException e) {
			System.out.println("some issue occured with config properties... Correct config file");
		}catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	/**
	 * Screenshot
	 * @return
	 */
	public String getScreenshot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+ "/screenshots/" + System.currentTimeMillis()+ ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.err.println("screenshot captured failed...");
		}
		return path;
	}
}