package xyz.veracode.verainsecure;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.log4j.*;

import com.veracode.annotation.FilePathCleanser;

public class Utilities {

	public static String crlfStringFix(String unsanitizedString) throws UnsupportedEncodingException {
		return URLEncoder.encode(unsanitizedString, "UTF-8");	
	}
	
	//CWE 117 Sanitizer
	public static void cleanInfoLogger(Logger logger, String unsanitizedString) throws UnsupportedEncodingException {		
		String sanitizedString = URLEncoder.encode(unsanitizedString, "UTF-8");
		logger.info(sanitizedString);		
	}
	
	//CWE 73 Custom Cleanser
	@FilePathCleanser(userComment="AlphaNumeric Regex to Fix File Path CWE73")
	public static String cleanFilePath(String stringToValidate) {
		if(stringToValidate.matches("[A-Za-z0-9]+")) {
			return stringToValidate;
		} else return "reserved";
	}
}