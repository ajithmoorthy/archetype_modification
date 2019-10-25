#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.constants;
/**
 *  File constants are created for the user can access the file constants
 *  and use that file path constants to perform read and,write data from
 *  any type of the file.
 * @author ajith.periyasamy
 */
public class FileConstants {
	public static int IMPLICIT_WAIT=20;
	public static int EXPLICIT_WAIT=20;
	public static int FLUENT_WAIT=30;
	public static int FLUENT_POLL=5;
	public static int PAGE_LOAD_TIME=10;
	
	//creating file constants for the config properties file paths
	public static final String CONFIG_PATH = "./config.properties";
	
	//creating file constants for the log4j file path
	public static final String LOG4J_CONFIG_PROPERTY_PATH ="./src/test/resources/log4j/log4j.properties";
	
	//creating file constants for the extend report file paths
	public static final String EXTENT_OUPUT_PATH="./src/test/resources/extent/extent.html";
	public static final String SCREENSHOT_PATH=System.getProperty("user.dir")+"/src/test/resources/extent/screenshot/";
	public static final String EXTENT_CONFIG_PATH ="./src/test/resources/extent/extent-config.xml";
	}
