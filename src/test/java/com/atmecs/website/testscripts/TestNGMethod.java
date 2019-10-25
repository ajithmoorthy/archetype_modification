package com.atmecs.website.testscripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import com.atmecs.website.constants.FileConstants;
import com.atmecs.website.utils.ExcelReader;
import com.atmecs.website.utils.PropertiesReader;

/**
 * This Class create the dynamic testNG suite file to run the scripts parallel
 * @author ajith.periyasamy
 *
 */
public class TestNGMethod {
	ExcelReader excelread=new ExcelReader();
	PropertiesReader propread=new PropertiesReader();
	/**
	 * This suiteXmlGenerator method take the below array
	 * @param classobject
	 * and generate the virtual suite file the run the script parallel via the all the browsers
	 * finally @return return the List of suite
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public List<XmlSuite> suiteXmlGenerator(String[] classobject) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException 
	{
		Properties props = propread.KeyValueLoader(FileConstants.CONFIG_PATH);
		List<String> browsernames = new ArrayList<String>();
		String[] browserarray = props.getProperty("webdrivername").split(",");
		String arr1[]=browserarray[1].split(":");
		for (String name : arr1)
		{
			browsernames.add(name);
		}
		XmlSuite xmlSuite = new XmlSuite();
		xmlSuite.setName("mysuite");
		xmlSuite.setParallel(ParallelMode.TESTS);
		int threadcount=browserarray.length*classobject.length;
		xmlSuite.setThreadCount(threadcount*5);
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		for(int initial=0; initial<classobject.length; initial++) 
		{
			for (int count=0; count<browsernames.size(); count++) 
			{
				XmlTest xmlTest1 = new XmlTest(xmlSuite);
				Map<String, String> parameter1 = new HashMap<String, String>();
				parameter1.put("browser", browserarray[0]+","+browsernames.get(count));
				xmlTest1.setParameters(parameter1);
				xmlTest1.setName("Test validate " +browsernames.get(count)+classobject[initial]);
				Class<?> class1 = Class.forName(classobject[initial]);  
				XmlClass myClass = new XmlClass(class1);
				List<XmlClass> xmlClassList1 = new ArrayList<XmlClass>();
				xmlClassList1.add(myClass);
				xmlTest1.setXmlClasses(xmlClassList1);
			}
		}
		suites.add(xmlSuite);
		return suites;
	}
	/**
	 * This xmlsuiteRunner Test is run the test suite file generated by the suiteXml method using the testNG class 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void xmlsuiteRunner() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String[] classes=excelread.excelDataProviderArray(FileConstants.CLASS_NAME_PATH, 0, "classname");
		List<XmlSuite> suites = suiteXmlGenerator(classes);
		TestNG testng = new TestNG();
		testng.setXmlSuites(suites);
		testng.run();
	}

}
