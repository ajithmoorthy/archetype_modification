package com.atmecs.website.helper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * This class is used as common utility for web table reading and web table data position reading web table validation. 
 * @author ajith.periyasamy
 *
 */
public class WebTableHelper {
	WaitForElement waitobject=new WaitForElement();
	SeleniumHelper seleniumhelp=new SeleniumHelper();
	public int sizeOfTable(WebDriver driver,String locator) {
		List<WebElement> countlist=driver.findElements(seleniumhelp.matchElement(locator));
		return countlist.size();
	}
	public String returnTableData(WebDriver driver,String locator) {
		WebElement element=driver.findElement(seleniumhelp.matchElement(locator));
		String text=element.getText();
		return text; 
	}
	public void tableReader(WebDriver driver,int rowcount,int colcount,String locator) {
		for(int initial=1; initial<=rowcount; initial++) {
			for(int count=1; count<=colcount; count++) {
				String celllocator=locator.replace("xxx", ""+initial);
			    celllocator=celllocator.replace("yyy", ""+count);
				String data=returnTableData(driver,celllocator);
				System.out.print(data+"\t |");
			}
			System.out.println();
		}
	}
	public void tableColumnReader(WebDriver driver,int rowcount,int colcount,String locator,int colnum) {
		for(int initial=1; initial<=rowcount; initial++) {
			for(int count=1; count<=colcount; count++) {
				String celllocator=locator.replace("xxx", ""+initial);
			    celllocator=celllocator.replace("yyy", ""+count);
			    if(count==colnum) {
				String data=returnTableData(driver,celllocator);
				System.out.print(data+"\t |");
			    }
			}
			System.out.println();
		}
	}
	public void tableRowReader(WebDriver driver,int rowcount,int colcount,String locator,int rownum) {
		for(int initial=1; initial<=rowcount; initial++) {
			for(int count=1; count<=colcount; count++) {
				String celllocator=locator.replace("xxx", ""+initial);
			    celllocator=celllocator.replace("yyy", ""+count);
			    if(initial==rownum) {
				String data=returnTableData(driver,celllocator);
				System.out.print(data+"\t |");
			    }
			}
			System.out.println();
		}
	}
	public int rowCounter(WebDriver driver,String locator) {
		List<WebElement> countlist=waitobject.WaitForFluent(driver,locator).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		return countlist.size();
	}
	public int columnCounter(WebDriver driver,String locator) {
		List<WebElement> countlist=waitobject.WaitForFluent(driver,locator).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td"));
		return countlist.size();
	}
	public void tableReader(WebDriver driver,String locator) {
		int rowcount=rowCounter(driver,locator);
		int colcount=rowCounter(driver,locator);
		for(int initial=0; initial<=rowcount; initial++) {
			for(int count=0; count<=colcount; count++) {
				String data=waitobject.WaitForFluent(driver, locator).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(initial).findElements(By.tagName("td")).get(count).getText();
				System.out.print(data+"\t |");
			}
			System.out.println();
		}
	}
	public void positionOfData(WebDriver driver,String locator,String celldata) {
		int rowcount=rowCounter(driver,locator);
		int colcount=rowCounter(driver,locator);
		for(int initial=0; initial<=rowcount; initial++) {
			for(int count=0; count<=colcount; count++) {
				String data=waitobject.WaitForFluent(driver, locator).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(initial).findElements(By.tagName("td")).get(count).getText();
				System.out.println(data);
				if(celldata.contentEquals(data)) 
				{
				System.out.println("rowindex = "+(initial+1)+" columnindex = "+(count+1));
				}
			}
		}
	}
	public void getCellData(WebDriver driver,String locator,int colnum,int rownum) {
		int rowcount=rowCounter(driver,locator);
		int colcount=rowCounter(driver,locator);
		for(int initial=0; initial<=rowcount; initial++) {
			for(int count=0; count<=colcount; count++) {
				String data=waitobject.WaitForFluent(driver, locator).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(initial).findElements(By.tagName("td")).get(count).getText();
				if(colnum==count && rownum==initial) {
					System.out.println(data);
				}
			}
		}
	}

}
