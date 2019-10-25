package it.pkg.helper;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import it.pkg.constants.FileConstants;
import it.pkg.extentreport.Extent;
import it.pkg.logreports.LogReporter;
import com.relevantcodes.extentreports.LogStatus;

public class ValidaterHelper extends Extent {
	LogReporter log=new LogReporter();
	By by;
	/**
	 * This urlValidater method take the below parameters
	 * @param Driver
	 * @param Expected_Url
	 * and validated the URL of the driver using expected URL.
	 * validation is done by the assertion using Assert class and its methods.
	 */
	public void urlValidater(WebDriver Driver,String Expected_Url){
		try {
			Assert.assertEquals(Driver.getCurrentUrl(),Expected_Url);
			log.logReportMessage("Successfully Validated the correct Url is :"+ Driver.getCurrentUrl());
			logger.log(LogStatus.INFO,"Successfully Validated the correct Url is :" +Driver.getCurrentUrl());
		}catch(AssertionError e) {
			System.out.println("Navigate to wrong Webpage");
			log.logReportMessage("Navigate to wrong Webpage");
			logger.log(LogStatus.INFO, "Navigate to wrong Webpage");
		}	
	}
	/**
	 * This titleValidater method take the below parameters
	 * @param driver
	 * @param documentTitle
	 * and validated the title of the web page using expected page title.
	 * validation is done by the assertion using Assert class and its method.
	 */
	public void titleValidater(WebDriver driver, String documentTitle){
		try {
			Assert.assertEquals(driver.getTitle(), documentTitle);
			log.logReportMessage("Document title is validated :"+driver.getTitle());
			logger.log(LogStatus.INFO,"Document title is validated :" +driver.getTitle());
		}
		catch(AssertionError e)
		{
			System.out.println("Document title is not match with Expected :"+driver.getTitle());
			log.logReportMessage("Document title is not match with Expected :"+driver.getTitle());
			logger.log(LogStatus.INFO,"Document title is not match with Expected :"+driver.getTitle());	
		}
	}
	/**
	 * This textOfElement method take below parameters
	 * @param webdriver
	 * @param locator
	 * get the text of the element  
	 *  and @return the content string.
	 */
	public String textOfElement(WebDriver webdriver,String locator) {
		String content = null;
		try {
			@SuppressWarnings("deprecation")
			Wait<WebDriver> wait = new FluentWait<WebDriver>(webdriver)
			.withTimeout(FileConstants.FLUENT_WAIT, TimeUnit.SECONDS)
			.pollingEvery(FileConstants.FLUENT_POLL, TimeUnit.SECONDS)
			.ignoring(ElementClickInterceptedException.class)
			.ignoring(NoSuchElementException.class);
			WebElement element=wait.until(new Function<WebDriver,WebElement>() 
			{
				public WebElement apply(WebDriver driver) {
					return driver.findElement(matchElement(locator));
				}
			});
			content=element.getText();
		}catch (Exception e) {
			System.out.println("Element is not available or not clickable");
		}
		return content;
	}
	/**
	 * This method take input as below parameters:
	 * @param locators
	 * and perform the separate the locators and options.
	 * using that locators create the Object of By class
	 * and @return by object.
	 */
	public By matchElement(String locators) {
		String[] input=locators.split(",");
		switch(input[0].toUpperCase())
		{
		case "XPATH":
			by=By.xpath(input[1]);
			break;
		case "ID":
			by=By.id(input[1]);
			break;
		case "NAME":
			by=By.name(input[1]);
			break;
		case "CSS":
			by=By.cssSelector(input[1]);
			break;
		case "CLASS":
			by=By.className(input[1]);
			break;
		case "LINK_TEXT":
			by=By.linkText(input[1]);
			break;
		case "PARTIAL_LINK_TEXT":
			by=By.partialLinkText(input[1]);
			break;
		case "TAG_NAME":
			by=By.tagName(input[1]);
			break;
		}
		return by;
	}
	/**
	 * This webElementsValidater method take the below parameters
	 * @param driver
	 * @param locators
	 * @param footerarray
	 * and validate the each web elements is present or not using assertions.
	 */
	public void webElementsValidater(WebDriver driver,String locators,String[] footerarray) {
		WebDriverWait wait2 = new WebDriverWait(driver, 20);
		wait2.until(ExpectedConditions.elementToBeClickable(matchElement(locators)));
		List<WebElement> list=driver.findElements(matchElement(locators));
		int count=0;
		while(count<1) {
			for(WebElement element:list)
			{
				String[] elementarray=element.getText().split("\n");
				for(int variable=0; variable<elementarray.length; variable++) {
					assertValidater(elementarray[variable],footerarray[count]);
					count++;
				}
			}
		}
	}
	/**
	 * This assertValidater method take the below parameters
	 * @param actual
	 * @param expected
	 * and check the actual and expected are equal or not by using the assertion.
	 */
	public void assertValidater(String actual,String expected) {
		try {
			Assert.assertEquals(actual,expected);
			log.logReportMessage("Actual Value :"+actual+" and Expected :"+expected+" is validated successfully");
			logger.log(LogStatus.INFO,"Actual Value :"+actual+" and Expected :"+expected+" is validated successfully");	
		}
		catch(AssertionError e)
		{
			System.out.println("Actual Value :"+actual+" not match with the Expected value :"+expected);
			log.logReportMessage("Actual Value :"+actual+" not match with the Expected value :"+expected);
			logger.log(LogStatus.INFO,"Actual Value :"+actual+" not match with the Expected value :"+expected);
		}
	}
	/**
	 * This contains Validations take the below parameters
	 * @param webdriver
	 * @param locator
	 * @param expected
	 * and check the element content and expected value using the if content is equal 
	 *  then @return boolean variable bool true.
	 */
	public boolean containsValidater(WebDriver webdriver,String locator,String expected) {
		boolean bool=false;
		WebElement element=webdriver.findElement(matchElement(locator));
		String content=element.getText();
		if(content.contains(expected))
		{
			bool=true;
		}
		return bool;
	}
	/**
	 * This assertValidater method take the below parameters
	 * @param actual
	 * @param expected
	 * and check the actual and expected are equal or not by using the assertion.
	 */
	public void assertValidater(boolean actual,boolean expected) {
		try {
			Assert.assertEquals(actual,expected);
			log.logReportMessage("Actual Value :"+actual+" and Expected :"+expected+" is validated successfully");
			logger.log(LogStatus.INFO,"Actual Value :"+actual+" and Expected :"+expected+" is validated successfully");	
		}
		catch(AssertionError e)
		{
			System.out.println("Actual Value :"+actual+" not match with the Expected value :"+expected);
			log.logReportMessage("Actual Value :"+actual+" not match with the Expected value :"+expected);
			logger.log(LogStatus.INFO,"Actual Value :"+actual+" not match with the Expected value :"+expected);
		}
	}
}
