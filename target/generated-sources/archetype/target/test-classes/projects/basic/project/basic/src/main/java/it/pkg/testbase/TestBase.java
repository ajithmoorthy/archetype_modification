package it.pkg.testbase;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import it.pkg.constants.FileConstants;
import it.pkg.extentreport.Extent;
import it.pkg.logreports.LogReporter;
import it.pkg.utils.PropertiesReader;

import io.github.bonigarcia.wdm.WebDriverManager;
/*this class will act as the base class for the test it will provide the browser based on the user choice */
/**
 * @author ajith.periyasamy
 * This Class is extend from the Extent class and class contains the browser selection method.
 * baseSetup method is used to select the script run in the Selenium grid or user system.
 */
public class TestBase extends Extent{
	protected Properties prop=null;
	PropertiesReader propertyReader = new PropertiesReader();
	LogReporter log=new LogReporter();
	/**
	 * This baseSetup method will provide the option to choose the browser driver to run the script.
	 *  The configuration details is read from the config.properties file.
	 *  and this browser details passed to the switch case and based on the configuration details script is running in different browsers. 
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("deprecation")
	@BeforeTest
	@Parameters("browser")
	public void baseSetup(String browser) throws IOException, InstantiationException, IllegalAccessException {
		prop = propertyReader.KeyValueLoader(FileConstants.CONFIG_PATH);
		String[] chooser=browser.split(",");
		if(chooser[0].equals("GRID")) {
			String Node = "http://55.55.53.104:4444/wd/hub";
			switch (chooser[1]) {
			case "CHROME":
				System.out.println("Executing on chrome");
				DesiredCapabilities chrome =new DesiredCapabilities();
				chrome.setBrowserName("chrome");
				driver = new RemoteWebDriver(new URL(Node), chrome);
				break;
			case "FIREFOX":
				System.out.println("Executing on FireFox");
				DesiredCapabilities fire = new DesiredCapabilities();
				fire.setBrowserName("firefox");
				driver = new RemoteWebDriver(new URL(Node), fire);
				break;
			case "IE":
				System.out.println("Executing on IE");
				DesiredCapabilities iecap = new DesiredCapabilities();
				iecap.setBrowserName("ie");
				driver = new RemoteWebDriver(new URL(Node), iecap);
				break;
			}
			driver.get(prop.getProperty("url"));
			log.logReportMessage("url is loaded");
		}
		else {
			switch (chooser[1]) {
			case "CHROME":
				WebDriverManager.chromedriver().arch32().setup();
				ChromeOptions chromeoptions = new ChromeOptions();
				chromeoptions.addArguments("--disable-notifications");
				chromeoptions.addArguments("disable-geolocation");
				driver = new ChromeDriver(chromeoptions);
				break;
			case "FIREFOX":
				WebDriverManager.firefoxdriver().arch64().setup();
				FirefoxOptions fire = new FirefoxOptions();
				fire.addPreference("dom.webnotifications.enabled", false);
				driver = new FirefoxDriver(fire);
				break;
			case "IE":
				WebDriverManager.iedriver().arch64().setup();
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, prop.getProperty("url"));
				capabilities.setCapability("requireWindowFocus", true);
				capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				driver=new InternetExplorerDriver(capabilities);
				break;
			case "EDGE":
				WebDriverManager.edgedriver().arch64().setup();
				driver = new EdgeDriver();
				break;
			}
			driver.get(prop.getProperty("url"));
			log.logReportMessage("url is loaded");
		}
	}
}
