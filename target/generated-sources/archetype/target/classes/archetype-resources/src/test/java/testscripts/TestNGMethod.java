#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.testscripts;

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

import ${package}.constants.FileConstants;
import ${package}.utils.ExcelReader;
import ${package}.utils.PropertiesReader;

public class TestNGMethod {
	ExcelReader excelread=new ExcelReader();
	PropertiesReader propread=new PropertiesReader();
	public List<XmlSuite> suiteXml(String[] classobject) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException 
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
		xmlSuite.setThreadCount(threadcount);
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
	@Test
	public void xmlsuite() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String[] classes=excelread.excelDataProviderArray("./", 0, "classname");
		List<XmlSuite> suites = suiteXml(classes);
		TestNG testng = new TestNG();
		testng.setXmlSuites(suites);
		testng.run();
	}

}
